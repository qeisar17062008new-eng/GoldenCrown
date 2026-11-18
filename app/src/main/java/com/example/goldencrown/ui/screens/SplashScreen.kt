package com.example.goldencrown.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.ui.components.GoldenCrowLogo
import com.example.goldencrown.ui.theme.*

/**
 * Layar Splash Screen Mewah GoldenCrow
 * Dilengkapi animasi respirasi aura emas (golden breathing aura) pada logo gagak emas,
 * tipografi elegan yang kontras tinggi di atas latar hitam #0F0F0F,
 * serta scrolling adaptif agar seluruh teks dan tombol selalu terlihat di semua ukuran layar.
 */
@Composable
fun SplashScreen(
    onGetStarted: () -> Unit,
    onRegisterClick: () -> Unit = {}
) {
    // Animasi Pulse & Aura Logo Mewah
    val infiniteTransition = rememberInfiniteTransition(label = "luxury_aura_pulse")

    val logoScale by infiniteTransition.animateFloat(
        initialValue = 0.97f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_scale"
    )

    val auraAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "aura_alpha"
    )

    val glowRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "glow_rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GcBlack)
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Central Logo & Brand Branding
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                // Wadah Logo dengan Aura Emas Bernafas
                Box(
                    modifier = Modifier
                        .size(170.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Cincin Cahaya Halo di Belakang Logo
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .scale(logoScale)
                            .alpha(auraAlpha)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        GcGoldLight.copy(alpha = 0.45f),
                                        GcGold.copy(alpha = 0.25f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    // Cincin Aksen Emas Berputar Tipis
                    Box(
                        modifier = Modifier
                            .size(136.dp)
                            .scale(logoScale * 0.98f)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                brush = Brush.sweepGradient(
                                    colors = listOf(
                                        GcGold,
                                        Color.Transparent,
                                        GcGoldLight,
                                        Color.Transparent,
                                        GcGold
                                    )
                                ),
                                shape = CircleShape
                            )
                    )

                    // Logo Resmi GoldenCrow (Crow)
                    GoldenCrowLogo(
                        size = 120.dp,
                        modifier = Modifier.scale(logoScale)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Judul Brand Utama (Serif Elegan)
                Text(
                    text = "GOLDENCROW",
                    style = Typography.headlineLarge,
                    color = GcGold,
                    letterSpacing = 3.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Tagline Mewah
                Text(
                    text = "Kemewahan Perhiasan & Pegadaian Emas",
                    style = Typography.titleSmall,
                    color = GcCream,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Your Gold, Your Timeless Future",
                    style = Typography.bodySmall,
                    color = GcGoldLight.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Badge Keamanan & Legalitas OJK (Sangat Jelas Terbaca)
                Surface(
                    color = GcSurface,
                    shape = RoundedCornerShape(20.dp),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, GcGold.copy(alpha = 0.6f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = GcGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Terdaftar & Diawasi Resmi oleh OJK",
                            fontSize = 12.sp,
                            color = GcCream,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Bagian Bawah: Tombol Aksi & Informasi Versi
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                // Tombol Utama: Latar Emas #C9A24B dengan Teks Hitam #0F0F0F
                Button(
                    onClick = onGetStarted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GcGold,
                        contentColor = GcBlack
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = "Masuk ke Akun",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = GcBlack
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = GcBlack,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Tombol Pendaftaran: Border Emas Tipis dengan Teks Krem/Emas
                OutlinedButton(
                    onClick = onRegisterClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GcGold),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = GcSurface,
                        contentColor = GcCream
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.PersonAdd,
                        contentDescription = null,
                        tint = GcGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Daftar Nasabah Baru (Gratis)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = GcCream
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Teks Versi & Keaslian (Terbaca Jelas)
                Text(
                    text = "v1.0.0 • PT GoldenCrow Indonesia • 100% Emas Murni Fisik",
                    fontSize = 11.sp,
                    color = GcMuted,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
