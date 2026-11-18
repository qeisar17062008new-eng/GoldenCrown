package com.example.goldencrown.ui.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.NotificationsActive
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
import com.example.goldencrown.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceAlertSheet(
    currentBuyPrice: Double = 1350000.0,
    onDismiss: () -> Unit
) {
    var targetPriceInput by remember { mutableStateOf("1300000") }
    var isAlertSaved by remember { mutableStateOf(false) }

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
                            .background(GoldLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.NotificationsActive, contentDescription = null, tint = GoldDark)
                    }
                    Text("Alarm & Notifikasi Harga Emas", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }

                IconButton(onClick = onDismiss) { Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup") }
            }

            Text("Harga Beli Saat Ini: Rp ${String.format("%,.0f", currentBuyPrice)} / gr", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PegadaianGreen)

            if (isAlertSaved) {
                Text("✅ Alarm Notifikasi Berhasil Disimpan! Anda akan diberi tahu secara otomatis saat harga emas mencapai target.", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PegadaianGreen)
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = PegadaianGreen)) {
                    Text("Tutup", fontWeight = FontWeight.Bold, color = Color.White)
                }
            } else {
                Text("Target Harga Beli (Beri Tahu Jika Turun Ke):", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                OutlinedTextField(
                    value = targetPriceInput,
                    onValueChange = { if (it.all { c -> c.isDigit() }) targetPriceInput = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    prefix = { Text("Rp ", fontWeight = FontWeight.Bold) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { isAlertSaved = true },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = Color(0xFF1E1705))
                ) {
                    Text("Simpan Alarm Harga Emas", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
