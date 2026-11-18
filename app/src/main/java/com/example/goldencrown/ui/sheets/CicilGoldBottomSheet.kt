package com.example.goldencrown.ui.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditScore
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
fun CicilGoldBottomSheet(
    onDismiss: () -> Unit,
    onSuccessCicil: (Double, String) -> Unit
) {
    var selectedGrams by remember { mutableDoubleStateOf(10.0) }
    var selectedTenorMonths by remember { mutableIntStateOf(12) }

    val totalGoldPrice = selectedGrams * 1350000.0
    val downPayment = totalGoldPrice * 0.15 // DP 15%
    val remainingLoan = totalGoldPrice - downPayment
    val monthlyPayment = (remainingLoan / selectedTenorMonths) * 1.02 // Including Margin

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
                            .background(GoldLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.CreditScore, contentDescription = null, tint = GoldDark)
                    }
                    Text(
                        text = "Cicil Emas Batangan",
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = GreenSuccess, modifier = Modifier.size(64.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Pengajuan Cicil Emas Disetujui!", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Kontrak pembiayaan cicil emas $selectedGrams gram selama $selectedTenorMonths bulan telah aktif.", fontSize = 13.sp, color = TextSecondary, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            onSuccessCicil(selectedGrams, "Cicil $selectedGrams g")
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                    ) {
                        Text("Selesai & Bayar Uang Muka", fontWeight = FontWeight.Bold, color = Color(0xFF1E1705))
                    }
                }
            } else {
                Text("Target Berat Emas Batangan", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextSecondary)
                Spacer(modifier = Modifier.height(6.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(5.0, 10.0, 25.0, 50.0).forEach { g ->
                        val isSelected = selectedGrams == g
                        OutlinedButton(
                            onClick = { selectedGrams = g },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) GoldPrimary else GoldBorder.copy(alpha = 0.5f))
                        ) {
                            Text("${g.toInt()}g", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (isSelected) GoldDark else TextSecondary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Pilih Jangka Waktu Angsuran", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextSecondary)
                Spacer(modifier = Modifier.height(6.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(6, 12, 24, 36).forEach { tenor ->
                        val isSelected = selectedTenorMonths == tenor
                        Button(
                            onClick = { selectedTenorMonths = tenor },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = if (isSelected) GoldPrimary else GoldLight, contentColor = if (isSelected) Color(0xFF1E1705) else GoldDark)
                        ) {
                            Text("$tenor Bln", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

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
                            Text("Uang Muka / DP (15%)", fontSize = 12.sp, color = TextSecondary)
                            Text("Rp ${String.format("%,.0f", downPayment)}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        HorizontalDivider(color = GoldBorder)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Angsuran per Bulan", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("Rp ${String.format("%,.0f", monthlyPayment)}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = PegadaianGreen)
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
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = Color(0xFF1E1705))
                ) {
                    Text("Ajukan Pembiayaan Cicil Emas", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
