package com.example.goldencrown.ui.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.model.PromoBanner
import com.example.goldencrown.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoListSheet(
    onDismiss: () -> Unit,
    onSelectPromo: (PromoBanner) -> Unit
) {
    val promos = listOf(
        PromoBanner(
            id = "1",
            title = "Cashback Beli Emas Rp 250rb",
            subtitle = "Gunakan kode 'GOLDENCROWN' untuk transaksi pertama Anda.",
            code = "GOLDENCROWN",
            tag = "Promo Spesial"
        ),
        PromoBanner(
            id = "2",
            title = "Gratis Biaya Cetak Galeri24",
            subtitle = "Cetak emas batangan 1g - 100g bebas ongkir ke seluruh Indonesia.",
            code = "FREEONGKIR",
            tag = "Cetak Emas"
        ),
        PromoBanner(
            id = "3",
            title = "Gadai Syariah Bunga 0%",
            subtitle = "Gadai emas tanpa bunga hingga 30 hari pertama di Pegadaian.",
            code = "GADAISYARIAH",
            tag = "Gadai Emas"
        )
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
                            .background(GoldLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.CardGiftcard, contentDescription = null, tint = GoldDark)
                    }
                    Text("Semua Promo Golden Crown", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            promos.forEach { promo ->
                Surface(
                    onClick = { onSelectPromo(promo) },
                    shape = RoundedCornerShape(16.dp),
                    color = BackgroundWhite,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(promo.tag, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GoldDark)
                            Text("Kode: ${promo.code}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PegadaianGreen)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(promo.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text(promo.subtitle, fontSize = 11.sp, color = TextSecondary)
                    }
                }
            }
        }
    }
}
