package com.example.goldencrown.data

import android.content.Context
import android.content.SharedPreferences
import com.example.goldencrown.model.GoldenGoal
import com.example.goldencrown.model.TransactionItem
import com.example.goldencrown.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject

class AppDatabaseRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("goldencrown_db", Context.MODE_PRIVATE)

    // Flow States
    private val _currentUser = MutableStateFlow(getLoggedInUserFromStorage())
    val currentUser: StateFlow<UserData?> = _currentUser.asStateFlow()

    private val _userGrams = MutableStateFlow(getUserGramsFromStorage())
    val userGrams: StateFlow<Double> = _userGrams.asStateFlow()

    private val _goldBuyPrice = MutableStateFlow(getBuyPriceFromStorage())
    val goldBuyPrice: StateFlow<Double> = _goldBuyPrice.asStateFlow()

    private val _goldSellPrice = MutableStateFlow(getSellPriceFromStorage())
    val goldSellPrice: StateFlow<Double> = _goldSellPrice.asStateFlow()

    private val _transactions = MutableStateFlow(getTransactionsFromStorage())
    val transactions: StateFlow<List<TransactionItem>> = _transactions.asStateFlow()

    private val _registeredUsers = MutableStateFlow(getUsersListFromStorage())
    val registeredUsers: StateFlow<List<UserData>> = _registeredUsers.asStateFlow()

    private val _goldenGoals = MutableStateFlow(getGoldenGoalsFromStorage())
    val goldenGoals: StateFlow<List<GoldenGoal>> = _goldenGoals.asStateFlow()

    // Default Seed Users: 5 Nasabah (Qeisar, Juwita, Alya, Fitrah, Shadira) & Admin Pegadaian
    private fun defaultUsers(): List<UserData> {
        return listOf(
            UserData(
                id = "USR-1001",
                fullName = "Qeisar",
                phone = "08111111111",
                email = "qeisar@goldencrown.id",
                ktpNumber = "3174011111110001",
                pin = "123456",
                userGrams = 125.5000,
                kycStatus = "Terverifikasi (KTP & Biometrik)",
                trustScore = "Sangat Terpercaya (100/100)",
                memberLevel = "Diamond Crown Member",
                priorityTier = "Prioritas 1 (VVIP Diamond Crown)",
                priorityBenefit = "Bebas Antre, Dedicated Private Gold Officer & Konsultasi VIP",
                isAdmin = false
            ),
            UserData(
                id = "USR-1002",
                fullName = "Juwita",
                phone = "08122222222",
                email = "juwita@goldencrown.id",
                ktpNumber = "3174022222220002",
                pin = "123456",
                userGrams = 68.2500,
                kycStatus = "Terverifikasi (KTP)",
                trustScore = "Terpercaya (98/100)",
                memberLevel = "Platinum Crown Member",
                priorityTier = "Prioritas 2 (Platinum Priority)",
                priorityBenefit = "Jalur Cepat Transaksi & Promo Cashback Buyback Khusus",
                isAdmin = false
            ),
            UserData(
                id = "USR-1003",
                fullName = "Alya",
                phone = "08133333333",
                email = "alya@goldencrown.id",
                ktpNumber = "3174033333330003",
                pin = "123456",
                userGrams = 32.8000,
                kycStatus = "Terverifikasi (KTP)",
                trustScore = "Terpercaya (95/100)",
                memberLevel = "Gold Crown Member",
                priorityTier = "Prioritas 3 (Gold Priority)",
                priorityBenefit = "Diskon Titipan Brankas 50% & Notifikasi Live Alert",
                isAdmin = false
            ),
            UserData(
                id = "USR-1004",
                fullName = "Fitrah",
                phone = "08144444444",
                email = "fitrah@goldencrown.id",
                ktpNumber = "3174044444440004",
                pin = "123456",
                userGrams = 14.5000,
                kycStatus = "Terverifikasi (KTP)",
                trustScore = "Terpercaya (90/100)",
                memberLevel = "Silver Crown Member",
                priorityTier = "Prioritas 4 (Silver Priority)",
                priorityBenefit = "Bebas Biaya Admin 5x/Bulan & Layanan Cepat",
                isAdmin = false
            ),
            UserData(
                id = "USR-1005",
                fullName = "Shadira",
                phone = "08155555555",
                email = "shadira@goldencrown.id",
                ktpNumber = "3174055555550005",
                pin = "123456",
                userGrams = 4.2500,
                kycStatus = "Terverifikasi (KTP)",
                trustScore = "Terpercaya (85/100)",
                memberLevel = "Bronze Crown Member",
                priorityTier = "Prioritas 5 (Bronze Regular)",
                priorityBenefit = "Panduan Asisten Emas Pemula & Kursus Investasi Gratis",
                isAdmin = false
            ),
            UserData(
                id = "ADM-8888",
                fullName = "Admin Pengelola Pegadaian",
                phone = "ADMIN-PEGADAIAN",
                email = "admin@goldencrown.id",
                ktpNumber = "327500000000001",
                pin = "888888",
                userGrams = 0.0,
                kycStatus = "Admin Certified",
                trustScore = "Otoritas Sistem (100/100)",
                memberLevel = "System Administrator",
                priorityTier = "Administrator Utama",
                priorityBenefit = "Hak Akses Penuh Sistem & Pengelolaan Nasabah",
                isAdmin = true
            )
        )
    }

    private fun getUsersListFromStorage(): List<UserData> {
        val jsonStr = prefs.getString("registered_users_json", null)
        // Clean migration if previous seed data was present
        if (jsonStr == null || jsonStr.contains("Sultan Ageng") || !jsonStr.contains("Qeisar")) {
            val def = defaultUsers()
            saveUsersListToStorage(def)
            return def
        }
        return try {
            val list = mutableListOf<UserData>()
            val arr = JSONArray(jsonStr)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(
                    UserData(
                        id = obj.getString("id"),
                        fullName = obj.getString("fullName"),
                        phone = obj.getString("phone"),
                        email = obj.getString("email"),
                        ktpNumber = obj.getString("ktpNumber"),
                        pin = obj.getString("pin"),
                        userGrams = obj.optDouble("userGrams", 125.50),
                        kycStatus = obj.optString("kycStatus", "Terverifikasi (KTP)"),
                        trustScore = obj.optString("trustScore", "Terpercaya (90/100)"),
                        memberLevel = obj.optString("memberLevel", "Gold Crown Member"),
                        priorityTier = obj.optString("priorityTier", "Prioritas Standar"),
                        priorityBenefit = obj.optString("priorityBenefit", "Layanan Prioritas Standar"),
                        isAdmin = obj.optBoolean("isAdmin", false)
                    )
                )
            }
            if (list.isEmpty()) defaultUsers().also { saveUsersListToStorage(it) } else list
        } catch (e: Exception) {
            defaultUsers().also { saveUsersListToStorage(it) }
        }
    }

    private fun saveUsersListToStorage(users: List<UserData>) {
        val arr = JSONArray()
        for (u in users) {
            val obj = JSONObject().apply {
                put("id", u.id)
                put("fullName", u.fullName)
                put("phone", u.phone)
                put("email", u.email)
                put("ktpNumber", u.ktpNumber)
                put("pin", u.pin)
                put("userGrams", u.userGrams)
                put("kycStatus", u.kycStatus)
                put("trustScore", u.trustScore)
                put("memberLevel", u.memberLevel)
                put("priorityTier", u.priorityTier)
                put("priorityBenefit", u.priorityBenefit)
                put("isAdmin", u.isAdmin)
            }
            arr.put(obj)
        }
        prefs.edit().putString("registered_users_json", arr.toString()).apply()
    }

    private fun getLoggedInUserFromStorage(): UserData? {
        val users = getUsersListFromStorage()
        val savedPhone = prefs.getString("logged_in_phone", "08111111111")
        return users.find { it.phone == savedPhone } ?: users.firstOrNull { !it.isAdmin }
    }

    // Register & Login Authentication Methods
    fun registerNewNasabah(fullName: String, phone: String, email: String, ktp: String, pin: String): Result<UserData> {
        val existing = _registeredUsers.value.find { it.phone == phone || it.email == email }
        if (existing != null) {
            return Result.failure(Exception("Nomor HP / Email sudah terdaftar! Sila login."))
        }

        val newUser = UserData(
            id = "USR-${System.currentTimeMillis().toString().takeLast(6)}",
            fullName = fullName,
            phone = phone,
            email = email,
            ktpNumber = ktp,
            pin = pin,
            userGrams = 0.0,
            kycStatus = "Terverifikasi (KTP)",
            trustScore = "Nasabah Baru (80/100)",
            memberLevel = "Bronze Crown Member",
            priorityTier = "Prioritas 5 (Bronze Regular)",
            priorityBenefit = "Layanan Pemula & Panduan Edukasi Emas",
            isAdmin = false
        )

        val updatedUsers = _registeredUsers.value + newUser
        saveUsersListToStorage(updatedUsers)
        _registeredUsers.value = updatedUsers

        prefs.edit().putString("logged_in_phone", phone).apply()
        _currentUser.value = newUser
        _userGrams.value = 0.0

        return Result.success(newUser)
    }

    fun authenticateUser(phoneOrEmail: String, pin: String, requireAdminRole: Boolean): Result<UserData> {
        val users = _registeredUsers.value
        val user = users.find { (it.phone == phoneOrEmail || it.email == phoneOrEmail) && it.pin == pin }

        if (user == null) {
            return Result.failure(Exception("Nomor Handphone/ID atau PIN tidak sesuai!"))
        }

        if (requireAdminRole && !user.isAdmin) {
            return Result.failure(Exception("Akses Ditolak! Akun Anda adalah Nasabah, bukan Admin Pengelola."))
        }

        if (!requireAdminRole && user.isAdmin) {
            return Result.failure(Exception("Sila gunakan Portal Admin untuk masuk dengan akun Pengelola."))
        }

        prefs.edit().putString("logged_in_phone", user.phone).apply()
        _currentUser.value = user
        _userGrams.value = user.userGrams

        // Update golden goals current grams to match user's gold ownership
        val updatedGoals = _goldenGoals.value.map { g ->
            g.copy(currentGrams = user.userGrams.coerceAtMost(g.targetGrams))
        }
        _goldenGoals.value = updatedGoals

        return Result.success(user)
    }

    // Storage Getters
    private fun getUserGramsFromStorage(): Double {
        val loggedUser = getLoggedInUserFromStorage()
        return loggedUser?.userGrams ?: prefs.getFloat("user_grams", 125.5000f).toDouble()
    }

    private fun getBuyPriceFromStorage(): Double {
        return prefs.getFloat("buy_price", 1350000.0f).toDouble()
    }

    private fun getSellPriceFromStorage(): Double {
        return prefs.getFloat("sell_price", 1240000.0f).toDouble()
    }

    private fun getTransactionsFromStorage(): List<TransactionItem> {
        val jsonString = prefs.getString("transactions_json", null) ?: return defaultTransactions()
        return try {
            val list = mutableListOf<TransactionItem>()
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    TransactionItem(
                        id = obj.getString("id"),
                        title = obj.getString("title"),
                        date = obj.getString("date"),
                        amountGrams = obj.getString("amountGrams"),
                        amountRupiah = obj.getString("amountRupiah"),
                        isPositive = obj.getBoolean("isPositive"),
                        status = obj.getString("status"),
                        type = obj.optString("type", "BUY")
                    )
                )
            }
            list
        } catch (e: Exception) {
            defaultTransactions()
        }
    }

    private fun defaultTransactions(): List<TransactionItem> {
        return listOf(
            TransactionItem("1", "Beli Tabungan Emas", "Hari ini, 09:30 WIB", "+1.0000 gram", "Rp 1.350.000", true, "Selesai"),
            TransactionItem("2", "Transfer Emas ke @siti_gold", "Kemarin, 14:15 WIB", "-0.5000 gram", "Rp 675.000", false, "Selesai"),
            TransactionItem("3", "Cashback Promo Golden Crown!", "12 Mei 2024", "+Rp 50.000", "Rp 50.000", true, "Selesai")
        )
    }

    // 🌟 Golden Goals Persistent CRUD Storage
    private fun getGoldenGoalsFromStorage(): List<GoldenGoal> {
        val jsonString = prefs.getString("golden_goals_json", null) ?: return defaultGoldenGoals()
        return try {
            val list = mutableListOf<GoldenGoal>()
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    GoldenGoal(
                        id = obj.getString("id"),
                        title = obj.getString("title"),
                        targetGrams = obj.getDouble("targetGrams"),
                        currentGrams = obj.getDouble("currentGrams"),
                        deadline = obj.getString("deadline"),
                        iconResName = obj.optString("iconResName", "crown")
                    )
                )
            }
            if (list.isEmpty()) defaultGoldenGoals() else list
        } catch (e: Exception) {
            defaultGoldenGoals()
        }
    }

    private fun defaultGoldenGoals(): List<GoldenGoal> {
        val curGrams = getUserGramsFromStorage()
        return listOf(
            GoldenGoal("1", "Target Emas Pertama (1g)", 1.0, curGrams.coerceAtMost(1.0), "31 Des 2024"),
            GoldenGoal("2", "Target Tabungan Emas (10g)", 10.0, curGrams.coerceAtMost(10.0), "31 Mar 2025"),
            GoldenGoal("3", "Target Sultan Crown (50g)", 50.0, curGrams.coerceAtMost(50.0), "31 Des 2025")
        )
    }

    private fun saveGoldenGoalsToStorage(list: List<GoldenGoal>) {
        val jsonArray = JSONArray()
        for (g in list) {
            val obj = JSONObject().apply {
                put("id", g.id)
                put("title", g.title)
                put("targetGrams", g.targetGrams)
                put("currentGrams", g.currentGrams)
                put("deadline", g.deadline)
                put("iconResName", g.iconResName)
            }
            jsonArray.put(obj)
        }
        prefs.edit().putString("golden_goals_json", jsonArray.toString()).apply()
    }

    // CRUD Methods for Golden Goals
    fun addGoldenGoal(title: String, targetGrams: Double, deadline: String) {
        val curGrams = _userGrams.value
        val newGoal = GoldenGoal(
            id = System.currentTimeMillis().toString(),
            title = title,
            targetGrams = targetGrams,
            currentGrams = curGrams.coerceAtMost(targetGrams),
            deadline = deadline
        )
        val updatedList = listOf(newGoal) + _goldenGoals.value
        saveGoldenGoalsToStorage(updatedList)
        _goldenGoals.value = updatedList
    }

    fun updateGoldenGoal(id: String, newTitle: String, newTargetGrams: Double, newDeadline: String) {
        val curGrams = _userGrams.value
        val updatedList = _goldenGoals.value.map { g ->
            if (g.id == id) {
                g.copy(
                    title = newTitle,
                    targetGrams = newTargetGrams,
                    currentGrams = curGrams.coerceAtMost(newTargetGrams),
                    deadline = newDeadline
                )
            } else g
        }
        saveGoldenGoalsToStorage(updatedList)
        _goldenGoals.value = updatedList
    }

    fun deleteGoldenGoal(id: String) {
        val updatedList = _goldenGoals.value.filter { it.id != id }
        saveGoldenGoalsToStorage(updatedList)
        _goldenGoals.value = updatedList
    }

    // Storage Mutators
    fun updateGoldGrams(newGrams: Double) {
        prefs.edit().putFloat("user_grams", newGrams.toFloat()).apply()
        _userGrams.value = newGrams

        // Update goals progress with new grams
        val updatedGoals = _goldenGoals.value.map { g ->
            g.copy(currentGrams = newGrams.coerceAtMost(g.targetGrams))
        }
        saveGoldenGoalsToStorage(updatedGoals)
        _goldenGoals.value = updatedGoals

        _currentUser.value?.let { user ->
            val updatedUser = user.copy(userGrams = newGrams)
            _currentUser.value = updatedUser
            val updatedList = _registeredUsers.value.map { if (it.phone == user.phone) updatedUser else it }
            saveUsersListToStorage(updatedList)
            _registeredUsers.value = updatedList
        }
    }

    fun updatePrices(buyPrice: Double, sellPrice: Double) {
        prefs.edit()
            .putFloat("buy_price", buyPrice.toFloat())
            .putFloat("sell_price", sellPrice.toFloat())
            .apply()

        _goldBuyPrice.value = buyPrice
        _goldSellPrice.value = sellPrice
    }

    fun addTransaction(tx: TransactionItem) {
        val updatedList = listOf(tx) + _transactions.value
        saveTransactionsToStorage(updatedList)
        _transactions.value = updatedList
    }

    private fun saveTransactionsToStorage(list: List<TransactionItem>) {
        val jsonArray = JSONArray()
        for (item in list) {
            val obj = JSONObject().apply {
                put("id", item.id)
                put("title", item.title)
                put("date", item.date)
                put("amountGrams", item.amountGrams)
                put("amountRupiah", item.amountRupiah)
                put("isPositive", item.isPositive)
                put("status", item.status)
                put("type", item.type)
            }
            jsonArray.put(obj)
        }
        prefs.edit().putString("transactions_json", jsonArray.toString()).apply()
    }
}
