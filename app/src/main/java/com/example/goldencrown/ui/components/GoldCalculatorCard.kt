package com.example.goldencrown.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
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

@Composable
fun GoldCalculatorCard(
    goldBuyPrice: Double = 1350000.0
) {
    var rupiahInput by remember { mutableStateOf("500000") }
    var targetGramsInput by remember { mutableStateOf("10") }
    var targetMonthsInput by remember { mutableStateOf("12") }

    val currentRupiah = rupiahInput.toDoubleOrNull() ?: 0.0
    val calculatedGrams = if (currentRupiah > 0) currentRupiah / goldBuyPrice else 0.0

    val targetGrams = targetGramsInput.toDoubleOrNull() ?: 10.0
    val targetMonths = targetMonthsInput.toIntOrNull() ?: 12
    val totalTargetRupiah = targetGrams * goldBuyPrice
    val monthlySavingRupiah = if (targetMonths > 0) totalTargetRupiah / targetMonths else 0.0

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .border(1.dp, GoldBorder.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                .padding(18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(GoldLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Calculate, contentDescription = null, tint = GoldDark)
                }
                Column {
                    Text("Kalkulator Emas & Simulasi Target", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text("Hitung konversi Rupiah & estimasi tabungan bulanan", fontSize = 11.sp, color = TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Calculator 1: Rupiah to Gram Converter
            Text("1. Konversi Rupiah ke Gram", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldDark)
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = rupiahInput,
                onValueChange = { if (it.all { char -> char.isDigit() }) rupiahInput = it },
                modifier = Modifier.fillMaxWidth(),
                prefix = { Text("Rp ", fontWeight = FontWeight.Bold, color = TextPrimary) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(GoldLight)
                    .border(1.dp, GoldBorder, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Estimasi Diterima", fontSize = 12.sp, color = TextSecondary)
                    Text("${String.format("%.4f", calculatedGrams)} gram", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = GoldDark)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Calculator 2: Monthly Saving Simulation
            Text("2. Simulasi Target Tabungan Bulanan", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PegadaianGreen)
            Spacer(modifier = Modifier.height(4.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Target (Gram)", fontSize = 11.sp, color = TextSecondary)
                    OutlinedTextField(
                        value = targetGramsInput,
                        onValueChange = { targetGramsInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Tenor (Bulan)", fontSize = 11.sp, color = TextSecondary)
                    OutlinedTextField(
                        value = targetMonthsInput,
                        onValueChange = { targetMonthsInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(PegadaianGreenLight)
                    .border(1.dp, PegadaianGreen.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Tabungan/Bulan", fontSize = 12.sp, color = TextSecondary)
                    Text("Rp ${String.format("%,.0f", monthlySavingRupiah)} / bln", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PegadaianGreen)
                }
            }
        }
    }
}
