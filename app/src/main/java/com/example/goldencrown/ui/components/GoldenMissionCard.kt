package com.example.goldencrown.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.model.GoldenMission
import com.example.goldencrown.ui.theme.*

@Composable
fun GoldenMissionCard(
    onClaimMission: (GoldenMission) -> Unit = {}
) {
    val missions = listOf(
        GoldenMission("1", "Menabung Mingguan", "Beli emas min 0.1g minggu ini", 100, 1, 1, true),
        GoldenMission("2", "Tantangan Bulanan", "Beli emas min 1.0g bulan ini", 500, 1, 1, false),
        GoldenMission("3", "Investor Konsisten", "Lakukan 3x transaksi tabungan emas", 250, 2, 3, false)
    )

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
                            .background(PegadaianGreenLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.MilitaryTech, contentDescription = null, tint = PegadaianGreen)
                    }
                    Column {
                        Text("Golden Mission (Misi Menabung)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text("Selesaikan misi & dapatkan Poin Rewards", fontSize = 11.sp, color = TextSecondary)
                    }
                }

                Surface(color = GoldLight, shape = CircleShape) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = GoldDark, modifier = Modifier.size(12.dp))
                        Text("1,250 Pts", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GoldDark)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                missions.forEach { mission ->
                    MissionItemRow(mission = mission, onClaim = { onClaimMission(mission) })
                }
            }
        }
    }
}

@Composable
private fun MissionItemRow(
    mission: GoldenMission,
    onClaim: () -> Unit
) {
    Surface(
        color = BackgroundWhite,
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, GoldBorder.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(mission.title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Surface(color = GoldLight, shape = RoundedCornerShape(6.dp)) {
                        Text("+${mission.rewardPoints} Pts", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GoldDark, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(mission.description, fontSize = 11.sp, color = TextSecondary)
            }

            if (mission.isCompleted) {
                Button(
                    onClick = onClaim,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PegadaianGreen, contentColor = Color.White),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp)
                ) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Klaim", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                OutlinedButton(
                    onClick = {},
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder)
                ) {
                    Text("${mission.currentProgress}/${mission.maxProgress}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                }
            }
        }
    }
}
