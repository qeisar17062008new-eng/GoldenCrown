package com.example.goldencrown.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.goldencrown.R
import com.example.goldencrown.ui.theme.GcBlack
import com.example.goldencrown.ui.theme.GcGold

/**
 * Komponen Logo Resmi GoldenCrow sesuai identitas visual perhiasan emas & pegadaian
 * Menampilkan logo gagak emas elegan dari aset logo crow.avif / logo_crow.png
 */
@Composable
fun GoldenCrowLogo(
    size: Dp = 80.dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .shadow(
                elevation = 6.dp,
                shape = CircleShape,
                spotColor = GcGold.copy(alpha = 0.4f)
            )
            .clip(CircleShape)
            .background(GcBlack)
            .border(
                width = 1.dp,
                color = GcGold,
                shape = CircleShape
            )
            .padding(size * 0.08f),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_crow),
            contentDescription = "GoldenCrown Official Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}

// Alias kompatibilitas
@Composable
fun GoldenCrownLogo(
    size: Dp = 80.dp,
    modifier: Modifier = Modifier
) {
    GoldenCrowLogo(size = size, modifier = modifier)
}
