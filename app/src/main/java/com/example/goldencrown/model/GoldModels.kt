package com.example.goldencrown.model

import androidx.compose.ui.graphics.vector.ImageVector

enum class ActiveSheet {
    NONE,
    BUY,
    SELL,
    TRANSFER,
    GADAI,
    CETAK,
    CICIL,
    PROMO,
    NOTIFICATIONS,
    HELP,
    TRANSACTION_DETAIL,
    PROMO_DETAIL,
    VAULT_CERTIFICATE,
    BANK_MANAGEMENT,
    BRANCH_LOCATOR,
    REGISTER,
    RESET_PIN,
    GOLD_CALCULATOR,
    GOLDEN_ASSISTANT,
    EXPORT_REPORT,
    GOLDEN_GOAL_CRUD,
    ABOUT_APP,
    AUTO_SAVE,
    PRICE_ALERT,
    ZAKAT_GOLD,
    PIN_AUTH,
    ORDER_TRACKING,
    CUSTOM_ORDER,
    GLOBAL_SEARCH
}

data class UserData(
    val id: String,
    val fullName: String,
    val phone: String,
    val email: String,
    val ktpNumber: String,
    val pin: String,
    val userGrams: Double = 125.5000,
    val kycStatus: String = "Terverifikasi (KTP)",
    val trustScore: String = "Terpercaya (98/100)",
    val memberLevel: String = "Gold Crown Member",
    val priorityTier: String = "Prioritas 1 (VVIP Diamond Crown)",
    val priorityBenefit: String = "Bebas Antre & Dedicated Private Gold Officer",
    val isAdmin: Boolean = false
)

data class QuickService(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val badge: String? = null
)

data class TransactionItem(
    val id: String,
    val title: String,
    val date: String,
    val amountGrams: String,
    val amountRupiah: String,
    val isPositive: Boolean,
    val status: String,
    val type: String = "BUY"
)

data class PromoBanner(
    val id: String,
    val title: String,
    val subtitle: String,
    val code: String,
    val tag: String,
    val discountInfo: String = "Diskon Rp 50.000"
)

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val isRead: Boolean = false
)

data class GoldenGoal(
    val id: String,
    val title: String,
    val targetGrams: Double,
    val currentGrams: Double,
    val deadline: String,
    val iconResName: String = "crown"
) {
    val progressPercentage: Float
        get() = ((currentGrams / targetGrams).coerceIn(0.0, 1.0)).toFloat()
}

data class GoldenMission(
    val id: String,
    val title: String,
    val description: String,
    val rewardPoints: Int,
    val currentProgress: Int,
    val maxProgress: Int,
    val isCompleted: Boolean = false
)

data class UserReward(
    val totalPoints: Int = 1250,
    val memberTier: String = "Gold Crown Member",
    val completedMissionsCount: Int = 8,
    val activeBadges: List<String> = listOf("Penebus Emas", "Investor Rajin", "Sultan Tabungan")
)

data class GoldArticle(
    val id: String,
    val title: String,
    val category: String,
    val readTime: String,
    val summary: String,
    val fullContent: String
)

data class ChatMessage(
    val id: String,
    val text: String,
    val isFromUser: Boolean,
    val timestamp: String
)

data class GoldProduct(
    val id: String,
    val brand: String,
    val weightGrams: Double,
    val priceRupiah: Double,
    val purity: String = "999.9 Fine Gold",
    val inStock: Boolean = true
)

// 🌟 Modul C: Pawn Loan & Vault Location Model
data class PawnLoan(
    val id: String,
    val itemName: String,
    val purityKarat: String, // "24K", "22K", "18K", "16K"
    val weightGrams: Double,
    val estimatedValueRupiah: Double,
    val loanAmountRupiah: Double,
    val interestRateMonthlyPercent: Double, // 2% / 5%
    val storageLocation: String, // e.g., "Brankas Utama A - Rak 3"
    val serialNumber: String,
    val conditionNote: String,
    val dueDate: String,
    val status: String // "Aktif", "Lunas", "Siap Lelang"
)

// 🌟 Modul D: Custom Order Model
data class CustomOrder(
    val id: String,
    val productName: String,
    val customSize: String,
    val purityKarat: String,
    val estimatedWeightGrams: Double,
    val totalPriceRupiah: Double,
    val dpPaidRupiah: Double,
    val status: String, // "Dipesan", "Dikerjakan", "Siap Diambil", "Selesai"
    val orderDate: String
)

// 🌟 Modul E: Gold Purity Rate (24K, 22K, 18K, 16K)
data class GoldPurityRate(
    val karat: String,
    val purityPercent: Double,
    val buyPricePerGram: Double,
    val sellPricePerGram: Double
)

// 🌟 Modul H: Audit Log Activity
data class AuditLog(
    val id: String,
    val timestamp: String,
    val userRole: String,
    val action: String,
    val details: String
)
