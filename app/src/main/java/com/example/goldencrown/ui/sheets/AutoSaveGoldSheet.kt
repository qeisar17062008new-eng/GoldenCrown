package com.example.goldencrown.ui.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
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
fun AutoSaveGoldSheet(
    onDismiss: () -> Unit
) {
    var frequency by remember { mutableStateOf("Mingguan") }
    var nominalInput by remember { mutableStateOf("100000") }
    var isEnabled by remember { mutableStateOf(true) }
    var isSaved by remember { mutableStateOf(false) }

    val nominalVal = nominalInput.toDoubleOrNull() ?: 0.0

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
                        Icon(imageVector = Icons.Default.Autorenew, contentDescription = null, tint = GoldDark)
                    }
                    Text("Tabungan Emas Otomatis (Auto-Save)", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }

                IconButton(onClick = onDismiss) { Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup") }
            }

            if (isSaved) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = GreenSuccess, modifier = Modifier.size(54.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Auto-Save Berhasil Diaktifkan!", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Pembelian Rp ${String.format("%,.0f", nominalVal)} akan diproses otomatis secara $frequency.", fontSize = 12.sp, color = TextSecondary)
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PegadaianGreen)
                    ) {
                        Text("Selesai", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            } else {
                Text("Pilih Frekuensi Auto-Save", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Mingguan", "Bulanan").forEach { freq ->
                        val isSelected = frequency == freq
                        Button(
                            onClick = { frequency = freq },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) GoldPrimary else GoldLight,
                                contentColor = if (isSelected) Color(0xFF1E1705) else GoldDark
                            )
                        ) {
                            Text(freq, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Text("Nominal Pembelian Otomatis (Rupiah)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                OutlinedTextField(
                    value = nominalInput,
                    onValueChange = { if (it.all { c -> c.isDigit() }) nominalInput = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    prefix = { Text("Rp ", fontWeight = FontWeight.Bold) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                // Quick preset chips
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("50000", "100000", "250000", "500000").forEach { p ->
                        OutlinedButton(
                            onClick = { nominalInput = p },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(2.dp)
                        ) {
                            Text("Rp ${p.toInt()/1000}rb", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { isSaved = true },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = Color(0xFF1E1705)),
                    enabled = nominalVal >= 10000
                ) {
                    Text("Simpan & Aktifkan Auto-Save", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
