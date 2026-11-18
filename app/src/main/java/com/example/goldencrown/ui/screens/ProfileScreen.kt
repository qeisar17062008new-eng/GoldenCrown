package com.example.goldencrown.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.ui.theme.*

@Composable
fun ProfileScreen(
    currentUser: com.example.goldencrown.model.UserData? = null,
    userGrams: Double = 125.5000,
    onLogout: () -> Unit = {},
    onManageBank: () -> Unit = {},
    onBranchLocator: () -> Unit = {},
    onAboutApp: () -> Unit = {}
) {
    var isBiometricEnabled by remember { mutableStateOf(true) }

    val name = currentUser?.fullName ?: "Qeisar"
    val initials = name.split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .map { it.first().uppercase() }
        .joinToString("")
        .ifEmpty { "QC" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Profile Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(GoldPrimary, GoldDark)
                            )
                        )
                        .border(2.dp, GoldBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(initials, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(
                    "${currentUser?.email ?: "qeisar@goldencrown.id"} • ${currentUser?.phone ?: "08111111111"}",
                    fontSize = 12.sp,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(10.dp))

                Surface(color = PegadaianGreenLight, shape = RoundedCornerShape(12.dp)) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = PegadaianGreen, modifier = Modifier.size(14.dp))
                        Text(
                            currentUser?.kycStatus ?: "Terverifikasi (KTP)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PegadaianGreen
                        )
                    }
                }
            }
        }

        // 🌟 Tingkat Prioritas & Hak Istimewa Nasabah
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite)
        ) {
            Column(
                modifier = Modifier
                    .border(1.5.dp, GoldPrimary.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Default.WorkspacePremium, contentDescription = null, tint = GoldDark)
                        Text("Tingkat Prioritas Layanan", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    }
                    Surface(color = GoldLight, shape = RoundedCornerShape(8.dp)) {
                        Text(
                            currentUser?.trustScore ?: "100/100",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldDark,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Surface(
                    color = NavyPrimary,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = currentUser?.priorityTier ?: "Prioritas 1 (VVIP Diamond Crown)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currentUser?.priorityBenefit ?: "Bebas Antre & Dedicated Private Gold Officer",
                            fontSize = 11.sp,
                            color = GcCream.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }

        // 🌟 Feature: Golden Rewards, Kepemilikan & Level Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite)
        ) {
            Column(
                modifier = Modifier
                    .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Default.EmojiEvents, contentDescription = null, tint = GoldDark)
                        Text("Kepemilikan & Level Member", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    }
                    Text(
                        "${String.format("%.4f", userGrams)} gr",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldDark
                    )
                }

                Surface(color = GoldLight, shape = RoundedCornerShape(10.dp)) {
                    Text(
                        "👑 ${currentUser?.memberLevel ?: "Gold Crown Member"}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldDark,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text("Lencana Kepemilikan & Loyalitas:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextSecondary)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("🎖️ Nasabah Terverifikasi", "🌟 Prioritas Pegadaian", "🏆 Investor Emas").forEach { badge ->
                        Surface(color = BackgroundWhite, shape = RoundedCornerShape(8.dp), border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.5f))) {
                            Text(badge, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextPrimary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                        }
                    }
                }
            }
        }

        // Account Settings List
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite)
        ) {
            Column(
                modifier = Modifier
                    .border(1.dp, GoldBorder.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Keamanan & Akun", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)

                // Biometric Toggle
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(imageVector = Icons.Default.Fingerprint, contentDescription = null, tint = GoldDark)
                        Text("Login Biometrik / Sidik Jari", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
                    }
                    Switch(
                        checked = isBiometricEnabled,
                        onCheckedChange = { isBiometricEnabled = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PegadaianGreen)
                    )
                }

                HorizontalDivider(color = GoldBorder.copy(alpha = 0.3f))

                // Linked Bank Account
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onManageBank() },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(imageVector = Icons.Default.AccountBalance, contentDescription = null, tint = GoldDark)
                        Column {
                            Text("Rekening Bank Tersambung", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
                            Text("BCA • **** 8829", fontSize = 11.sp, color = TextSecondary)
                        }
                    }
                    Text("Ubah", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PegadaianGreen)
                }

                HorizontalDivider(color = GoldBorder.copy(alpha = 0.3f))

                // Pegadaian Branch Locator
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onBranchLocator() },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = PegadaianGreen)
                        Column {
                            Text("Lokasi Cabang Pegadaian", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
                            Text("Cari outlet fisik terdekat untuk cetak emas", fontSize = 11.sp, color = TextSecondary)
                        }
                    }
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
                }

                HorizontalDivider(color = GoldBorder.copy(alpha = 0.3f))

                // About App Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAboutApp() },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = GoldDark)
                        Column {
                            Text("Tentang Aplikasi & Profil Pegadaian", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
                            Text("Informasi versi v1.0.0 & regulasi OJK", fontSize = 11.sp, color = TextSecondary)
                        }
                    }
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
                }
            }
        }

        // Logout Button
        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RedDanger.copy(alpha = 0.1f), contentColor = RedDanger)
        ) {
            Icon(imageVector = Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Keluar dari Akun Golden Crown", fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
    }
}
