package com.example.goldencrown.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.model.TransactionItem
import com.example.goldencrown.ui.theme.*

@Composable
fun TransactionHistoryCard(
    transactions: List<TransactionItem>,
    onSeeAllClick: () -> Unit = {},
    onTransactionClick: (TransactionItem) -> Unit = {}
) {
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
                Text(
                    text = "Riwayat Transaksi Terakhir",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Text(
                    text = "Lihat Semua",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PegadaianGreen,
                    modifier = Modifier.clickable { onSeeAllClick() }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                transactions.forEachIndexed { index, tx ->
                    TransactionRowItem(item = tx, onClick = { onTransactionClick(tx) })
                    if (index < transactions.size - 1) {
                        HorizontalDivider(
                            color = GoldBorder.copy(alpha = 0.3f),
                            thickness = 0.8.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionRowItem(
    item: TransactionItem,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (item.isPositive) GoldLight
                        else PegadaianGreenLight
                    )
                    .border(
                        1.dp,
                        if (item.isPositive) GoldBorder else PegadaianGreen.copy(alpha = 0.3f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (item.title.contains("Beli")) Icons.Default.AddCard
                    else if (item.title.contains("Promo") || item.title.contains("Cashback")) Icons.Default.CardGiftcard
                    else Icons.Default.SwapHoriz,
                    contentDescription = null,
                    tint = if (item.isPositive) GoldDark else PegadaianGreen,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column {
                Text(
                    text = item.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = item.date,
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = item.amountGrams,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (item.isPositive) GreenSuccess else TextPrimary
            )
            Text(
                text = item.amountRupiah,
                fontSize = 11.sp,
                color = TextSecondary
            )
        }
    }
}
