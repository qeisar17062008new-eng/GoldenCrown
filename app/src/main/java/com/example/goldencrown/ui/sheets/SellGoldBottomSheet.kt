package com.example.goldencrown.ui.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Sell
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
fun SellGoldBottomSheet(
    userName: String = "Qeisar",
    availableGrams: Double,
    onDismiss: () -> Unit,
    onSuccessSell: (Double, String) -> Unit
) {
    var gramsInput by remember { mutableStateOf("1.0000") }
    val sellPricePerGram = 1240000.0 // Buyback price Rp 1.240.000 / gr

    val inputGrams = gramsInput.replace(",", ".").toDoubleOrNull() ?: 0.0
    val estimatedRupiah = inputGrams * sellPricePerGram
    val isAmountValid = inputGrams > 0 && inputGrams <= availableGrams

    var isSold by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = SurfaceWhite,
        scrimColor = Color.Black.copy(alpha = 0.5f)
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(PegadaianGreenLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sell,
                            contentDescription = null,
                            tint = PegadaianGreen
                        )
                    }
                    Text(
                        text = "Jual Emas (Buyback)",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isSold) {
                // Success State
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Berhasil",
                        tint = GreenSuccess,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Penjualan Emas Berhasil!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Dana sebesar Rp ${String.format("%,.0f", estimatedRupiah)} langsung ditransfer ke rekening Bank BCA Anda.",
                        fontSize = 13.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            onSuccessSell(inputGrams, "Rp ${String.format("%,.0f", estimatedRupiah)}")
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PegadaianGreen)
                    ) {
                        Text("Selesai", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            } else {
                Text(
                    text = "Harga Buyback Hari Ini: Rp 1.240.000 / gram",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PegadaianGreen
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Saldo Tersedia: ${String.format("%.4f", availableGrams)} gram",
                    fontSize = 11.sp,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Jumlah Emas yang Dijual (Gram)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = gramsInput,
                    onValueChange = { gramsInput = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    suffix = { Text("gram", fontWeight = FontWeight.Bold, color = TextPrimary) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PegadaianGreen,
                        unfocusedBorderColor = GoldBorder
                    ),
                    isError = inputGrams > availableGrams
                )

                if (inputGrams > availableGrams) {
                    Text(
                        text = "Jumlah gram melebihi saldo tersedia",
                        fontSize = 11.sp,
                        color = RedDanger,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Quick Gram preset chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(0.5, 1.0, 2.0, 5.0).forEach { preset ->
                        OutlinedButton(
                            onClick = { gramsInput = preset.toString() },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(4.dp),
                            enabled = preset <= availableGrams,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (gramsInput == preset.toString()) PegadaianGreen else GoldBorder.copy(alpha = 0.5f)
                            )
                        ) {
                            Text(
                                text = "${preset}g",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (gramsInput == preset.toString()) PegadaianGreen else TextSecondary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Bank Account Info Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(BackgroundWhite)
                        .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountBalance,
                                contentDescription = null,
                                tint = PegadaianGreen
                            )
                            Column {
                                Text("Bank BCA • **** 8829", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                Text("a.n $userName", fontSize = 11.sp, color = TextSecondary)
                            }
                        }

                        Text("Ubah", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PegadaianGreen)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Payout Total Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(PegadaianGreenLight)
                        .border(1.dp, PegadaianGreen.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total Pencairan (Penerimaan)", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextSecondary)
                        Text("Rp ${String.format("%,.0f", estimatedRupiah)}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PegadaianGreen)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { isSold = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PegadaianGreen,
                        contentColor = Color.White
                    ),
                    enabled = isAmountValid
                ) {
                    Text("Konfirmasi Jual Emas", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
