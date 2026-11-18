package com.example.goldencrown.ui.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.LocationOn
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
fun CetakGoldBottomSheet(
    availableGrams: Double,
    onDismiss: () -> Unit,
    onSuccessCetak: (Double, String) -> Unit
) {
    var selectedBrand by remember { mutableStateOf("Galeri24") }
    var selectedWeight by remember { mutableDoubleStateOf(1.0) }
    var addressInput by remember { mutableStateOf("Jl. Menteng Raya No. 42, Jakarta Pusat") }

    val mintingFee = when (selectedWeight) {
        0.5 -> 75000.0
        1.0 -> 100000.0
        5.0 -> 250000.0
        else -> 500000.0
    }

    val isValid = selectedWeight <= availableGrams && addressInput.isNotBlank()
    var isOrdered by remember { mutableStateOf(false) }

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
                        Icon(
                            imageVector = Icons.Default.Inbox,
                            contentDescription = null,
                            tint = GoldDark
                        )
                    }
                    Text(
                        text = "Cetak Emas Fisik Batangan",
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

            if (isOrdered) {
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
                        text = "Pencetakan Fisik Diproses!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Emas Batangan $selectedBrand $selectedWeight gram akan dikirimkan langsung ke alamat Anda dengan asuransi pengiriman penuh.",
                        fontSize = 13.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            onSuccessCetak(selectedWeight, "$selectedBrand $selectedWeight g")
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                    ) {
                        Text("Lacak Pengiriman", fontWeight = FontWeight.Bold, color = Color(0xFF1E1705))
                    }
                }
            } else {
                Text(text = "Pilih Produsen Emas", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextSecondary)
                Spacer(modifier = Modifier.height(6.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Galeri24", "Antam", "Lotus Archi").forEach { brand ->
                        val isSelected = selectedBrand == brand
                        Button(
                            onClick = { selectedBrand = brand },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) GoldPrimary else GoldLight,
                                contentColor = if (isSelected) Color(0xFF1E1705) else GoldDark
                            )
                        ) {
                            Text(text = brand, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Pilih Denominasi Gram", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextSecondary)
                Spacer(modifier = Modifier.height(6.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(0.5, 1.0, 5.0, 10.0).forEach { weight ->
                        val isSelected = selectedWeight == weight
                        OutlinedButton(
                            onClick = { selectedWeight = weight },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            enabled = weight <= availableGrams,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) GoldPrimary else GoldBorder.copy(alpha = 0.5f)
                            )
                        ) {
                            Text("${weight}g", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Alamat Pengiriman", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextSecondary)
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = addressInput,
                    onValueChange = { addressInput = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = PegadaianGreen) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, unfocusedBorderColor = GoldBorder)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(GoldLight)
                        .border(1.dp, GoldBorder, RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Ongkos Cetak & Asuransi", fontSize = 12.sp, color = TextSecondary)
                        Text("Rp ${String.format("%,.0f", mintingFee)}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GoldDark)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { isOrdered = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = Color(0xFF1E1705)),
                    enabled = isValid
                ) {
                    Text("Pesan Cetak Emas Sekarang", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
