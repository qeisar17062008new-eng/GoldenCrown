package com.example.goldencrown

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.goldencrown.data.AppDatabaseRepository
import com.example.goldencrown.model.ActiveSheet
import com.example.goldencrown.model.GoldenGoal
import com.example.goldencrown.model.PromoBanner
import com.example.goldencrown.model.TransactionItem
import com.example.goldencrown.model.UserData
import com.example.goldencrown.ui.components.*
import com.example.goldencrown.data.repository.FirestoreCustomerRepository
import com.example.goldencrown.ui.screen.customer.CustomerScreen
import com.example.goldencrown.ui.screen.customer.CustomerViewModel
import com.example.goldencrown.ui.screens.*
import com.example.goldencrown.ui.sheets.*
import com.example.goldencrown.ui.theme.*

enum class AppNavState {
    SPLASH,
    LOGIN,
    REGISTER,
    USER_DASHBOARD,
    ADMIN_DASHBOARD,
    CUSTOMER_MANAGEMENT
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoldenCrownTheme {
                MainRootNavigation()
            }
        }
    }
}

@Composable
fun MainRootNavigation() {
    val context = LocalContext.current
    val dbRepo = remember { AppDatabaseRepository(context) }
    val customerRepo = remember { FirestoreCustomerRepository(context) }
    val customerViewModel = remember { CustomerViewModel(customerRepo) }

    var navState by remember { mutableStateOf(AppNavState.SPLASH) }

    // Live Database Flow States
    val currentUser by dbRepo.currentUser.collectAsState()
    val registeredUsers by dbRepo.registeredUsers.collectAsState()
    val userGrams by dbRepo.userGrams.collectAsState()
    val goldBuyPrice by dbRepo.goldBuyPrice.collectAsState()
    val goldSellPrice by dbRepo.goldSellPrice.collectAsState()
    val transactions by dbRepo.transactions.collectAsState()
    val goldenGoals by dbRepo.goldenGoals.collectAsState()

    var activeSheet by remember { mutableStateOf(ActiveSheet.NONE) }

    when (navState) {
        AppNavState.SPLASH -> {
            SplashScreen(
                onGetStarted = { navState = AppNavState.LOGIN },
                onRegisterClick = { navState = AppNavState.REGISTER }
            )
        }
        AppNavState.LOGIN -> {
            LoginScreen(
                onLoginSubmit = { phoneOrEmail, pin, isAdminRole ->
                    val result = dbRepo.authenticateUser(phoneOrEmail, pin, isAdminRole)
                    if (result.isSuccess) {
                        navState = if (isAdminRole) AppNavState.ADMIN_DASHBOARD else AppNavState.USER_DASHBOARD
                    }
                    result
                },
                onForgotPassword = { activeSheet = ActiveSheet.RESET_PIN },
                onRegisterClick = { navState = AppNavState.REGISTER }
            )
        }
        AppNavState.REGISTER -> {
            RegisterScreen(
                onRegisterSubmit = { fullName, phone, email, ktp, pin ->
                    val result = dbRepo.registerNewNasabah(fullName, phone, email, ktp, pin)
                    if (result.isSuccess) {
                        navState = AppNavState.USER_DASHBOARD
                    }
                    result
                },
                onLoginClick = { navState = AppNavState.LOGIN }
            )
        }
        AppNavState.ADMIN_DASHBOARD -> {
            AdminDashboardScreen(
                registeredUsers = registeredUsers,
                currentBuyPrice = goldBuyPrice,
                currentSellPrice = goldSellPrice,
                onUpdatePrices = { newBuy, newSell ->
                    dbRepo.updatePrices(newBuy, newSell)
                },
                onOpenCustomerCrud = { navState = AppNavState.CUSTOMER_MANAGEMENT },
                onLogout = { navState = AppNavState.LOGIN }
            )
        }
        AppNavState.USER_DASHBOARD -> {
            GoldenCrownApp(
                currentUser = currentUser,
                userGoldGrams = userGrams,
                goldBuyPrice = goldBuyPrice,
                goldSellPrice = goldSellPrice,
                transactions = transactions,
                goldenGoals = goldenGoals,
                onUpdateGrams = { newGrams -> dbRepo.updateGoldGrams(newGrams) },
                onAddTransaction = { tx -> dbRepo.addTransaction(tx) },
                onAddGoal = { title, grams, deadline -> dbRepo.addGoldenGoal(title, grams, deadline) },
                onUpdateGoal = { id, title, grams, deadline -> dbRepo.updateGoldenGoal(id, title, grams, deadline) },
                onDeleteGoal = { id -> dbRepo.deleteGoldenGoal(id) },
                onOpenCustomerCrud = { navState = AppNavState.CUSTOMER_MANAGEMENT },
                onLogout = { navState = AppNavState.LOGIN }
            )
        }
        AppNavState.CUSTOMER_MANAGEMENT -> {
            CustomerScreen(
                viewModel = customerViewModel,
                onNavigateBack = {
                    navState = if (currentUser?.isAdmin == true) AppNavState.ADMIN_DASHBOARD else AppNavState.USER_DASHBOARD
                }
            )
        }
    }

    // Modal Sheets for Login Level
    when (activeSheet) {
        ActiveSheet.RESET_PIN -> {
            ResetPinSheet(
                onDismiss = { activeSheet = ActiveSheet.NONE }
            )
        }
        else -> {}
    }
}

