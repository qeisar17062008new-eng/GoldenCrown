package com.example.goldencrown.ui.sheets

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportReportSheet(
    userName: String = "Qeisar",
    userPriority: String = "Prioritas 1 (VVIP Diamond Crown)",
    userGrams: Double,
    goldBuyPrice: Double,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var isDownloaded by remember { mutableStateOf(false) }
    val totalValue = userGrams * goldBuyPrice

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = SurfaceWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
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
                            .background(GoldLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.PictureAsPdf, contentDescription = null, tint = GoldDark)
                    }
                    Text("Golden Report (Laporan Aset)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }

                IconButton(onClick = onDismiss) { Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(GoldLight)
                    .border(1.dp, GoldBorder, RoundedCornerShape(16.dp))
                    .padding(18.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Ringkasan Laporan Emas Bulanan", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    HorizontalDivider(color = GoldBorder)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Saldo Emas Murni", fontSize = 12.sp, color = TextSecondary)
                        Text("${String.format("%.4f", userGrams)} gram", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Estimasi Nilai Aset", fontSize = 12.sp, color = TextSecondary)
                        Text("Rp ${String.format("%,.0f", totalValue)}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldDark)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Keuntungan Portofolio", fontSize = 12.sp, color = TextSecondary)
                        Text("+14.2% (+Rp 4.812.500)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GreenSuccess)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = { isDownloaded = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PegadaianGreen, contentColor = Color.White)
                ) {
                    Icon(imageVector = Icons.Default.Download, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isDownloaded) "Laporan PDF Berhasil Diunduh! 📄" else "Ekspor Laporan PDF LENGKAP", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, "Golden Report - Portofolio Emas")
                            putExtra(
                                Intent.EXTRA_TEXT,
                                """
                                👑 *GOLDEN CROWN - GOLDEN REPORT SUMMARY*
                                
                                Laporan Ringkasan Aset Emas Digital
                                ----------------------------------
                                • Nasabah          : $userName
                                • Prioritas        : $userPriority
                                • Saldo Emas       : ${String.format("%.4f", userGrams)} gram
                                • Estimasi Nilai   : Rp ${String.format("%,.0f", totalValue)}
                                • Keuntungan Aset  : +14.2% (+Rp 4.812.500)
                                • Status Vault     : Pegadaian Guaranteed 100%
                                
                                Terdaftar & Diawasi oleh OJK (Otoritas Jasa Keuangan).
                                """.trimIndent()
                            )
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Bagikan Laporan Aset via:"))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary)
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = GoldDark)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Bagikan Laporan Aset ke Orang Lain (WhatsApp / Email)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldDark)
                }
            }
        }
    }
}
