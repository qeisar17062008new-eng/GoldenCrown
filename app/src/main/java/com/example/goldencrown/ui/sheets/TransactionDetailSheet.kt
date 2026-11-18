package com.example.goldencrown.ui.sheets

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.model.TransactionItem
import com.example.goldencrown.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailSheet(
    item: TransactionItem?,
    onDismiss: () -> Unit
) {
    if (item == null) return
    val context = LocalContext.current

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
                            .clip(CircleShape)
                            .background(GoldLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.ReceiptLong, contentDescription = null, tint = GoldDark)
                    }
                    Text("Detail Transaksi", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Status Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(PegadaianGreenLight)
                    .border(1.dp, PegadaianGreen.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = PegadaianGreen, modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(item.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text("Transaksi ${item.status} • Verified", fontSize = 12.sp, color = PegadaianGreen, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Receipt Breakdown
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = BackgroundWhite)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("No. Referensi", fontSize = 12.sp, color = TextSecondary)
                        Text("TRX-GC-${item.id.takeLast(8)}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Waktu Transaksi", fontSize = 12.sp, color = TextSecondary)
                        Text(item.date, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    }
                    HorizontalDivider(color = GoldBorder.copy(alpha = 0.3f))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Jumlah Gram Emas", fontSize = 12.sp, color = TextSecondary)
                        Text(item.amountGrams, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (item.isPositive) GreenSuccess else TextPrimary)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Nominal (Rp)", fontSize = 12.sp, color = TextSecondary)
                        Text(item.amountRupiah, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = GoldDark)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Native Android Share Intent Action Button
            Button(
                onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "Bukti Resmi Transaksi Golden Crown")
                        putExtra(
                            Intent.EXTRA_TEXT,
                            """
                            👑 *GOLDEN CROWN x PEGADAIAN DIGITAL*
                            
                            *Bukti Resmi Transaksi Emas Digital*
                            ------------------------------------
                            • Jenis Transaksi : ${item.title}
                            • No. Referensi   : TRX-GC-${item.id.takeLast(8)}
                            • Waktu Transaksi  : ${item.date}
                            • Jumlah Emas     : ${item.amountGrams}
                            • Total Nominal   : ${item.amountRupiah}
                            • Status          : ${item.status} (Verified OJK)
                            
                            Terdaftar & Diawasi oleh OJK (Otoritas Jasa Keuangan).
                            Aplikasi Golden Crown: Your Gold, Your Future.
                            """.trimIndent()
                        )
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Bagikan Bukti Transaksi Via:"))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = Color(0xFF1E1705))
            ) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Bagikan Bukti Transaksi (WhatsApp / Email)", fontWeight = FontWeight.Bold)
            }
        }
    }
}