@Composable
fun GoldenCrownApp(
    currentUser: UserData? = null,
    userGoldGrams: Double,
    goldBuyPrice: Double,
    goldSellPrice: Double,
    transactions: List<TransactionItem>,
    goldenGoals: List<GoldenGoal>,
    onUpdateGrams: (Double) -> Unit,
    onAddTransaction: (TransactionItem) -> Unit,
    onAddGoal: (String, Double, String) -> Unit,
    onUpdateGoal: (String, String, Double, String) -> Unit,
    onDeleteGoal: (String) -> Unit,
    onOpenCustomerCrud: () -> Unit = {},
    onLogout: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var activeSheet by remember { mutableStateOf(ActiveSheet.NONE) }

    val calculatedRupiahValue = "Rp " + String.format("%,.0f", userGoldGrams * goldBuyPrice)
    val calculatedGramsFormatted = String.format("%.4f", userGoldGrams)

    var selectedTransaction by remember { mutableStateOf<TransactionItem?>(null) }
    var selectedPromo by remember { mutableStateOf<PromoBanner?>(null) }
    var selectedGoalForEdit by remember { mutableStateOf<GoldenGoal?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = CreamBackground,
        topBar = {
            AppTopBar(
                userName = currentUser?.fullName ?: "Qeisar",
                userRole = currentUser?.priorityTier ?: "Prioritas 1 (VVIP Diamond Crown)",
                onSearchClick = { activeSheet = ActiveSheet.GLOBAL_SEARCH },
                onNotificationClick = { activeSheet = ActiveSheet.NOTIFICATIONS },
                onHelpClick = { activeSheet = ActiveSheet.HELP }
            )
        },
        bottomBar = {
            AppBottomNavigation(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> HomeScreen(
                    calculatedGramsFormatted = calculatedGramsFormatted,
                    calculatedRupiahValue = calculatedRupiahValue,
                    userGoldGrams = userGoldGrams,
                    goldBuyPrice = goldBuyPrice,
                    goldSellPrice = goldSellPrice,
                    transactionList = transactions,
                    goals = goldenGoals,
                    onSelectTab = { selectedTab = it },
                    onOpenSheet = { sheet -> activeSheet = sheet },
                    onOpenCustomerCrud = onOpenCustomerCrud,
                    onGoalClick = { goal ->
                        selectedGoalForEdit = goal
                        activeSheet = ActiveSheet.GOLDEN_GOAL_CRUD
                    },
                    onTransactionClick = { tx ->
                        selectedTransaction = tx
                        activeSheet = ActiveSheet.TRANSACTION_DETAIL
                    },
                    onPromoClick = { promo ->
                        selectedPromo = promo
                        activeSheet = ActiveSheet.PROMO_DETAIL
                    }
                )
                1 -> AssetScreen(
                    userGrams = userGoldGrams,
                    goldBuyPrice = goldBuyPrice,
                    onViewCertificate = { activeSheet = ActiveSheet.VAULT_CERTIFICATE },
                    onExportReport = { activeSheet = ActiveSheet.EXPORT_REPORT }
                )
                2 -> TransactionHistoryScreen(transactions = transactions)
                3 -> EducationScreen(
                    onOpenAssistant = { activeSheet = ActiveSheet.GOLDEN_ASSISTANT }
                )
                4 -> ProfileScreen(
                    currentUser = currentUser,
                    userGrams = userGoldGrams,
                    onLogout = onLogout,
                    onManageBank = { activeSheet = ActiveSheet.BANK_MANAGEMENT },
                    onBranchLocator = { activeSheet = ActiveSheet.BRANCH_LOCATOR },
                    onAboutApp = { activeSheet = ActiveSheet.ABOUT_APP }
                )
            }
        }
    }

    // Active Bottom Sheets Handler
    when (activeSheet) {
        ActiveSheet.BUY -> {
            BuyGoldBottomSheet(
                onDismiss = { activeSheet = ActiveSheet.NONE },
                onSuccessBuy = { grams, rupiah ->
                    val addedGrams = grams.replace(",", ".").toDoubleOrNull() ?: 0.0
                    onUpdateGrams(userGoldGrams + addedGrams)

                    val newTx = TransactionItem(
                        id = System.currentTimeMillis().toString(),
                        title = "Beli Tabungan Emas",
                        date = "Hari ini, Baru saja",
                        amountGrams = "+$grams gram",
                        amountRupiah = rupiah,
                        isPositive = true,
                        status = "Selesai"
                    )
                    onAddTransaction(newTx)
                }
            )
        }
        ActiveSheet.SELL -> {
            SellGoldBottomSheet(
                userName = currentUser?.fullName ?: "Qeisar",
                availableGrams = userGoldGrams,
                onDismiss = { activeSheet = ActiveSheet.NONE },
                onSuccessSell = { grams, rupiah ->
                    onUpdateGrams((userGoldGrams - grams).coerceAtLeast(0.0))

                    val newTx = TransactionItem(
                        id = System.currentTimeMillis().toString(),
                        title = "Jual Emas (Buyback)",
                        date = "Hari ini, Baru saja",
                        amountGrams = "-${String.format("%.4f", grams)} gram",
                        amountRupiah = rupiah,
                        isPositive = false,
                        status = "Selesai"
                    )
                    onAddTransaction(newTx)
                }
            )
        }
        ActiveSheet.TRANSFER -> {
            TransferGoldBottomSheet(
                availableGrams = userGoldGrams,
                onDismiss = { activeSheet = ActiveSheet.NONE },
                onSuccessTransfer = { recipient, grams ->
                    onUpdateGrams((userGoldGrams - grams).coerceAtLeast(0.0))

                    val newTx = TransactionItem(
                        id = System.currentTimeMillis().toString(),
                        title = "Transfer Emas ke $recipient",
                        date = "Hari ini, Baru saja",
                        amountGrams = "-${String.format("%.4f", grams)} gram",
                        amountRupiah = "Rp " + String.format("%,.0f", grams * goldBuyPrice),
                        isPositive = false,
                        status = "Selesai"
                    )
                    onAddTransaction(newTx)
                }
            )
        }
        ActiveSheet.GADAI -> {
            GadaiGoldBottomSheet(
                availableGrams = userGoldGrams,
                onDismiss = { activeSheet = ActiveSheet.NONE },
                onSuccessGadai = { grams, loanAmount ->
                    val newTx = TransactionItem(
                        id = System.currentTimeMillis().toString(),
                        title = "Pencairan Gadai Syariah",
                        date = "Hari ini, Baru saja",
                        amountGrams = "Agunan ${String.format("%.1f", grams)}g",
                        amountRupiah = loanAmount,
                        isPositive = true,
                        status = "Selesai"
                    )
                    onAddTransaction(newTx)
                }
            )
        }
        ActiveSheet.CETAK -> {
            CetakGoldBottomSheet(
                availableGrams = userGoldGrams,
                onDismiss = { activeSheet = ActiveSheet.NONE },
                onSuccessCetak = { grams, desc ->
                    onUpdateGrams((userGoldGrams - grams).coerceAtLeast(0.0))

                    val newTx = TransactionItem(
                        id = System.currentTimeMillis().toString(),
                        title = "Cetak Emas Fisik $desc",
                        date = "Hari ini, Baru saja",
                        amountGrams = "-${String.format("%.4f", grams)} gram",
                        amountRupiah = "Dikirim",
                        isPositive = false,
                        status = "Diproses"
                    )
                    onAddTransaction(newTx)
                }
            )
        }
        ActiveSheet.CICIL -> {
            CicilGoldBottomSheet(
                onDismiss = { activeSheet = ActiveSheet.NONE },
                onSuccessCicil = { grams, desc ->
                    val newTx = TransactionItem(
                        id = System.currentTimeMillis().toString(),
                        title = "Pembiayaan Cicil Emas $grams g",
                        date = "Hari ini, Baru saja",
                        amountGrams = "+$grams g (Cicil)",
                        amountRupiah = desc,
                        isPositive = true,
                        status = "Aktif"
                    )
                    onAddTransaction(newTx)
                }
            )
        }
        ActiveSheet.AUTO_SAVE -> {
            AutoSaveGoldSheet(onDismiss = { activeSheet = ActiveSheet.NONE })
        }
        ActiveSheet.PRICE_ALERT -> {
            PriceAlertSheet(
                currentBuyPrice = goldBuyPrice,
                onDismiss = { activeSheet = ActiveSheet.NONE }
            )
        }
        ActiveSheet.ZAKAT_GOLD -> {
            ZakatGoldSheet(
                userGrams = userGoldGrams,
                goldBuyPrice = goldBuyPrice,
                onDismiss = { activeSheet = ActiveSheet.NONE }
            )
        }
        ActiveSheet.GLOBAL_SEARCH -> {
            GlobalSearchSheet(
                onDismiss = { activeSheet = ActiveSheet.NONE },
                onSelectSheet = { activeSheet = it }
            )
        }
        ActiveSheet.NOTIFICATIONS -> {
            NotificationBottomSheet(onDismiss = { activeSheet = ActiveSheet.NONE })
        }
        ActiveSheet.HELP -> {
            HelpCenterBottomSheet(onDismiss = { activeSheet = ActiveSheet.NONE })
        }
        ActiveSheet.TRANSACTION_DETAIL -> {
            TransactionDetailSheet(
                item = selectedTransaction,
                onDismiss = { activeSheet = ActiveSheet.NONE }
            )
        }
        ActiveSheet.PROMO_DETAIL, ActiveSheet.PROMO -> {
            PromoListSheet(
                onDismiss = { activeSheet = ActiveSheet.NONE },
                onSelectPromo = { promo ->
                    activeSheet = ActiveSheet.BUY
                }
            )
        }
        ActiveSheet.VAULT_CERTIFICATE -> {
            VaultCertificateSheet(onDismiss = { activeSheet = ActiveSheet.NONE })
        }
        ActiveSheet.BANK_MANAGEMENT -> {
            BankManagementSheet(
                userName = currentUser?.fullName ?: "Qeisar",
                onDismiss = { activeSheet = ActiveSheet.NONE }
            )
        }
        ActiveSheet.BRANCH_LOCATOR -> {
            BranchLocatorSheet(onDismiss = { activeSheet = ActiveSheet.NONE })
        }
        ActiveSheet.GOLDEN_ASSISTANT -> {
            GoldenAssistantSheet(
                userName = currentUser?.fullName ?: "Qeisar",
                goldBuyPrice = goldBuyPrice,
                onDismiss = { activeSheet = ActiveSheet.NONE }
            )
        }
        ActiveSheet.EXPORT_REPORT -> {
            ExportReportSheet(
                userName = currentUser?.fullName ?: "Qeisar",
                userPriority = currentUser?.priorityTier ?: "Prioritas 1 (VVIP Diamond Crown)",
                userGrams = userGoldGrams,
                goldBuyPrice = goldBuyPrice,
                onDismiss = { activeSheet = ActiveSheet.NONE }
            )
        }
        ActiveSheet.ABOUT_APP -> {
            AboutAppSheet(onDismiss = { activeSheet = ActiveSheet.NONE })
        }
        ActiveSheet.GOLDEN_GOAL_CRUD -> {
            GoldenGoalCrudSheet(
                goalToEdit = selectedGoalForEdit,
                onDismiss = { activeSheet = ActiveSheet.NONE; selectedGoalForEdit = null },
                onCreateGoal = { title, grams, deadline ->
                    onAddGoal(title, grams, deadline)
                },
                onUpdateGoal = { id, title, grams, deadline ->
                    onUpdateGoal(id, title, grams, deadline)
                },
                onDeleteGoal = { id ->
                    onDeleteGoal(id)
                }
            )
        }
        ActiveSheet.REGISTER, ActiveSheet.RESET_PIN, ActiveSheet.GOLD_CALCULATOR, ActiveSheet.PIN_AUTH, ActiveSheet.ORDER_TRACKING, ActiveSheet.CUSTOM_ORDER, ActiveSheet.NONE -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun GoldenCrownAppPreview() {
    GoldenCrownTheme {
        MainRootNavigation()
    }
}
