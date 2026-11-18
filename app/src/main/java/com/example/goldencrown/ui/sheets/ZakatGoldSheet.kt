package com.example.goldencrown.ui.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZakatGoldSheet(
    userGrams: Double = 28.7500,
    goldBuyPrice: Double = 1350000.0,
    onDismiss: () -> Unit
) {
    val nisabGrams = 85.0
    val totalGoldValue = userGrams * goldBuyPrice
    val nisabRupiah = nisabGrams * goldBuyPrice
    val isNisabReached = userGrams >= nisabGrams

    val zakatGrams = if (isNisabReached) userGrams * 0.025 else 0.0
    val zakatRupiah = if (isNisabReached) totalGoldValue * 0.025 else 0.0

    var isPaid by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = SurfaceWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(PegadaianGreenLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.VolunteerActivism, contentDescription = null, tint = PegadaianGreen)
                    }
                    Text("Kalkulator & Zakat Emas (2.5%)", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }

                IconButton(onClick = onDismiss) { Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup") }
            }

            if (isPaid) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = GreenSuccess, modifier = Modifier.size(54.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Pembayaran Zakat Emas Berhasil!", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Zakat emas sebesar Rp ${String.format("%,.0f", zakatRupiah)} telah disalurkan via BAZNAS / Rumah Zakat Pegadaian.", fontSize = 12.sp, color = TextSecondary)
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PegadaianGreen)
                    ) {
                        Text("Selesai & Unduh Bukti Zakat", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(GoldLight)
                        .border(1.dp, GoldBorder, RoundedCornerShape(14.dp))
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total Emas Anda", fontSize = 12.sp, color = TextSecondary)
                            Text("${String.format("%.4f", userGrams)} gram", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Nisab Zakat Emas (Haul 1 Thn)", fontSize = 12.sp, color = TextSecondary)
                            Text("85.00 gram (Rp ${String.format("%,.0f", nisabRupiah)})", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PegadaianGreen)
                        }
                        HorizontalDivider(color = GoldBorder)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Status Nisab", fontSize = 12.sp, color = TextSecondary)
                            Text(
                                text = if (isNisabReached) "Wajib Zakat (Mencapai Nisab)" else "Belum Wajib Zakat (< 85g)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isNisabReached) PegadaianGreen else TextMuted
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Kewajiban Zakat (2.5%)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("Rp ${String.format("%,.0f", zakatRupiah)}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = GoldDark)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { isPaid = true },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PegadaianGreen, contentColor = Color.White),
                    enabled = isNisabReached || zakatRupiah > 0
                ) {
                    Text("Bayar Zakat Emas via BAZNAS", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
