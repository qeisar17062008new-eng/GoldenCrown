package com.example.goldencrown.ui.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun BranchLocatorSheet(
    onDismiss: () -> Unit
) {
    val branches = listOf(
        Pair("Outlet Pegadaian Menteng Raya", "Jl. Menteng Raya No. 42 • 1.2 km"),
        Pair("Outlet Pegadaian Sudirman Center", "Gedung Pegadaian Lt. 1, Sudirman • 2.8 km"),
        Pair("Outlet Pegadaian Galeri24 Senayan", "Senayan City Mall Lt. 2 • 4.5 km")
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
                        Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = PegadaianGreen)
                    }
                    Text("Cabang Pegadaian Terdekat", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            branches.forEach { (name, address) ->
                Surface(
                    color = BackgroundWhite,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .border(1.dp, GoldBorder.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text(address, fontSize = 11.sp, color = TextSecondary)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Buka: 08:00 - 16:00 WIB", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PegadaianGreen)
                        }

                        IconButton(onClick = onDismiss) {
                            Icon(imageVector = Icons.Default.Navigation, contentDescription = null, tint = PegadaianGreen)
                        }
                    }
                }
            }
        }
    }
}
