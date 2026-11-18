package com.example.goldencrown.domain.model

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

object CustomerValidator {
    fun validateNama(nama: String): ValidationResult {
        val trimmed = nama.trim()
        return when {
            trimmed.isEmpty() -> ValidationResult(false, "Nama lengkap wajib diisi")
            trimmed.length < 3 -> ValidationResult(false, "Nama minimal 3 karakter")
            else -> ValidationResult(true)
        }
    }

    fun validateNik(nik: String): ValidationResult {
        val clean = nik.trim()
        return when {
            clean.isEmpty() -> ValidationResult(false, "NIK wajib diisi")
            clean.length != 16 -> ValidationResult(false, "NIK harus tepat 16 digit angka (saat ini ${clean.length} digit)")
            !clean.all { it.isDigit() } -> ValidationResult(false, "NIK hanya boleh berisi digit angka")
            else -> ValidationResult(true)
        }
    }

    fun validateHp(hp: String): ValidationResult {
        val clean = hp.trim().replace(" ", "").replace("-", "")
        return when {
            clean.isEmpty() -> ValidationResult(false, "Nomor handphone wajib diisi")
            clean.length < 10 -> ValidationResult(false, "Nomor HP minimal 10 digit (saat ini ${clean.length} digit)")
            clean.length > 15 -> ValidationResult(false, "Nomor HP maksimal 15 digit")
            !clean.all { it.isDigit() || it == '+' } -> ValidationResult(false, "Nomor HP hanya boleh berisi angka")
            else -> ValidationResult(true)
        }
    }

    fun validateAlamat(alamat: String): ValidationResult {
        val trimmed = alamat.trim()
        return when {
            trimmed.isEmpty() -> ValidationResult(false, "Alamat domisili wajib diisi")
            trimmed.length < 5 -> ValidationResult(false, "Alamat minimal 5 karakter")
            else -> ValidationResult(true)
        }
    }
}
