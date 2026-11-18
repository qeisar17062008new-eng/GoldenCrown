package com.example.goldencrown.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.goldencrown.data.model.Customer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import org.json.JSONArray
import org.json.JSONObject

class FirestoreCustomerRepository(context: Context) : CustomerRepository {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("goldencrow_customers", Context.MODE_PRIVATE)

    // Fallback in-memory state saat Firebase belum terhubung ke project live
    private val localCustomersState = MutableStateFlow(loadFromLocalPreferences())

    private val firestore: FirebaseFirestore? by lazy {
        try {
            FirebaseFirestore.getInstance()
        } catch (e: Exception) {
            null
        }
    }

    private fun defaultCustomers(): List<Customer> {
        val now = System.currentTimeMillis()
        return listOf(
            Customer(
                id = "CUST-1001",
                nama = "Qeisar",
                nik = "3174011111110001",
                hp = "08111111111",
                alamat = "Jl. Menteng Raya No. 1, Jakarta Pusat",
                createdAt = now - 86400000L * 5
            ),
            Customer(
                id = "CUST-1002",
                nama = "Juwita",
                nik = "3174022222220002",
                hp = "08122222222",
                alamat = "Jl. Senopati No. 18, Jakarta Selatan",
                createdAt = now - 86400000L * 4
            ),
            Customer(
                id = "CUST-1003",
                nama = "Alya",
                nik = "3174033333330003",
                hp = "08133333333",
                alamat = "Jl. Dago Asri No. 45, Bandung",
                createdAt = now - 86400000L * 3
            ),
            Customer(
                id = "CUST-1004",
                nama = "Fitrah",
                nik = "3174044444440004",
                hp = "08144444444",
                alamat = "Jl. Malioboro No. 12, Yogyakarta",
                createdAt = now - 86400000L * 2
            ),
            Customer(
                id = "CUST-1005",
                nama = "Shadira",
                nik = "3174055555550005",
                hp = "08155555555",
                alamat = "Jl. Pemuda No. 77, Semarang",
                createdAt = now - 86400000L * 1
            )
        )
    }

    private fun loadFromLocalPreferences(): List<Customer> {
        val json = prefs.getString("customers_json", null) ?: return defaultCustomers().also {
            saveToLocalPreferences(it)
        }
        return try {
            val arr = JSONArray(json)
            val list = mutableListOf<Customer>()
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(
                    Customer(
                        id = obj.getString("id"),
                        nama = obj.getString("nama"),
                        nik = obj.getString("nik"),
                        hp = obj.getString("hp"),
                        alamat = obj.getString("alamat"),
                        createdAt = obj.getLong("createdAt")
                    )
                )
            }
            if (list.isEmpty()) defaultCustomers().also { saveToLocalPreferences(it) } else list
        } catch (e: Exception) {
            defaultCustomers().also { saveToLocalPreferences(it) }
        }
    }

    private fun saveToLocalPreferences(list: List<Customer>) {
        val arr = JSONArray()
        for (c in list) {
            val obj = JSONObject().apply {
                put("id", c.id)
                put("nama", c.nama)
                put("nik", c.nik)
                put("hp", c.hp)
                put("alamat", c.alamat)
                put("createdAt", c.createdAt)
            }
            arr.put(obj)
        }
        prefs.edit().putString("customers_json", arr.toString()).apply()
        localCustomersState.value = list
    }

    override fun getCustomers(): Flow<List<Customer>> {
        val db = firestore
        if (db == null) {
            return localCustomersState
        }

        return callbackFlow {
            val listener = db.collection("customers")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        trySend(localCustomersState.value)
                        return@addSnapshotListener
                    }

                    if (snapshot != null && !snapshot.isEmpty) {
                        val customers = snapshot.documents.map { doc ->
                            Customer.fromFirestoreMap(doc.id, doc.data ?: emptyMap())
                        }
                        saveToLocalPreferences(customers)
                        trySend(customers)
                    } else {
                        // Jika Firestore masih kosong, kirim data lokal default
                        trySend(localCustomersState.value)
                    }
                }

            awaitClose { listener.remove() }
        }
    }

    override suspend fun getCustomerById(id: String): Customer? {
        val db = firestore
        if (db != null) {
            try {
                val doc = db.collection("customers").document(id).get().await()
                if (doc.exists()) {
                    return Customer.fromFirestoreMap(doc.id, doc.data ?: emptyMap())
                }
            } catch (_: Exception) {
            }
        }
        return localCustomersState.value.find { it.id == id }
    }

    override suspend fun addCustomer(customer: Customer): Result<String> {
        val generatedId = if (customer.id.isNotBlank()) customer.id else "CUST-${System.currentTimeMillis().toString().takeLast(6)}"
        val newCustomer = customer.copy(id = generatedId)

        val db = firestore
        if (db != null) {
            try {
                db.collection("customers").document(generatedId).set(newCustomer.toFirestoreMap()).await()
            } catch (e: Exception) {
                // Tetap simpan secara lokal jika koneksi Firestore terkendala
            }
        }

        val updated = listOf(newCustomer) + localCustomersState.value.filter { it.id != generatedId }
        saveToLocalPreferences(updated)
        return Result.success(generatedId)
    }

    override suspend fun updateCustomer(customer: Customer): Result<Unit> {
        val db = firestore
        if (db != null) {
            try {
                db.collection("customers").document(customer.id).set(customer.toFirestoreMap()).await()
            } catch (e: Exception) {
                // Fallback simpan lokal
            }
        }

        val updated = localCustomersState.value.map { if (it.id == customer.id) customer else it }
        saveToLocalPreferences(updated)
        return Result.success(Unit)
    }

    override suspend fun deleteCustomer(id: String): Result<Unit> {
        val db = firestore
        if (db != null) {
            try {
                db.collection("customers").document(id).delete().await()
            } catch (e: Exception) {
                // Fallback simpan lokal
            }
        }

        val updated = localCustomersState.value.filter { it.id != id }
        saveToLocalPreferences(updated)
        return Result.success(Unit)
    }
}
