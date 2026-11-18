package com.example.goldencrown.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.model.UserData
import com.example.goldencrown.ui.components.GoldButton
import com.example.goldencrown.ui.components.GoldButtonStyle
import com.example.goldencrown.ui.components.GoldenCrowLogo
import com.example.goldencrown.ui.components.LoadingScreenDialog
import com.example.goldencrown.ui.theme.*

@Composable
fun RegisterScreen(
    onRegisterSubmit: (fullName: String, phone: String, email: String, ktp: String, pin: String) -> Result<UserData>,
    onLoginClick: () -> Unit = {}
) {
    var fullNameInput by remember { mutableStateOf("") }
    var ktpInput by remember { mutableStateOf("") }
    var phoneInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    var pinInput by remember { mutableStateOf("") }
    var isPinVisible by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    if (isLoading) {
        LoadingScreenDialog(
            message = "Memproses Pendaftaran Nasabah Emas...",
            onDismiss = { isLoading = false }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GcBlack)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Logo & Judul
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 12.dp, bottom = 14.dp)
            ) {
                GoldenCrowLogo(size = 64.dp)

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "DAFTAR NASABAH BARU",
                    style = Typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = GcGold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Buka Rekening Tabungan Emas Digital Resmi",
                    style = Typography.bodySmall,
                    color = GcCream,
                    fontWeight = FontWeight.Medium
                )
            }

            // Kartu Pendaftaran
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = GcSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .border(0.5.dp, GcOutline, RoundedCornerShape(12.dp))
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Data Identitas Nasabah Emas",
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = GcCream
                    )

                    errorMessage?.let { err ->
                        Surface(
                            color = GcError.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(0.5.dp, GcError.copy(alpha = 0.5f))
                        ) {
                            Text(err, fontSize = 11.sp, color = GcError, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                        }
                    }

                    // Full Name
                    OutlinedTextField(
                        value = fullNameInput,
                        onValueChange = { fullNameInput = it },
                        label = { Text("Nama Lengkap Sesuai KTP") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = GcGold) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = GcCream,
                            unfocusedTextColor = GcCream,
                            focusedContainerColor = GcSurfaceHigh,
                            unfocusedContainerColor = GcSurfaceHigh,
                            focusedBorderColor = GcGold,
                            unfocusedBorderColor = GcOutline,
                            focusedLabelColor = GcGold,
                            unfocusedLabelColor = GcMuted
                        )
                    )

                    // KTP Number
                    OutlinedTextField(
                        value = ktpInput,
                        onValueChange = { if (it.length <= 16 && it.all { c -> c.isDigit() }) ktpInput = it },
                        label = { Text("NIK KTP (16 Digit)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        leadingIcon = { Icon(imageVector = Icons.Default.Badge, contentDescription = null, tint = GcGold) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = GcCream,
                            unfocusedTextColor = GcCream,
                            focusedContainerColor = GcSurfaceHigh,
                            unfocusedContainerColor = GcSurfaceHigh,
                            focusedBorderColor = GcGold,
                            unfocusedBorderColor = GcOutline,
                            focusedLabelColor = GcGold,
                            unfocusedLabelColor = GcMuted
                        )
                    )

                    // Phone Number
                    OutlinedTextField(
                        value = phoneInput,
                        onValueChange = { if (it.all { c -> c.isDigit() }) phoneInput = it },
                        label = { Text("Nomor Handphone Terdaftar") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = GcGold) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = GcCream,
                            unfocusedTextColor = GcCream,
                            focusedContainerColor = GcSurfaceHigh,
                            unfocusedContainerColor = GcSurfaceHigh,
                            focusedBorderColor = GcGold,
                            unfocusedBorderColor = GcOutline,
                            focusedLabelColor = GcGold,
                            unfocusedLabelColor = GcMuted
                        )
                    )

                    // Email Address
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        label = { Text("Alamat Email Aktif") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = GcGold) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = GcCream,
                            unfocusedTextColor = GcCream,
                            focusedContainerColor = GcSurfaceHigh,
                            unfocusedContainerColor = GcSurfaceHigh,
                            focusedBorderColor = GcGold,
                            unfocusedBorderColor = GcOutline,
                            focusedLabelColor = GcGold,
                            unfocusedLabelColor = GcMuted
                        )
                    )

                    // PIN 6 Digit
                    OutlinedTextField(
                        value = pinInput,
                        onValueChange = { if (it.length <= 6 && it.all { c -> c.isDigit() }) pinInput = it },
                        label = { Text("Buat 6 Digit PIN Keamanan") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = GcGold) },
                        trailingIcon = {
                            IconButton(onClick = { isPinVisible = !isPinVisible }) {
                                Icon(imageVector = if (isPinVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility, contentDescription = null, tint = GcMuted)
                            }
                        },
                        visualTransformation = if (isPinVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = GcCream,
                            unfocusedTextColor = GcCream,
                            focusedContainerColor = GcSurfaceHigh,
                            unfocusedContainerColor = GcSurfaceHigh,
                            focusedBorderColor = GcGold,
                            unfocusedBorderColor = GcOutline,
                            focusedLabelColor = GcGold,
                            unfocusedLabelColor = GcMuted
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    GoldButton(
                        text = "Daftarkan Rekening Emas",
                        onClick = {
                            if (fullNameInput.isBlank() || ktpInput.length != 16 || phoneInput.length < 10 || pinInput.length != 6) {
                                errorMessage = "Harap lengkapi seluruh formulir pendaftaran dengan benar!"
                                return@GoldButton
                            }

                            isLoading = true
                            errorMessage = null
                            val result = onRegisterSubmit(fullNameInput, phoneInput, emailInput, ktpInput, pinInput)
                            isLoading = false
                            if (result.isFailure) {
                                errorMessage = result.exceptionOrNull()?.message ?: "Gagal mendaftar akun!"
                            }
                        },
                        style = GoldButtonStyle.PRIMARY,
                        enabled = fullNameInput.isNotBlank() && ktpInput.length == 16 && phoneInput.length >= 10 && pinInput.length == 6
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Footer
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = GcGold, modifier = Modifier.size(14.dp))
                    Text(
                        text = "Data Anda Dijamin Rahasia & Terdaftar OJK",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GcMuted
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Sudah memiliki akun? ", fontSize = 12.sp, color = GcMuted)
                    Text(
                        text = "Masuk di sini",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = GcGoldLight,
                        modifier = Modifier.clickable { onLoginClick() }
                    )
                }
            }
        }
    }
}
