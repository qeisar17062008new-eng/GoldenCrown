package com.example.goldencrown.ui.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
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
import com.example.goldencrown.model.GoldenGoal
import com.example.goldencrown.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoldenGoalCrudSheet(
    goalToEdit: GoldenGoal? = null,
    onDismiss: () -> Unit,
    onCreateGoal: (title: String, targetGrams: Double, deadline: String) -> Unit,
    onUpdateGoal: (id: String, title: String, targetGrams: Double, deadline: String) -> Unit,
    onDeleteGoal: (id: String) -> Unit
) {
    var titleInput by remember { mutableStateOf(goalToEdit?.title ?: "") }
    var targetGramsInput by remember { mutableStateOf(goalToEdit?.targetGrams?.toInt()?.toString() ?: "10") }
    var deadlineInput by remember { mutableStateOf(goalToEdit?.deadline ?: "31 Des 2025") }

    val isEditing = goalToEdit != null
    val targetGramsVal = targetGramsInput.toDoubleOrNull() ?: 0.0
    val isValid = titleInput.isNotBlank() && targetGramsVal > 0.0 && deadlineInput.isNotBlank()

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
                        Icon(imageVector = Icons.Default.EmojiEvents, contentDescription = null, tint = GoldDark)
                    }
                    Text(if (isEditing) "Kelola / Edit Golden Goal" else "Tambah Golden Goal Baru", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup")
                }
            }

            // Title Input
            Text("Judul Target Emas", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            OutlinedTextField(
                value = titleInput,
                onValueChange = { titleInput = it },
                placeholder = { Text("cth. Tabungan Emas Pendidikan / Permikahan") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // Target Grams Input
            Text("Target Jumlah Gram Emas", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            OutlinedTextField(
                value = targetGramsInput,
                onValueChange = { if (it.all { c -> c.isDigit() }) targetGramsInput = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                suffix = { Text("gram", fontWeight = FontWeight.Bold) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            // Deadline Input
            Text("Target Tanggal Pencapaian", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            OutlinedTextField(
                value = deadlineInput,
                onValueChange = { deadlineInput = it },
                placeholder = { Text("cth. 31 Des 2025") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (isEditing) {
                    IconButton(
                        onClick = {
                            onDeleteGoal(goalToEdit!!.id)
                            onDismiss()
                        },
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(RedDanger.copy(alpha = 0.15f))
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Hapus Target", tint = RedDanger)
                    }
                }

                Button(
                    onClick = {
                        if (isEditing) {
                            onUpdateGoal(goalToEdit!!.id, titleInput, targetGramsVal, deadlineInput)
                        } else {
                            onCreateGoal(titleInput, targetGramsVal, deadlineInput)
                        }
                        onDismiss()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = Color(0xFF1E1705)),
                    enabled = isValid
                ) {
                    Icon(imageVector = if (isEditing) Icons.Default.Edit else Icons.Default.EmojiEvents, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isEditing) "Simpan Perubahan Target" else "Simpan Target Emas Baru", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
