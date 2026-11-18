package com.example.goldencrown.ui.sheets

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaultCertificateSheet(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var isDownloaded by remember { mutableStateOf(false) }

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
                            .background(PegadaianGreenLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = PegadaianGreen)
                    }
                    Text("Sertifikat Vault Pegadaian", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(GoldLight)
                    .border(1.dp, GoldBorder, RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = PegadaianGreen, modifier = Modifier.size(18.dp))
                        Text("Sertifikat Kepemilikan Emas Murni", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    }

                    HorizontalDivider(color = GoldBorder)

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("No. Registrasi Vault", fontSize = 12.sp, color = TextSecondary)
                        Text("PEG-GOLD-8829104", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldDark)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Lokasi Penyimpanan", fontSize = 12.sp, color = TextSecondary)
                        Text("Vault Utama Pegadaian Jakarta", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Perusahaan Asuransi", fontSize = 12.sp, color = TextSecondary)
                        Text("PT Asuransi Jasindo (100%)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PegadaianGreen)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = { isDownloaded = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PegadaianGreen, contentColor = Color.White)
                ) {
                    Icon(imageVector = Icons.Default.Download, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isDownloaded) "Sertifikat PDF Berhasil Diunduh! 📄" else "Unduh Dokumen Sertifikat PDF", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, "Sertifikat Vault Emas Golden Crown")
                            putExtra(
                                Intent.EXTRA_TEXT,
                                """
                                👑 *PEGADAIAN x GOLDEN CROWN VAULT CERTIFICATE*
                                
                                Sertifikat Resmi Kepemilikan Emas Murni
                                -------------------------------------
                                • No. Vault        : PEG-GOLD-8829104
                                • Lokasi Brankas   : Vault Utama Pegadaian Jakarta
                                • Status Asuransi  : Tercover 100% PT Asuransi Jasindo
                                • Legalitas OJK    : Terdaftar & Diawasi OJK
                                
                                Aplikasi Golden Crown: Your Gold, Your Future.
                                """.trimIndent()
                            )
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Bagikan Sertifikat Vault Via:"))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary)
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = GoldDark)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Bagikan Dokumen Sertifikat (WhatsApp / Email)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldDark)
                }
            }
        }
    }
}
