package com.example.goldencrown.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.model.GoldenGoal
import com.example.goldencrown.ui.theme.*

@Composable
fun GoldenGoalCard(
    currentGrams: Double,
    goals: List<GoldenGoal> = emptyList(),
    onAddGoalClick: () -> Unit = {},
    onGoalClick: (GoldenGoal) -> Unit = {}
) {
    val displayGoals = if (goals.isEmpty()) {
        listOf(
            GoldenGoal("1", "Target Emas Pertama (1g)", 1.0, currentGrams.coerceAtMost(1.0), "31 Des 2024"),
            GoldenGoal("2", "Target Tabungan Emas (10g)", 10.0, currentGrams.coerceAtMost(10.0), "31 Mar 2025")
        )
    } else goals

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
                    Column {
                        Text("Golden Goal (Target Emas)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text("Capai impian kepemilikan emas Anda", fontSize = 11.sp, color = TextSecondary)
                    }
                }

                IconButton(onClick = onAddGoalClick) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Target", tint = PegadaianGreen)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                displayGoals.take(2).forEach { goal ->
                    GoalProgressItem(goal = goal, onClick = { onGoalClick(goal) })
                }
            }
        }
    }
}

@Composable
private fun GoalProgressItem(
    goal: GoldenGoal,
    onClick: () -> Unit = {}
) {
    val progress = (goal.currentGrams / goal.targetGrams).coerceIn(0.0, 1.0).toFloat()
    val isAchieved = goal.currentGrams >= goal.targetGrams

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(BackgroundWhite)
            .clickable { onClick() }
            .border(1.dp, if (isAchieved) GreenSuccess.copy(alpha = 0.5f) else GoldBorder.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(imageVector = Icons.Default.Flag, contentDescription = null, tint = if (isAchieved) GreenSuccess else GoldDark, modifier = Modifier.size(16.dp))
                    Text(goal.title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }

                Text(
                    text = "${String.format("%.2f", goal.currentGrams)} / ${goal.targetGrams.toInt()}g",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isAchieved) GreenSuccess else GoldDark
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Linear Progress Bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape),
                color = if (isAchieved) GreenSuccess else GoldPrimary,
                trackColor = GoldLight
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Target: ${goal.deadline}", fontSize = 10.sp, color = TextMuted)
                Text("${(progress * 100).toInt()}% Tercapai", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (isAchieved) GreenSuccess else TextSecondary)
            }
        }
    }
}
