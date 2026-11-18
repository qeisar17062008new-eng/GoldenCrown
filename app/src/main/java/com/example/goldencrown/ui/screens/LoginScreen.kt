package com.example.goldencrown.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Fingerprint
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
fun LoginScreen(
    onLoginSubmit: (phoneOrEmail: String, pin: String, isAdminRole: Boolean) -> Result<UserData>,
    onForgotPassword: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    var isAdminMode by remember { mutableStateOf(false) }
    var phoneInput by remember { mutableStateOf("08111111111") }
    var pinInput by remember { mutableStateOf("123456") }
    var isPinVisible by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val presetNasabahList = listOf(
        Triple("Qeisar", "08111111111", "P1 • 125.5g"),
        Triple("Juwita", "08122222222", "P2 • 68.25g"),
        Triple("Alya", "08133333333", "P3 • 32.8g"),
        Triple("Fitrah", "08144444444", "P4 • 14.5g"),
        Triple("Shadira", "08155555555", "P5 • 4.25g")
    )

    if (isLoading) {
        LoadingScreenDialog(
            message = if (isAdminMode) "Memverifikasi Akses Admin Pengelola..." else "Memverifikasi Akun Nasabah Emas...",
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
            // Header Logo & Judul Brand (Terbaca Jelas Emas & Krem)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 12.dp, bottom = 16.dp)
            ) {
                GoldenCrowLogo(size = 72.dp)

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "GOLDENCROW",
                    style = Typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = GcGold,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Toko Perhiasan & Pegadaian Emas",
                    style = Typography.bodySmall,
                    color = GcCream,
                    fontWeight = FontWeight.Medium
                )
            }

            // Kartu Login Form (Latar GcSurface, Border Tipis Emas 0.5dp)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = GcSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .border(0.5.dp, GcOutline, RoundedCornerShape(12.dp))
                        .padding(20.dp)
                ) {
                    // Role Switcher: Nasabah vs Admin
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(GcSurfaceHigh)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Button(
                            onClick = {
                                isAdminMode = false
                                phoneInput = "08111111111"
                                pinInput = "123456"
                                errorMessage = null
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (!isAdminMode) GcGold else GcSurfaceHigh,
                                contentColor = if (!isAdminMode) GcBlack else GcMuted
                            ),
                            contentPadding = PaddingValues(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Nasabah Emas", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                isAdminMode = true
                                phoneInput = "ADMIN-PEGADAIAN"
                                pinInput = "888888"
                                errorMessage = null
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isAdminMode) GcGold else GcSurfaceHigh,
                                contentColor = if (isAdminMode) GcBlack else GcMuted
                            ),
                            contentPadding = PaddingValues(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.AdminPanelSettings, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Admin Crow", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if (isAdminMode) "Login Admin Pengelola 🛡️" else "Pilih Akun Nasabah 👋",
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = GcCream
                    )

                    if (!isAdminMode) {
                        Text(
                            text = "Pilih nasabah untuk masuk cepat & lihat prioritas:",
                            fontSize = 11.sp,
                            color = GcMuted,
                            modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                        )

                        // 5 Nasabah Quick Switcher Chips
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            presetNasabahList.forEach { (name, phone, tag) ->
                                val isSelected = phoneInput == phone
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable {
                                            phoneInput = phone
                                            pinInput = "123456"
                                            errorMessage = null
                                        },
                                    color = if (isSelected) GcGold else GcSurfaceHigh,
                                    shape = RoundedCornerShape(8.dp),
                                    border = androidx.compose.foundation.BorderStroke(
                                        0.5.dp,
                                        if (isSelected) GcGoldLight else GcOutline
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = name,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) GcBlack else GcCream
                                        )
                                        Text(
                                            text = tag,
                                            fontSize = 9.sp,
                                            color = if (isSelected) GcBlack.copy(alpha = 0.8f) else GcGoldLight
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    } else {
                        Text(
                            text = "Khusus Pegawai & Pengelola Resmi Pegadaian",
                            fontSize = 12.sp,
                            color = GcMuted,
                            modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
                        )
                    }

                    errorMessage?.let { err ->
                        Surface(
                            color = GcError.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(0.5.dp, GcError.copy(alpha = 0.5f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                        ) {
                            Text(
                                text = err,
                                fontSize = 11.sp,
                                color = GcError,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    // Input Nomor HP / ID
                    Text(
                        text = if (isAdminMode) "ID Admin / Pegawai" else "Nomor Handphone / ID",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GcCream
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = phoneInput,
                        onValueChange = { phoneInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = if (isAdminMode) Icons.Default.AdminPanelSettings else Icons.Default.Phone,
                                contentDescription = null,
                                tint = GcGold
                            )
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = GcCream,
                            unfocusedTextColor = GcCream,
                            focusedContainerColor = GcSurfaceHigh,
                            unfocusedContainerColor = GcSurfaceHigh,
                            focusedBorderColor = GcGold,
                            unfocusedBorderColor = GcOutline,
                            focusedLeadingIconColor = GcGold,
                            unfocusedLeadingIconColor = GcGold
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Input PIN / Password
                    Text(
                        text = "Password / PIN Keamanan",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GcCream
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = pinInput,
                        onValueChange = { if (it.length <= 6) pinInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = GcGold)
                        },
                        trailingIcon = {
                            IconButton(onClick = { isPinVisible = !isPinVisible }) {
                                Icon(
                                    imageVector = if (isPinVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                    tint = GcMuted
                                )
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
                            focusedLeadingIconColor = GcGold,
                            unfocusedLeadingIconColor = GcGold
                        )
                    )

                    if (!isAdminMode) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = onForgotPassword) {
                                Text("Lupa Password/PIN?", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GcGoldLight)
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Tombol Masuk Utama (Latar Emas #C9A24B dengan Teks Hitam #0F0F0F)
                    GoldButton(
                        text = if (isAdminMode) "Masuk Sebagai Admin" else "Masuk ke Akun Nasabah",
                        onClick = {
                            isLoading = true
                            errorMessage = null
                            val result = onLoginSubmit(phoneInput, pinInput, isAdminMode)
                            isLoading = false
                            if (result.isFailure) {
                                errorMessage = result.exceptionOrNull()?.message ?: "Gagal verifikasi login!"
                            }
                        },
                        style = if (isAdminMode) GoldButtonStyle.SECONDARY else GoldButtonStyle.PRIMARY,
                        enabled = phoneInput.isNotBlank() && pinInput.length == 6
                    )

                    if (!isAdminMode) {
                        Spacer(modifier = Modifier.height(10.dp))

                        // Tombol Sidik Jari (Teks & Ikon Terbaca Terang)
                        OutlinedButton(
                            onClick = {
                                val result = onLoginSubmit(phoneInput, pinInput, false)
                                if (result.isFailure) errorMessage = result.exceptionOrNull()?.message
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GcGold),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = GcSurfaceHigh,
                                contentColor = GcCream
                            )
                        ) {
                            Icon(imageVector = Icons.Default.Fingerprint, contentDescription = null, modifier = Modifier.size(20.dp), tint = GcGold)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Masuk Cepat Sidik Jari",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = GcCream
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer Section
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
                        text = "Terdaftar & Diawasi Resmi OJK",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GcMuted
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Belum punya akun? ", fontSize = 12.sp, color = GcMuted)
                    Text(
                        text = "Daftar Sekarang",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = GcGoldLight,
                        modifier = Modifier.clickable { onRegisterClick() }
                    )
                }
            }
        }
    }
}
