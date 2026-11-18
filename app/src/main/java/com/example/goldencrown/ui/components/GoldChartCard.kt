package com.example.goldencrown.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.ui.theme.*

data class TimeframeChartData(
    val timeframeKey: String,
    val label: String,
    val growthText: String,
    val startPrice: String,
    val endPrice: String,
    val timelineLabels: List<String>,
    val yNormalizedHeights: List<Float>
)

data class YearlyGoldPrice(
    val year: String,
    val priceRupiah: String,
    val changeText: String,
    val isUp: Boolean
)

@Composable
fun GoldChartCard() {
    var selectedTimeframe by remember { mutableStateOf("1T") }
    val timeframes = listOf("1M", "3M", "6M", "1T", "5T")

    val timeframeMap = mapOf(
        "1M" to TimeframeChartData(
            timeframeKey = "1M",
            label = "1 Bulan Terakhir",
            growthText = "+1.85% (+Rp 25.000)",
            startPrice = "Rp 1.325.000",
            endPrice = "Rp 1.350.000",
            timelineLabels = listOf("1 Nov", "10 Nov", "20 Nov", "Sekarang"),
            yNormalizedHeights = listOf(0.85f, 0.75f, 0.80f, 0.50f, 0.40f, 0.15f, 0.10f)
        ),
        "3M" to TimeframeChartData(
            timeframeKey = "3M",
            label = "3 Bulan Terakhir",
            growthText = "+4.65% (+Rp 60.000)",
            startPrice = "Rp 1.290.000",
            endPrice = "Rp 1.350.000",
            timelineLabels = listOf("Sep", "Okt", "Nov", "Sekarang"),
            yNormalizedHeights = listOf(0.90f, 0.70f, 0.65f, 0.45f, 0.30f, 0.20f, 0.10f)
        ),
        "6M" to TimeframeChartData(
            timeframeKey = "6M",
            label = "6 Bulan Terakhir",
            growthText = "+9.75% (+Rp 120.000)",
            startPrice = "Rp 1.230.000",
            endPrice = "Rp 1.350.000",
            timelineLabels = listOf("Jun", "Ags", "Okt", "Sekarang"),
            yNormalizedHeights = listOf(0.95f, 0.80f, 0.60f, 0.50f, 0.35f, 0.25f, 0.10f)
        ),
        "1T" to TimeframeChartData(
            timeframeKey = "1T",
            label = "1 Tahun Terakhir (2024)",
            growthText = "+18.42% (+Rp 210.000)",
            startPrice = "Rp 1.140.000",
            endPrice = "Rp 1.350.000",
            timelineLabels = listOf("Jan 24", "Apr 24", "Jul 24", "Sekarang"),
            yNormalizedHeights = listOf(0.95f, 0.82f, 0.70f, 0.45f, 0.50f, 0.20f, 0.08f)
        ),
        "5T" to TimeframeChartData(
            timeframeKey = "5T",
            label = "5 Tahun (Tahun ke Tahun)",
            growthText = "+45.16% (+Rp 420.000)",
            startPrice = "Rp 930.000",
            endPrice = "Rp 1.350.000",
            timelineLabels = listOf("2021", "2022", "2023", "2024", "2025"),
            yNormalizedHeights = listOf(0.98f, 0.85f, 0.60f, 0.30f, 0.25f, 0.15f, 0.05f)
        )
    )

    val yearlyPrices = listOf(
        YearlyGoldPrice("2021", "Rp 930.000", "Awal Investasi", true),
        YearlyGoldPrice("2022", "Rp 980.000", "+5.37%", true),
        YearlyGoldPrice("2023", "Rp 1.120.000", "+14.28%", true),
        YearlyGoldPrice("2024", "Rp 1.280.000", "+14.28%", true),
        YearlyGoldPrice("2025 (Sekarang)", "Rp 1.350.000", "+5.46%", true)
    )

    val currentData = timeframeMap[selectedTimeframe] ?: timeframeMap["1T"]!!

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
            // Header Title & Growth Rate
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Tren Harga Emas (${currentData.label})",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        maxLines = 1
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = null, tint = GreenSuccess, modifier = Modifier.size(12.dp))
                        Text(
                            text = currentData.growthText,
                            fontSize = 11.sp,
                            color = GreenSuccess,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Dedicated Full Width Timeframe Chips Row (1M, 3M, 6M, 1T, 5T)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                timeframes.forEach { tf ->
                    val isSelected = selectedTimeframe == tf
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 2.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isSelected) GoldPrimary else CreamBackground
                            )
                            .border(
                                1.dp,
                                if (isSelected) GoldPrimary else GoldBorder.copy(alpha = 0.5f),
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { selectedTimeframe = tf }
                            .padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tf,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) NavyPrimary else TextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Price Labels above Chart
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Awal: ${currentData.startPrice}", fontSize = 11.sp, color = TextMuted)
                Text("Kini: ${currentData.endPrice}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GoldDark)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Dynamic Chart Drawing Canvas
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                val width = size.width
                val height = size.height

                val rawHeights = currentData.yNormalizedHeights
                val stepX = width / (rawHeights.size - 1)

                val points = rawHeights.mapIndexed { i, normY ->
                    Offset(i * stepX, height * normY)
                }

                val strokePath = Path().apply {
                    moveTo(points[0].x, points[0].y)
                    for (i in 1 until points.size) {
                        val p1 = points[i - 1]
                        val p2 = points[i]
                        val cx = (p1.x + p2.x) / 2
                        cubicTo(cx, p1.y, cx, p2.y, p2.x, p2.y)
                    }
                }

                val fillPath = Path().apply {
                    addPath(strokePath)
                    lineTo(width, height)
                    lineTo(0f, height)
                    close()
                }

                // Gradient Under Graph
                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            GoldPrimary.copy(alpha = 0.35f),
                            GoldPrimary.copy(alpha = 0.02f)
                        )
                    )
                )

                // Smooth Trend Line
                drawPath(
                    path = strokePath,
                    color = GoldPrimary,
                    style = Stroke(width = 3.dp.toPx())
                )

                // Draw Dots at Peak Data Points
                points.forEach { pt ->
                    drawCircle(color = GoldPrimary, radius = 3.dp.toPx(), center = pt)
                }

                // Highlight Last Peak Point
                val lastPoint = points.last()
                drawCircle(color = GoldDark, radius = 6.dp.toPx(), center = lastPoint)
                drawCircle(color = Color.White, radius = 3.dp.toPx(), center = lastPoint)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Dynamic Timeline Labels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                currentData.timelineLabels.forEach { label ->
                    Text(label, fontSize = 10.sp, color = TextMuted, fontWeight = FontWeight.Medium)
                }
            }

            // Year-over-Year Price Table (Show when 5T selected)
            if (selectedTimeframe == "5T") {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = GoldBorder.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(12.dp))

                Text("Perkembangan Harga Tahun ke Tahun (5 Tahun):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NavyPrimary)
                Spacer(modifier = Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    yearlyPrices.forEach { yp ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(CreamBackground)
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(yp.year, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DarkText)
                            Text(yp.priceRupiah, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldDark)
                            Text(yp.changeText, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GreenSuccess)
                        }
                    }
                }
            }
        }
    }
}
