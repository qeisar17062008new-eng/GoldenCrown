package com.example.goldencrown.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun TransactionHistoryScreen(
    transactions: List<TransactionItem>
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("Semua") }

    val filters = listOf("Semua", "Beli", "Jual", "Transfer", "Gadai")

    val filteredTransactions = transactions.filter { tx ->
        val matchesSearch = tx.title.contains(searchQuery, ignoreCase = true) || tx.date.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (selectedFilter) {
            "Beli" -> tx.title.contains("Beli")
            "Jual" -> tx.title.contains("Jual")
            "Transfer" -> tx.title.contains("Transfer")
            "Gadai" -> tx.title.contains("Gadai")
            else -> true
        }
        matchesSearch && matchesFilter
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Cari transaksi...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = TextMuted) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, unfocusedBorderColor = GoldBorder)
        )

        // Filter chips row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filters.forEach { filter ->
                val isSelected = selectedFilter == filter
                Button(
                    onClick = { selectedFilter = filter },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) GoldPrimary else SurfaceWhite,
                        contentColor = if (isSelected) Color(0xFF1E1705) else TextSecondary
                    ),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(filter, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Transactions list
        if (filteredTransactions.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Tidak ada transaksi ditemukan", color = TextMuted, fontSize = 13.sp)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredTransactions) { tx ->
                    Surface(
                        color = SurfaceWhite,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, GoldBorder.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(if (tx.isPositive) GoldLight else PegadaianGreenLight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (tx.title.contains("Beli")) Icons.Default.AddCard
                                        else if (tx.title.contains("Promo") || tx.title.contains("Cashback")) Icons.Default.CardGiftcard
                                        else Icons.Default.SwapHoriz,
                                        contentDescription = null,
                                        tint = if (tx.isPositive) GoldDark else PegadaianGreen
                                    )
                                }

                                Column {
                                    Text(tx.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                    Text(tx.date, fontSize = 11.sp, color = TextSecondary)
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(tx.amountGrams, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (tx.isPositive) GreenSuccess else TextPrimary)
                                Text(tx.amountRupiah, fontSize = 11.sp, color = TextSecondary)
                            }
                        }
                    }
                }
            }
        }
    }
}
