package com.example.goldencrown.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.model.GoldArticle
import com.example.goldencrown.ui.theme.*

@Composable
fun EducationScreen(
    onOpenAssistant: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("Semua") }
    val categories = listOf("Semua", "Pemula", "Tips", "Istilah", "FAQ")

    val articles = listOf(
        GoldArticle("1", "Mengapa Emas Disebut Pelindung Inflasi (Safe Haven)?", "Pemula", "3 mnt baca", "Emas memiliki nilai intrinsik yang stabil secara historis terhadap penurunan daya beli mata uang.", "Emas adalah komoditas dengan nilai universal..."),
        GoldArticle("2", "5 Strategi Menabung Emas Secara Konsisten", "Tips", "4 mnt baca", "Pelajari teknik Dollar Cost Averaging (DCA) untuk mengoptimalkan harga rata-rata pembelian emas.", "Dollar Cost Averaging adalah strategi menabung emas secara rutin..."),
        GoldArticle("3", "Mengenal Istilah 'Buyback' dan 'Spread' Harga Emas", "Istilah", "2 mnt baca", "Memahami selisih harga beli dan jual emas agar keuntungan investasi maksimal.", "Spread adalah selisih antara harga beli dan harga buyback..."),
        GoldArticle("4", "FAQ: Apakah Emas Digital Pegadaian Bisa Dicetak Fisik?", "FAQ", "2 mnt baca", "Ya, saldo emas digital di Golden Crown dapat dicetak menjadi emas batangan fisik Galeri24/Antam.", "Setiap saldo emas digital dijamin 100% dengan emas fisik di brankas...")
    )

    val filteredArticles = articles.filter {
        if (selectedCategory == "Semua") true else it.category == selectedCategory
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Golden Assistant Banner Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF382A0A), Color(0xFF1E1705))
                        )
                    )
                    .border(1.5.dp, GoldPrimary, RoundedCornerShape(20.dp))
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(18.dp))
                            Text("Golden Assistant AI 👑", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = GoldPrimary)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Tanyakan apa saja seputar investasi & cara transaksi emas 24/7.", fontSize = 11.sp, color = Color.White)
                    }

                    Button(
                        onClick = onOpenAssistant,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = Color(0xFF1E1705))
                    ) {
                        Text("Tanya AI", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Gold Academy Header
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, tint = GoldDark, modifier = Modifier.size(18.dp))
            Text("Gold Academy & Edukasi", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        }

        // Category Filter Chips
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            categories.forEach { cat ->
                val isSelected = selectedCategory == cat
                Button(
                    onClick = { selectedCategory = cat },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) GoldPrimary else SurfaceWhite,
                        contentColor = if (isSelected) Color(0xFF1E1705) else TextSecondary
                    ),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(cat, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Article List
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(filteredArticles) { art ->
                Surface(
                    color = SurfaceWhite,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, GoldBorder.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Surface(color = GoldLight, shape = RoundedCornerShape(6.dp)) {
                                Text(art.category, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GoldDark, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                            Text(art.readTime, fontSize = 10.sp, color = TextMuted)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(art.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(art.summary, fontSize = 11.sp, color = TextSecondary, maxLines = 2)
                    }
                }
            }
        }
    }
}
