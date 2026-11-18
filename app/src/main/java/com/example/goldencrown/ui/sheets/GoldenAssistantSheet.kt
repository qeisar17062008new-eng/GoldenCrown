package com.example.goldencrown.ui.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.model.ChatMessage
import com.example.goldencrown.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoldenAssistantSheet(
    userName: String = "Qeisar",
    goldBuyPrice: Double = 1350000.0,
    onDismiss: () -> Unit
) {
    var messageInput by remember { mutableStateOf("") }
    var chatList by remember {
        mutableStateOf(
            listOf(
                ChatMessage(
                    "1",
                    "Halo $userName! Saya Golden Assistant AI 👑.\n" +
                    "Saya siap menjawab pertanyaan seputar:\n" +
                    "• 📜 Hukum Islam & Fatwa MUI Emas\n" +
                    "• 🇮🇩 Regulasi OJK & Pajak PMK 48/2023\n" +
                    "• 🌐 Standar Kemurnian LBMA (24K 99.99%)\n" +
                    "• 💰 Zakat Emas, Gadai Rahn & Cara Cetak\n\n" +
                    "Ada yang ingin Anda tanyakan hari ini?",
                    false,
                    "10:00"
                )
            )
        )
    }

    fun generateAiResponse(userText: String): String {
        val q = userText.lowercase()

        return when {
            // 🕌 HUKUM ISLAM / SYARIAH / FATWA MUI / ZAKAT / RAHN
            q.contains("islam") || q.contains("syariah") || q.contains("fatwa") || q.contains("mui") || q.contains("halal") || q.contains("haram") || q.contains("riba") -> {
                "📜 **Hukum Islam & Syariah Transaksi Emas:**\n\n" +
                "1. **Fatwa DSN-MUI No. 77/DSN-MUI/V/2010**:\n" +
                "   Jual beli emas secara angsuran/tidak tunai hukumnya **HALAL / BOLEH**, selama emas dijadikan barang investasi dan bukan media pembayaran tunai yang mendzalimi, serta emas fisik dijamin ketersediaannya.\n\n" +
                "2. **Gadai Emas Syariah (Rahn)**:\n" +
                "   Bebas dari Riba & Bunga! Biaya yang dikenakan murni sewa tempat penyimpanan (*Mu'nah*) yang dihitung berdasarkan berat emas fisik, bukan dari nilai rupiah pinjaman.\n\n" +
                "3. **Prinsip Yad bi Yadin (Tukar Menukar)**:\n" +
                "   Setiap emas digital di Golden Crown memiliki underlying asset fisik 100% murni 24 Karat yang dapat dicetak kapan saja."
            }

            q.contains("zakat") || q.contains("nisab") || q.contains("haul") -> {
                "💰 **Aturan Zakat Emas menurut Syariat Islam:**\n\n" +
                "• **Nisab Zakat**: Wajib dikeluarkannya zakat jika total kepemilikan emas mencapai minimal **85 Gram**.\n" +
                "• **Haul**: Telah disimpan/diendapkan selama **1 Tahun Hijriyah**.\n" +
                "• **Besaran Zakat**: Sebesar **2.5%** dari total berat/nilai emas.\n\n" +
                "💡 *Contoh*: Jika Anda memiliki 100 gram emas selama 1 tahun, zakatnya adalah 2.5 gram (atau senilai Rp ${String.format("%,.0f", goldBuyPrice * 2.5)})."
            }

            // 🇮🇩 HUKUM NEGARA / REGULASI OJK / PAJAK / PERATURAN PEGADAIAN
            q.contains("ojk") || q.contains("negara") || q.contains("regulasi") || q.contains("hukum") || q.contains("uu") || q.contains("resmi") || q.contains("pajak") || q.contains("pph") || q.contains("legal") -> {
                "🇮🇩 **Hukum & Regulasi Resmi di Indonesia:**\n\n" +
                "1. **Izin & Pengawasan OJK**:\n" +
                "   Golden Crown bekerjasama dengan Pegadaian Digital di bawah **POJK No. 31/POJK.05/2016** tentang Usaha Pergadaian resmi di Indonesia.\n\n" +
                "2. **Ketentuan Pajak PMK No. 48/2023**:\n" +
                "   • Pembelian emas batangan dipotong **PPh 22 sebesar 0.25%** (dengan NPWP).\n" +
                "   • Penjualan kembali (Buyback) di atas Rp 10 Juta dikenakan PPh 22 sebesar **1.5%**.\n\n" +
                "3. **Perlindungan Konsumen & Sertifikasi**:\n" +
                "   Setiap emas cetak terjamin memiliki sertifikat resmi **SNI / LBMA / Antam / Galeri24** dengan tingkat kemurnian teruji lab."
            }

            // 🌐 STANDAR INTERNASIONAL / LBMA / KARAT / GLOBAL
            q.contains("internasional") || q.contains("lbma") || q.contains("dunia") || q.contains("global") || q.contains("24k") || q.contains("karat") || q.contains("london") || q.contains("dolar") || q.contains("inflasi") -> {
                "🌐 **Standar Emas Internasional & Pasar Global:**\n\n" +
                "1. **Akreditasi LBMA (London Bullion Market Association)**:\n" +
                "   Standar emas murni 24 Karat internasional memiliki tingkat kemurnian **99.99% (999.9 Fine Gold)** yang diakui secara universal di seluruh bank sentral dan pasar emas dunia.\n\n" +
                "2. **Penetapan Harga Global (London Gold Fix)**:\n" +
                "   Harga emas dunia dipengaruhi oleh indeks mata uang Dolar AS (DXY), suku bunga The Fed, geopolitik global, serta tingkat inflasi dunia.\n\n" +
                "3. **Aset Pelindung (Safe Haven)**:\n" +
                "   Emas secara historis terbukti menjaga daya beli aset dari inflasi selama ribuan tahun tanpa risiko kebangkrutan emiten."
            }

            // 🏷️ HARGA & PASAR
            q.contains("harga") || q.contains("beli") || q.contains("jual") || q.contains("grafik") || q.contains("tren") || q.contains("naik") || q.contains("turun") -> {
                "📈 **Update Harga & Analisis Pasar Emas Hari Ini:**\n\n" +
                "• **Harga Beli Emas**: Rp ${String.format("%,.0f", goldBuyPrice)} / gram\n" +
                "• **Harga Buyback (Jual Kembali)**: Rp ${String.format("%,.0f", goldBuyPrice * 0.94)} / gram\n" +
                "• **Pergerakan 24 Jam**: Up +0.89% (Tren Bullish/Positif)\n\n" +
                "💡 *Saran Investasi*: Menabung emas secara rutin (Dollar Cost Averaging) sangat direkomendasikan untuk jangka menengah & panjang."
            }

            // 🔨 CETAK EMAS FISIK
            q.contains("cetak") || q.contains("fisik") || q.contains("antam") || q.contains("galeri") || q.contains("dikirim") -> {
                "📦 **Layanan Cetak Emas Fisik:**\n\n" +
                "Anda dapat mengonversi saldo emas digital menjadi emas batangan fisik asli:\n" +
                "1. Pilih menu **'Cetak Emas'** di beranda.\n" +
                "2. Pilih brand ternama: **Antam, Galeri24, atau Lotus Archi**.\n" +
                "3. Denominasi mulai dari **0.5g, 1g, 5g, 10g hingga 100g**.\n" +
                "4. Kirim langsung ke alamat rumah Anda dengan asuransi pengiriman 100%!"
            }

            // 🤝 GADAI EMAS / RAHN
            q.contains("gadai") || q.contains("rahn") || q.contains("pinjam") || q.contains("modal") || q.contains("dana") -> {
                "🤝 **Gadai Emas Syariah (Rahn):**\n\n" +
                "Dapatkan dana tunai cepat tanpa perlu menjual emas kesayangan Anda:\n" +
                "• **Pencairan**: Hingga **85%** dari nilai taksiran emas.\n" +
                "• **Sistem Syariah**: Tanpa Bunga (0%), hanya biaya sewa tempat (*Mu'nah*).\n" +
                "• **Tenor Flexibel**: Dapat diperpanjang atau dilunasi sewaktu-waktu."
            }

            // 📲 AKUN & REGISTRASI / KYC / VERIFIKASI
            q.contains("daftar") || q.contains("akun") || q.contains("kyc") || q.contains("verifikasi") || q.contains("syarat") -> {
                "🛡️ **Persyaratan Registrasi & Keamanan Akun:**\n\n" +
                "• **Syarat KYC**: Menyiapkan KTP resmi & verifikasi foto selfie.\n" +
                "• **Keamanan**: Dilengkapi enkripsi PIN 6 digit dan keamanan biometrik Sidik Jari.\n" +
                "• Tabungan emas Anda langsung tercatat resmi atas nama Anda di sistem."
            }

            // 👋 SALAM / UMUM
            q.contains("halo") || q.contains("hai") || q.contains("pagi") || q.contains("siang") || q.contains("malam") || q.contains("siapa") || q.contains("bantu") -> {
                "Halo $userName! Saya Golden Assistant AI 👑.\n" +
                "Ada yang ingin Anda konsultasikan seputar Hukum Islam emas, Regulasi Negara OJK, Standar LBMA Internasional, atau cara transaksi emas?"
            }

            q.contains("terima kasih") || q.contains("makasih") || q.contains("thanks") -> {
                "Sama-sama $userName! 🌟 Senang dapat membantu Anda. Jangan ragu bertanya lagi kapan saja!"
            }

            // 👑 DEFAULT JAWABAN LENGKAP
            else -> {
                "👑 **Informasi Golden Crown Assistant:**\n\n" +
                "Emas adalah aset bernilai tinggi yang diakui secara **Syariah Islam** (Fatwa MUI 77/2010), dilindungi **Regulasi Negara OJK**, dan diakui secara **Internasional LBMA (24K 99.99%)**.\n\n" +
                "Anda dapat menanyakan hal spesifik seperti:\n" +
                "1. *'Bagaimana hukum syariah emas?'*\n" +
                "2. *'Bagaimana aturan pajak OJK & PMK?'*\n" +
                "3. *'Apa itu standar emas LBMA 24K?'*\n" +
                "4. *'Berapa zakat emas dan nisabnya?'*\n" +
                "5. *'Cara gadai syariah Rahn & cetak fisik?'*"
            }
        }
    }

    fun handleSend() {
        if (messageInput.isBlank()) return
        val userText = messageInput
        val userMsg = ChatMessage(System.currentTimeMillis().toString(), userText, true, "Sekarang")
        chatList = chatList + userMsg
        messageInput = ""

        val replyText = generateAiResponse(userText)
        val aiMsg = ChatMessage((System.currentTimeMillis() + 1).toString(), replyText, false, "Sekarang")
        chatList = chatList + aiMsg
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = SurfaceWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .padding(20.dp)
        ) {
            // Header Bar
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
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = GoldDark)
                    }
                    Column {
                        Text("Golden Assistant AI 👑", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text("Asisten Edukasi & Informasi Emas 24/7", fontSize = 11.sp, color = PegadaianGreen, fontWeight = FontWeight.SemiBold)
                    }
                }

                IconButton(onClick = onDismiss) { Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup") }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Chat Messages List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(chatList) { msg ->
                    ChatBubbleItem(msg = msg)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Quick Question Prompt Chips (Scrollable Horizontal)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(
                    "📜 Hukum Islam Emas?",
                    "🇮🇩 Regulasi OJK & Pajak?",
                    "🌐 Standar LBMA 24K?",
                    "💰 Zakat Emas Berapa?",
                    "🤝 Cara Gadai Rahn?",
                    "📦 Cara Cetak Emas?"
                ).forEach { prompt ->
                    OutlinedButton(
                        onClick = {
                            messageInput = prompt
                            handleSend()
                        },
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder)
                    ) {
                        Text(prompt, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GoldLight)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Input Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageInput,
                    onValueChange = { messageInput = it },
                    placeholder = { Text("Tanyakan hukum Islam, OJK, LBMA...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary, unfocusedBorderColor = GoldBorder)
                )

                IconButton(
                    onClick = { handleSend() },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(GoldPrimary)
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = "Kirim", tint = Color(0xFF1E1705))
                }
            }
        }
    }
}

@Composable
private fun ChatBubbleItem(msg: ChatMessage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (msg.isFromUser) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 290.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (msg.isFromUser) 16.dp else 4.dp,
                        bottomEnd = if (msg.isFromUser) 4.dp else 16.dp
                    )
                )
                .background(if (msg.isFromUser) GoldPrimary else GcSurfaceHigh)
                .border(
                    1.dp,
                    if (msg.isFromUser) GoldPrimary else GoldBorder,
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (msg.isFromUser) 16.dp else 4.dp,
                        bottomEnd = if (msg.isFromUser) 4.dp else 16.dp
                    )
                )
                .padding(12.dp)
        ) {
            Text(
                text = msg.text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = if (msg.isFromUser) Color(0xFF1E1705) else TextPrimary
            )
        }
    }
}
