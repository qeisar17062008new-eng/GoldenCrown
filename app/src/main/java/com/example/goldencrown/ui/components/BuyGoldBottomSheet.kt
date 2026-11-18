package com.example.goldencrown.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MonetizationOn
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
fun BuyGoldBottomSheet(
    onDismiss: () -> Unit,
    onSuccessBuy: (String, String) -> Unit
) {
    var rupiahInput by remember { mutableStateOf("100000") }
    val goldPricePerGram = 1350000.0 // Rp 1.350.000 / gr

    val inputAmount = rupiahInput.toDoubleOrNull() ?: 0.0
    val estimatedGrams = if (inputAmount > 0) inputAmount / goldPricePerGram else 0.0
    val formattedGrams = String.format("%.4f", estimatedGrams)

    var isPurchased by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = SurfaceWhite,
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
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(GoldLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MonetizationOn,
                            contentDescription = null,
                            tint = GoldDark
                        )
                    }
                    Text(
                        text = "Beli Tabungan Emas",
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

            if (isPurchased) {
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
                        text = "Pembelian Emas Berhasil!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "+$formattedGrams gram emas telah ditambahkan ke Saldo Golden Crown Anda.",
                        fontSize = 13.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            onSuccessBuy(formattedGrams, "Rp ${String.format("%,.0f", inputAmount)}")
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PegadaianGreen)
                    ) {
                        Text("Selesai & Lihat Saldo", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            } else {
                // Input Form
                Text(
                    text = "Harga Beli Saat Ini: Rp 1.350.000 / gram",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PegadaianGreen
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Masukkan Nominal Pembelian (Rupiah)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = rupiahInput,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() }) {
                            rupiahInput = input
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    prefix = { Text("Rp ", fontWeight = FontWeight.Bold, color = TextPrimary) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = GoldBorder
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Quick nominal chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("50000", "100000", "500000", "1000000").forEach { preset ->
                        OutlinedButton(
                            onClick = { rupiahInput = preset },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(4.dp),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (rupiahInput == preset) GoldPrimary else GoldBorder.copy(alpha = 0.5f)
                            )
                        ) {
                            Text(
                                text = "Rp ${preset.toInt() / 1000}rb",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (rupiahInput == preset) GoldDark else TextSecondary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Estimated Grams Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(GoldLight)
                        .border(1.dp, GoldBorder, RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Estimasi Emas Diterima",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextSecondary
                        )

                        Text(
                            text = "$formattedGrams gram",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldDark
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { isPurchased = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = Color(0xFF1E1705)
                    ),
                    enabled = inputAmount >= 10000
                ) {
                    Text(
                        text = "Konfirmasi & Bayar",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
