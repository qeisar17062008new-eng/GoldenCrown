package com.example.goldencrown.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.LocalOffer
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
import com.example.goldencrown.model.PromoBanner
import com.example.goldencrown.ui.theme.*

@Composable
fun PromoBannerCarousel(
    onPromoClick: (PromoBanner) -> Unit = {}
) {
    val promos = listOf(
        PromoBanner(
            id = "1",
            title = "Cashback Beli Emas Rp 250rb",
            subtitle = "Gunakan kode 'GOLDENCROWN' untuk transaksi pertama",
            code = "GOLDENCROWN",
            tag = "Promo Spesial"
        ),
        PromoBanner(
            id = "2",
            title = "Gratis Biaya Cetak Galeri24",
            subtitle = "Cetak emas batangan 1g - 100g bebas ongkir ke seluruh Indonesia",
            code = "FREEONGKIR",
            tag = "Cetak Emas"
        ),
        PromoBanner(
            id = "3",
            title = "Gadai Syariah Bunga 0%",
            subtitle = "Gadai emas tanpa bunga hingga 30 hari pertama di Pegadaian",
            code = "GADAISYARIAH",
            tag = "Gadai Emas"
        )
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocalOffer,
                    contentDescription = null,
                    tint = GoldDark,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Penawaran & Promo Golden Crown",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            Text(
                text = "Lihat Semua",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PegadaianGreen,
                modifier = Modifier.clickable { onPromoClick(promos.first()) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            items(promos) { promo ->
                PromoCardItem(promo = promo, onClick = { onPromoClick(promo) })
            }
        }
    }
}

@Composable
private fun PromoCardItem(
    promo: PromoBanner,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .width(280.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .background(
                brush = Brush.horizontalGradient(
                    colors = if (promo.id == "1") listOf(Color(0xFF006837), Color(0xFF004D28))
                    else if (promo.id == "2") listOf(Color(0xFF382A0A), Color(0xFF2C2208))
                    else listOf(Color(0xFF9A7B1C), Color(0xFF6B5310))
                )
            )
            .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = GoldPrimary,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = promo.tag,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1705),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Icon(
                    imageVector = Icons.Default.CardGiftcard,
                    contentDescription = null,
                    tint = GoldLight,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = promo.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = promo.subtitle,
                fontSize = 11.sp,
                color = GoldLight.copy(alpha = 0.85f),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Kode: ${promo.code}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimary
                )
            }
        }
    }
}
