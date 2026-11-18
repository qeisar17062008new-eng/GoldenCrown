package com.example.goldencrown.ui.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Check
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
fun BankManagementSheet(
    userName: String = "Qeisar",
    onDismiss: () -> Unit
) {
    var selectedBank by remember { mutableStateOf("BCA") }

    val banks = listOf(
        Triple("BCA", "Bank Central Asia • **** 8829", "a.n $userName"),
        Triple("Mandiri", "Bank Mandiri • **** 5102", "a.n $userName"),
        Triple("BRI", "Bank Rakyat Indonesia • **** 1948", "a.n $userName")
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
                            .clip(RoundedCornerShape(10.dp))
                            .background(PegadaianGreenLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.AccountBalance, contentDescription = null, tint = PegadaianGreen)
                    }
                    Text("Kelola Rekening Bank", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            banks.forEach { (code, detail, owner) ->
                val isSelected = selectedBank == code
                Surface(
                    onClick = { selectedBank = code },
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) PegadaianGreenLight else BackgroundWhite,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .border(
                            1.dp,
                            if (isSelected) PegadaianGreen else GoldBorder.copy(alpha = 0.4f),
                            RoundedCornerShape(16.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(detail, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text(owner, fontSize = 11.sp, color = TextSecondary)
                        }

                        if (isSelected) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = PegadaianGreen)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PegadaianGreen, contentColor = Color.White)
            ) {
                Text("Simpan Rekening Utama", fontWeight = FontWeight.Bold)
            }
        }
    }
}
