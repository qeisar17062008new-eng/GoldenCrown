package com.example.goldencrown.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.goldencrown.ui.theme.*

@Composable
fun LoadingScreenDialog(
    message: String = "Memverifikasi Akun Golden Crown...",
    onDismiss: () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading_rotate")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = SurfaceWhite,
            shadowElevation = 12.dp,
            modifier = Modifier
                .border(1.5.dp, GoldBorder, RoundedCornerShape(20.dp))
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(68.dp),
                        color = GoldPrimary,
                        trackColor = GoldLight,
                        strokeWidth = 4.dp
                    )

                    Box(
                        modifier = Modifier
                            .rotate(rotation),
                        contentAlignment = Alignment.Center
                    ) {
                        GoldenCrowLogo(size = 36.dp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = message,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = GcCream
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Mohon tunggu sebentar...",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }
        }
    }
}
