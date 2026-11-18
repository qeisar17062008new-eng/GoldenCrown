package com.example.goldencrown.data.model

import com.google.firebase.Timestamp

data class Customer(
    val id: String = "",
    val nama: String = "",
    val nik: String = "",
    val hp: String = "",
    val alamat: String = "",
    val createdAt: Long = System.currentTimeMillis()
) {
    // Konversi ke map untuk penyimpanan Firestore
    fun toFirestoreMap(): Map<String, Any> {
        return mapOf(
            "nama" to nama,
            "nik" to nik,
            "hp" to hp,
            "alamat" to alamat,
            "createdAt" to createdAt
        )
    }

    companion object {
        fun fromFirestoreMap(id: String, map: Map<String, Any?>): Customer {
            val createdVal = map["createdAt"]
            val parsedTime = when (createdVal) {
                is Number -> createdVal.toLong()
                is Timestamp -> createdVal.toDate().time
                else -> System.currentTimeMillis()
            }

            return Customer(
                id = id,
                nama = map["nama"] as? String ?: "",
                nik = map["nik"] as? String ?: "",
                hp = map["hp"] as? String ?: "",
                alamat = map["alamat"] as? String ?: "",
                createdAt = parsedTime
            )
        }
    }
}
