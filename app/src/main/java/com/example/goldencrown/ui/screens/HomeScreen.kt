package com.example.goldencrown.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.model.PromoBanner
import com.example.goldencrown.model.TransactionItem
import com.example.goldencrown.ui.components.*
import com.example.goldencrown.ui.theme.TextMuted

@Composable
fun HomeScreen(
    calculatedGramsFormatted: String,
    calculatedRupiahValue: String,
    userGoldGrams: Double,
    goldBuyPrice: Double,
    goldSellPrice: Double,
    transactionList: List<TransactionItem>,
    goals: List<com.example.goldencrown.model.GoldenGoal> = emptyList(),
    onSelectTab: (Int) -> Unit,
    onOpenSheet: (com.example.goldencrown.model.ActiveSheet) -> Unit,
    onOpenCustomerCrud: () -> Unit = {},
    onGoalClick: (com.example.goldencrown.model.GoldenGoal) -> Unit = {},
    onTransactionClick: (TransactionItem) -> Unit,
    onPromoClick: (PromoBanner) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Main Gold Balance Portfolio Card
        GoldPortfolioCard(
            goldGrams = calculatedGramsFormatted,
            valueRupiah = calculatedRupiahValue,
            profitAmount = "+Rp 4.812.500 (+14.2%)",
            onCardClick = { onSelectTab(1) },
            onBuyClick = { onOpenSheet(com.example.goldencrown.model.ActiveSheet.BUY) },
            onSellClick = { onOpenSheet(com.example.goldencrown.model.ActiveSheet.SELL) },
            onTransferClick = { onOpenSheet(com.example.goldencrown.model.ActiveSheet.TRANSFER) }
        )

        // Today's Gold Price Board
        GoldPriceCard(
            buyPrice = "Rp " + String.format("%,.0f", goldBuyPrice),
            sellPrice = "Rp " + String.format("%,.0f", goldSellPrice),
            onBuyClick = { onOpenSheet(com.example.goldencrown.model.ActiveSheet.BUY) },
            onSellClick = { onOpenSheet(com.example.goldencrown.model.ActiveSheet.SELL) },
            onChartClick = { onSelectTab(1) }
        )

        // Quick Services Grid
        QuickServicesGrid(
            onServiceClick = { service ->
                when (service.id) {
                    "buy" -> onOpenSheet(com.example.goldencrown.model.ActiveSheet.BUY)
                    "sell" -> onOpenSheet(com.example.goldencrown.model.ActiveSheet.SELL)
                    "transfer" -> onOpenSheet(com.example.goldencrown.model.ActiveSheet.TRANSFER)
                    "gadai" -> onOpenSheet(com.example.goldencrown.model.ActiveSheet.GADAI)
                    "cetak" -> onOpenSheet(com.example.goldencrown.model.ActiveSheet.CETAK)
                    "auto_save" -> onOpenSheet(com.example.goldencrown.model.ActiveSheet.AUTO_SAVE)
                    "zakat" -> onOpenSheet(com.example.goldencrown.model.ActiveSheet.ZAKAT_GOLD)
                    "price_alert" -> onOpenSheet(com.example.goldencrown.model.ActiveSheet.PRICE_ALERT)
                    "chart" -> onSelectTab(1)
                    "cicil" -> onOpenSheet(com.example.goldencrown.model.ActiveSheet.CICIL)
                    "promo" -> onOpenSheet(com.example.goldencrown.model.ActiveSheet.PROMO_DETAIL)
                    "customer_crud" -> onOpenCustomerCrud()
                }
            }
        )

        // 🌟 Unique Feature 2: Golden Goal Card
        GoldenGoalCard(
            currentGrams = userGoldGrams,
            goals = goals,
            onAddGoalClick = { onOpenSheet(com.example.goldencrown.model.ActiveSheet.GOLDEN_GOAL_CRUD) },
            onGoalClick = onGoalClick
        )

        // 🌟 Unique Feature 1: Golden Mission Card
        GoldenMissionCard(
            onClaimMission = { onOpenSheet(com.example.goldencrown.model.ActiveSheet.PROMO_DETAIL) }
        )

        // 🌟 Unique Feature 5: Gold Calculator
        GoldCalculatorCard(goldBuyPrice = goldBuyPrice)

        // Gold Price Chart Analysis Card
        GoldChartCard()

        // Promo Banner Carousel
        PromoBannerCarousel(onPromoClick = onPromoClick)

        // Recent Transactions History Card
        TransactionHistoryCard(
            transactions = transactionList,
            onSeeAllClick = { onSelectTab(2) },
            onTransactionClick = onTransactionClick
        )

        // Footer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Golden Crown • Pegadaian Digital",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted
                )
                Text(
                    text = "Terdaftar & Diawasi oleh OJK (Otoritas Jasa Keuangan)",
                    fontSize = 10.sp,
                    color = TextMuted
                )
            }
        }
    }
}
