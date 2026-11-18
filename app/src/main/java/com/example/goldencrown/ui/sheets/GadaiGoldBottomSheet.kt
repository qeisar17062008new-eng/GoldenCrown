package com.example.goldencrown.ui.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
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
fun GadaiGoldBottomSheet(
    availableGrams: Double,
    onDismiss: () -> Unit,
    onSuccessGadai: (Double, String) -> Unit
) {
    var selectedGrams by remember { mutableDoubleStateOf(if (availableGrams >= 5.0) 5.0 else availableGrams) }
    var selectedTenor by remember { mutableIntStateOf(120) } // 120 hari

    val goldMarketValue = selectedGrams * 1350000.0
    val maxLoanAmount = goldMarketValue * 0.85 // Max 85% Taksiran Marhun
    val munahFee = maxLoanAmount * (if (selectedTenor == 30) 0.01 else if (selectedTenor == 60) 0.02 else 0.035)

    var isSubmitted by remember { mutableStateOf(false) }

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
                            imageVector = Icons.Default.AccountBalance,
                            contentDescription = null,
                            tint = PegadaianGreen
                        )
                    }
                    Text(
                        text = "Gadai Emas Syariah (RAHN)",
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

            if (isSubmitted) {
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
                        text = "Pengajuan Gadai Disetujui!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Pinjaman Pinjaman (Marhun Bih) sebesar Rp ${String.format("%,.0f", maxLoanAmount)} telah dikirim ke rekening Anda.",
                        fontSize = 13.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            onSuccessGadai(selectedGrams, "Rp ${String.format("%,.0f", maxLoanAmount)}")
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PegadaianGreen)
                    ) {
                        Text("Selesai & Lihat Akad", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            } else {
                Text(
                    text = "Agunan Emas (Jaminan)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(1.0, 2.0, 5.0, 10.0).forEach { g ->
                        val isSelected = selectedGrams == g
                        OutlinedButton(
                            onClick = { selectedGrams = g },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            enabled = g <= availableGrams,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) PegadaianGreen else GoldBorder.copy(alpha = 0.5f)
                            )
                        ) {
                            Text(
                                text = "${g}g",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) PegadaianGreen else TextSecondary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Pilih Jangka Waktu (Tenor)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(30, 60, 120).forEach { tenor ->
                        val isSelected = selectedTenor == tenor
                        Button(
                            onClick = { selectedTenor = tenor },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) PegadaianGreen else PegadaianGreenLight,
                                contentColor = if (isSelected) Color.White else PegadaianGreen
                            )
                        ) {
                            Text(text = "$tenor Hari", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Detail Ringkasan Akad Syariah Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(GoldLight)
                        .border(1.dp, GoldBorder, RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Nilai Taksiran Emas", fontSize = 12.sp, color = TextSecondary)
                            Text("Rp ${String.format("%,.0f", goldMarketValue)}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Sewa Tempat (Mu'nah)", fontSize = 12.sp, color = TextSecondary)
                            Text("Rp ${String.format("%,.0f", munahFee)}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldDark)
                        }
                        HorizontalDivider(color = GoldBorder)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Maksimal Pinjaman (Diterima)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("Rp ${String.format("%,.0f", maxLoanAmount)}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = PegadaianGreen)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { isSubmitted = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PegadaianGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text("Ajukan Gadai Syariah Now", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
