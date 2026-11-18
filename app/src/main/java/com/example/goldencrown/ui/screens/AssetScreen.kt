package com.example.goldencrown.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.ui.components.GoldChartCard
import com.example.goldencrown.ui.theme.*

@Composable
fun AssetScreen(
    userGrams: Double,
    goldBuyPrice: Double,
    onViewCertificate: () -> Unit,
    onExportReport: () -> Unit
) {
    val totalValue = userGrams * goldBuyPrice
    val avgBuyPrice = 1182000.0
    val totalProfit = userGrams * (goldBuyPrice - avgBuyPrice)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Portfolio Summary Header Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF2C2208),
                                Color(0xFF1E1705)
                            )
                        )
                    )
                    .border(1.5.dp, GoldPrimary, RoundedCornerShape(24.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total Portofolio Aset Emas", fontSize = 13.sp, color = GoldLight.copy(alpha = 0.8f))
                        Surface(color = PegadaianGreen, shape = RoundedCornerShape(12.dp)) {
                            Text("Sertifikat Pegadaian", fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Rp ${String.format("%,.0f", totalValue)}", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = GoldPrimary)
                    Text("${String.format("%.4f", userGrams)} gram emas murni 99.99%", fontSize = 13.sp, color = Color.White)

                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider(color = GoldPrimary.copy(alpha = 0.3f))

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text("Keuntungan (Unrealized)", fontSize = 11.sp, color = TextMuted)
                            Text("+Rp ${String.format("%,.0f", totalProfit)} (+14.2%)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GreenSuccess)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Harga Rata-Rata Beli", fontSize = 11.sp, color = TextMuted)
                            Text("Rp ${String.format("%,.0f", avgBuyPrice)} / gr", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GoldLight)
                        }
                    }
                }
            }
        }

        // Golden Report Button
        Button(
            onClick = onExportReport,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PegadaianGreen, contentColor = Color.White)
        ) {
            Icon(imageVector = Icons.Default.PictureAsPdf, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Golden Report • Unduh Laporan PDF", fontWeight = FontWeight.Bold)
        }

        // Gold Insight (Analysis Chart Card)
        GoldChartCard()

        // Vault Storage Guarantee
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceWhite)
                .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(PegadaianGreenLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = PegadaianGreen)
                }
                Column {
                    Text("Jaminan Brankas Pegadaian", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text("No. Sertifikat Vault: PEG-GOLD-8829104", fontSize = 11.sp, color = TextSecondary)
                }
            }
        }

        // Asset Breakdown Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite)
        ) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text("Rincian Aset Emas Anda", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)

                // Asset 1
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(imageVector = Icons.Default.AccountBalanceWallet, contentDescription = null, tint = GoldDark)
                        Column {
                            Text("Tabungan Emas Digital Golden Crown", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("Bisa dicairkan kapan saja", fontSize = 11.sp, color = TextSecondary)
                        }
                    }
                    Text("${String.format("%.4f", userGrams)} g", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }

                HorizontalDivider(color = GoldBorder.copy(alpha = 0.3f))

                // Asset 2
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(imageVector = Icons.Default.Inbox, contentDescription = null, tint = PegadaianGreen)
                        Column {
                            Text("Emas Fisik Batangan", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("Di dalam Vault / Brankas", fontSize = 11.sp, color = TextSecondary)
                        }
                    }
                    Text("0.0000 g", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                }
            }
        }
    }
}
