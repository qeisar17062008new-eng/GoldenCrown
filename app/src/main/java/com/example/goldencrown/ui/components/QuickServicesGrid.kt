package com.example.goldencrown.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.model.QuickService
import com.example.goldencrown.ui.theme.*

@Composable
fun QuickServicesGrid(
    onServiceClick: (QuickService) -> Unit = {}
) {
    val services = listOf(
        QuickService("buy", "Beli Emas", Icons.Default.AddCard, "Beli"),
        QuickService("sell", "Jual Emas", Icons.Default.Sell, "Cair"),
        QuickService("transfer", "Transfer", Icons.Default.SwapHoriz),
        QuickService("gadai", "Gadai Emas", Icons.Default.AccountBalance, "Syariah"),
        QuickService("cetak", "Cetak Emas", Icons.Default.Inbox),
        QuickService("customer_crud", "Pelanggan", Icons.Default.People, "CRUD"),
        QuickService("zakat", "Zakat Emas", Icons.Default.VolunteerActivism, "2.5%"),
        QuickService("price_alert", "Alarm Harga", Icons.Default.NotificationsActive)
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
                .padding(16.dp)
        ) {
            Text(
                text = "Layanan & Fitur Emas Digital",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 4 x 2 Grid
            val rows = services.chunked(4)
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rows.forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        rowItems.forEach { service ->
                            ServiceGridItem(
                                service = service,
                                onClick = { onServiceClick(service) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ServiceGridItem(
    service: QuickService,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(72.dp)
            .clickable { onClick() }
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (service.id == "buy") GoldLight
                        else if (service.id == "gadai" || service.id == "zakat") PegadaianGreenLight
                        else GoldLight.copy(alpha = 0.6f)
                    )
                    .border(
                        1.dp,
                        if (service.id == "gadai" || service.id == "zakat") PegadaianGreen.copy(alpha = 0.3f)
                        else GoldBorder.copy(alpha = 0.6f),
                        RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = service.icon,
                    contentDescription = service.title,
                    tint = if (service.id == "gadai" || service.id == "zakat") PegadaianGreen else GoldDark,
                    modifier = Modifier.size(24.dp)
                )
            }

            service.badge?.let { badge ->
                Surface(
                    color = if (badge == "Syariah" || badge == "2.5%") PegadaianGreen else GoldPrimary,
                    shape = CircleShape,
                    modifier = Modifier.offset(x = 6.dp, y = (-4).dp)
                ) {
                    Text(
                        text = badge,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = service.title,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimary,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}
