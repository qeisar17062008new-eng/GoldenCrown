package com.example.goldencrown.ui.sheets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPinSheet(
    onDismiss: () -> Unit
) {
    var phoneInput by remember { mutableStateOf("08111111111") }
    var newPinInput by remember { mutableStateOf("") }
    var isResetDone by remember { mutableStateOf(false) }

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
                Text("Lupa / Reset PIN Keamanan", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                IconButton(onClick = onDismiss) { Icon(imageVector = Icons.Default.Close, contentDescription = null) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isResetDone) {
                Text("PIN Keamanan Anda Berhasil Diperbarui!", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PegadaianGreen)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PegadaianGreen)
                ) {
                    Text("Kembali ke Login", fontWeight = FontWeight.Bold, color = Color.White)
                }
            } else {
                OutlinedTextField(
                    value = phoneInput,
                    onValueChange = { phoneInput = it },
                    label = { Text("Nomor Handphone Terdaftar") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = GoldDark) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = newPinInput,
                    onValueChange = { if (it.length <= 6) newPinInput = it },
                    label = { Text("Masukkan 6 Digit PIN Baru") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = GoldDark) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { isResetDone = true },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PegadaianGreen, contentColor = Color.White),
                    enabled = phoneInput.length >= 10 && newPinInput.length == 6
                ) {
                    Text("Reset & Simpan PIN Baru", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
