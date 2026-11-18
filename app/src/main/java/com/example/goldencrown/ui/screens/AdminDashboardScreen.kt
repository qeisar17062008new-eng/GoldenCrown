package com.example.goldencrown.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.model.UserData
import com.example.goldencrown.ui.theme.*

@Composable
fun AdminDashboardScreen(
    registeredUsers: List<UserData> = emptyList(),
    currentBuyPrice: Double,
    currentSellPrice: Double,
    onUpdatePrices: (Double, Double) -> Unit,
    onOpenCustomerCrud: () -> Unit = {},
    onLogout: () -> Unit
) {
    var buyPriceInput by remember { mutableStateOf(currentBuyPrice.toInt().toString()) }
    var sellPriceInput by remember { mutableStateOf(currentSellPrice.toInt().toString()) }
    var priceUpdateMessage by remember { mutableStateOf<String?>(null) }

    val nasabahList = registeredUsers.filter { !it.isAdmin }

    // Dummy Pending Approvals
    var kycList by remember {
        mutableStateOf(
            listOf(
                "Budi Santoso (3174****8819)",
                "Siti Rahmawati (3275****1902)"
            )
        )
    }

    var gadaiList by remember {
        mutableStateOf(
            listOf(
                "Gadai 5g Emas • Rp 5.737.500 (Ahmad Fauzi)",
                "Cetak Emas 10g Galeri24 (Dewi Sartika)"
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Admin Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = PegadaianGreen)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.AdminPanelSettings, contentDescription = null, tint = Color.White, modifier = Modifier.size(26.dp))
                    }

                    Column {
                        Text("Panel Admin Pegadaian", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text("Sistem Pengelola Harga & Nasabah", fontSize = 11.sp, color = PegadaianGreenLight)
                    }
                }

                IconButton(onClick = onLogout) {
                    Icon(imageVector = Icons.Default.Logout, contentDescription = "Keluar", tint = Color.White)
                }
            }
        }

        // Section 1: Update Live Gold Price
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite)
        ) {
            Column(
                modifier = Modifier
                    .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = GoldDark)
                    Text("Kelora Harga Emas Realtime", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }

                Text("Ubah harga beli & jual emas yang tampil ke seluruh nasabah:", fontSize = 12.sp, color = TextSecondary)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Harga Beli (Rp / g)", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = buyPriceInput,
                            onValueChange = { buyPriceInput = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Harga Buyback (Rp / g)", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = sellPriceInput,
                            onValueChange = { sellPriceInput = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PegadaianGreen)
                        )
                    }
                }

                priceUpdateMessage?.let { msg ->
                    Text(text = msg, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GreenSuccess)
                }

                Button(
                    onClick = {
                        val newBuy = buyPriceInput.toDoubleOrNull() ?: currentBuyPrice
                        val newSell = sellPriceInput.toDoubleOrNull() ?: currentSellPrice
                        onUpdatePrices(newBuy, newSell)
                        priceUpdateMessage = "✅ Harga Emas Berhasil Diperbarui secara Live!"
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = Color(0xFF1E1705))
                ) {
                    Text("Update Harga Emas Sekarang", fontWeight = FontWeight.Bold)
                }
            }
        }

        // Section: Daftar Akun Nasabah, Prioritas & Kepemilikan
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite)
        ) {
            Column(
                modifier = Modifier
                    .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Default.People, contentDescription = null, tint = GcGold)
                        Text("Daftar Akun Nasabah & Prioritas", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = GcCream)
                    }
                    Button(
                        onClick = onOpenCustomerCrud,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GcGold, contentColor = GcBlack),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.ManageAccounts, contentDescription = null, modifier = Modifier.size(16.dp), tint = GcBlack)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Kelola Pelanggan (CRUD)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GcBlack)
                    }
                }

                Text(
                    "Tingkat prioritas dan total kepemilikan aset emas setiap nasabah:",
                    fontSize = 12.sp,
                    color = GcMuted
                )

                nasabahList.forEach { nasabah ->
                    val initials = nasabah.fullName.take(2).uppercase()
                    val totalRp = nasabah.userGrams * currentBuyPrice

                    Surface(
                        color = CreamBackground,
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.6f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(NavyPrimary),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(initials, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GoldPrimary)
                                    }

                                    Column {
                                        Text(nasabah.fullName, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                        Text(nasabah.phone, fontSize = 11.sp, color = TextSecondary)
                                    }
                                }

                                Surface(
                                    color = GoldPrimary.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        nasabah.priorityTier.take(11), // e.g. "Prioritas 1"
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldDark,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(color = GoldBorder.copy(alpha = 0.3f))
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Kepemilikan Emas:", fontSize = 10.sp, color = TextMuted)
                                    Text(
                                        "${String.format("%.4f", nasabah.userGrams)} gram",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldPrimary
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text("Estimasi Nilai:", fontSize = 10.sp, color = TextMuted)
                                    Text(
                                        "Rp ${String.format("%,.0f", totalRp)}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PegadaianGreen
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "👑 Benefit: ${nasabah.priorityBenefit}",
                                fontSize = 10.sp,
                                color = TextSecondary
                            )
                        }
                    }
                }
            }
        }

        // Section 2: KYC Approvals
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite)
        ) {
            Column(
                modifier = Modifier.border(1.dp, GoldBorder.copy(alpha = 0.4f), RoundedCornerShape(20.dp)).padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Verifikasi KYC Nasabah Baru (${kycList.size})", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)

                if (kycList.isEmpty()) {
                    Text("Tidak ada antrean verifikasi KYC.", fontSize = 12.sp, color = TextMuted)
                } else {
                    kycList.forEach { user ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(user, fontSize = 12.sp, color = TextPrimary)
                            Button(
                                onClick = { kycList = kycList.filter { it != user } },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = PegadaianGreen),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp)
                            ) {
                                Text("Setujui KTP", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Section 3: Gadai & Cetak Approvals
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite)
        ) {
            Column(
                modifier = Modifier.border(1.dp, GoldBorder.copy(alpha = 0.4f), RoundedCornerShape(20.dp)).padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Persetujuan Gadai & Cetak (${gadaiList.size})", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)

                if (gadaiList.isEmpty()) {
                    Text("Semua pengajuan telah diproses.", fontSize = 12.sp, color = TextMuted)
                } else {
                    gadaiList.forEach { req ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(req, fontSize = 11.sp, color = TextPrimary, modifier = Modifier.weight(1f))
                            Button(
                                onClick = { gadaiList = gadaiList.filter { it != req } },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = Color(0xFF1E1705)),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp)
                            ) {
                                Text("Setujui", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Logout Button
        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RedDanger.copy(alpha = 0.15f), contentColor = RedDanger)
        ) {
            Icon(imageVector = Icons.Default.Logout, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Keluar dari Mode Admin", fontWeight = FontWeight.Bold)
        }
    }
}
