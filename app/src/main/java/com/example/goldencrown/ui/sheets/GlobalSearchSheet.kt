package com.example.goldencrown.ui.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.ui.theme.*

data class GlobalSearchResult(
    val title: String,
    val category: String,
    val detail: String,
    val targetSheet: com.example.goldencrown.model.ActiveSheet? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalSearchSheet(
    onDismiss: () -> Unit,
    onSelectSheet: (com.example.goldencrown.model.ActiveSheet) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val allData = listOf(
        GlobalSearchResult("Beli Tabungan Emas", "Layanan", "Beli emas digital mulai Rp 10.000", com.example.goldencrown.model.ActiveSheet.BUY),
        GlobalSearchResult("Jual Emas (Buyback)", "Layanan", "Cairkan saldo emas ke rekening bank", com.example.goldencrown.model.ActiveSheet.SELL),
        GlobalSearchResult("Transfer Emas ke Sesama", "Layanan", "Kirim emas instan bebas biaya", com.example.goldencrown.model.ActiveSheet.TRANSFER),
        GlobalSearchResult("Gadai Emas Syariah", "Layanan", "Pinjaman dana tunai agunan emas 24K-16K", com.example.goldencrown.model.ActiveSheet.GADAI),
        GlobalSearchResult("Cetak Emas Fisik Batangan", "Layanan", "Cetak emas Galeri24/Antam 1g-100g", com.example.goldencrown.model.ActiveSheet.CETAK),
        GlobalSearchResult("Auto-Save Tabungan Otomatis", "Layanan", "Fitur menabung rutin mingguan/bulanan", com.example.goldencrown.model.ActiveSheet.AUTO_SAVE),
        GlobalSearchResult("Zakat Emas 2.5% BAZNAS", "Layanan", "Kalkulator & pembayaran zakat emas", com.example.goldencrown.model.ActiveSheet.ZAKAT_GOLD),
        GlobalSearchResult("Alarm & Alert Harga Beli Emas", "Layanan", "Notifikasi otomatis saat harga emas turun", com.example.goldencrown.model.ActiveSheet.PRICE_ALERT),
        GlobalSearchResult("Emas Batangan Antam 1 Gram 999.9", "Produk Emas", "Rp 1.350.000 • Sertifikat CertiCard", com.example.goldencrown.model.ActiveSheet.CETAK),
        GlobalSearchResult("Emas Galeri24 Pegadaian 5 Gram", "Produk Emas", "Rp 6.700.000 • Vault Guarantee", com.example.goldencrown.model.ActiveSheet.CETAK),
        GlobalSearchResult("Mengapa Emas Pelindung Inflasi?", "Edukasi", "Artikel 3 menit baca", com.example.goldencrown.model.ActiveSheet.HELP)
    )

    val filteredResults = remember(searchQuery) {
        if (searchQuery.isBlank()) allData
        else allData.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
            it.category.contains(searchQuery, ignoreCase = true) ||
            it.detail.contains(searchQuery, ignoreCase = true)
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = SurfaceWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = GoldDark)
                    Text("Pencarian Global Golden Crown", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }

                IconButton(onClick = onDismiss) { Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup") }
            }

            // Search Bar Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Cari layanan, emas, zakat, promo...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = GoldPrimary) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, unfocusedBorderColor = GoldBorder)
            )

            Text("Hasil Pencarian (${filteredResults.size}):", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextSecondary)

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 350.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredResults) { res ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                res.targetSheet?.let { sheet -> onSelectSheet(sheet) }
                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = BackgroundWhite)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Surface(color = GoldLight, shape = RoundedCornerShape(4.dp)) {
                                        Text(res.category, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GoldDark, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                    }
                                    Text(res.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(res.detail, fontSize = 11.sp, color = TextSecondary)
                            }
                        }
                    }
                }
            }
        }
    }
}
