package com.example.goldencrown.ui.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.model.NotificationItem
import com.example.goldencrown.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationBottomSheet(
    onDismiss: () -> Unit
) {
    val notifications = listOf(
        NotificationItem("1", "Harga Emas Naik!", "Harga beli emas hari ini naik Rp 12.000 menjadi Rp 1.350.000/gr.", "10:45 WIB"),
        NotificationItem("2", "Pembelian Emas Berhasil", "Pembelian Tabungan Emas 1.0000 gram telah sukses diverifikasi.", "Kemarin"),
        NotificationItem("3", "Voucher Promo Golden Crown!", "Gunakan kode 'GOLDENCROWN' untuk cashback Rp 250.000.", "2 Hari lalu")
    )

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
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = null, tint = GoldDark)
                    }
                    Text("Notifikasi & Info Golden Crown", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            notifications.forEach { item ->
                Surface(
                    color = BackgroundWhite,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(item.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text(item.time, fontSize = 10.sp, color = TextMuted)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(item.message, fontSize = 12.sp, color = TextSecondary)
                    }
                }
            }
        }
    }
}
