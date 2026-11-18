package com.example.goldencrown.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.data.model.Customer
import com.example.goldencrown.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Format tanggal standar Indonesia
fun formatTanggalIndonesia(timestamp: Long): String {
    val sdf = SimpleDateFormat("d MMMM yyyy, HH:mm 'WIB'", Locale("id", "ID"))
    return sdf.format(Date(timestamp))
}

/**
 * Kartu Pelanggan Emas
 * Sesuai panduan: Sudut 12dp, bayangan tipis, border emas 0.5dp, spasi kelipatan 8dp
 */
@Composable
fun CustomerCard(
    customer: Customer,
    onEditClick: (Customer) -> Unit,
    onDeleteClick: (Customer) -> Unit,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(0.5.dp, GcOutline, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GcSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Baris: Avatar Inisial, Nama, NIK, dan Menu
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    // Avatar Inisial Emas
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(GcSurfaceHigh)
                            .border(1.dp, GcGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = customer.nama.take(2).uppercase(Locale.ROOT),
                            style = Typography.titleMedium,
                            color = GcGold
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = customer.nama,
                            style = Typography.titleMedium,
                            color = GcCream,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Badge,
                                contentDescription = null,
                                tint = GcGoldLight,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "NIK: ${customer.nik}",
                                style = Typography.bodySmall,
                                color = GcMuted
                            )
                        }
                    }
                }

                // Menu Dropdown Edit & Hapus
                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = "Opsi Pelanggan",
                            tint = GcMuted
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        modifier = Modifier
                            .background(GcSurfaceHigh)
                            .border(0.5.dp, GcOutline, RoundedCornerShape(8.dp))
                    ) {
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Edit,
                                        contentDescription = null,
                                        tint = GcGold,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text("Ubah Data", color = GcCream, style = Typography.bodyMedium)
                                }
                            },
                            onClick = {
                                menuExpanded = false
                                onEditClick(customer)
                            }
                        )

                        HorizontalDivider(color = GcOutline)

                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.DeleteOutline,
                                        contentDescription = null,
                                        tint = GcError,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text("Hapus Pelanggan", color = GcError, style = Typography.bodyMedium)
                                }
                            },
                            onClick = {
                                menuExpanded = false
                                onDeleteClick(customer)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = GcOutline.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(12.dp))

            // Kontak & Alamat
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = null,
                        tint = GcGold,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = customer.hp,
                        style = Typography.bodyMedium,
                        color = GcCream,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = GcMuted,
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = customer.alamat,
                        style = Typography.bodySmall,
                        color = GcMuted,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Footer Kartu: Tanggal Registrasi
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ID: ${customer.id}",
                    style = Typography.labelSmall,
                    color = GcMuted.copy(alpha = 0.7f)
                )

                Text(
                    text = formatTanggalIndonesia(customer.createdAt),
                    style = Typography.labelSmall,
                    color = GcGoldLight.copy(alpha = 0.9f)
                )
            }
        }
    }
}

/**
 * Kartu Ringkasan Total Pelanggan (Variasi Layout)
 */
@Composable
fun CustomerSummaryCard(
    totalCustomers: Int,
    searchActive: Boolean,
    filteredCount: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(0.5.dp, GcOutline, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GcSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "DATABASE PELANGGAN EMAS",
                    style = Typography.labelSmall,
                    color = GcGold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (searchActive) "$filteredCount Ditemukan" else "$totalCustomers Nasabah Terdaftar",
                    style = Typography.headlineMedium,
                    color = GcCream
                )
            }

            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(GcSurfaceHigh)
                    .border(0.5.dp, GcGold, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.People,
                    contentDescription = null,
                    tint = GcGold,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

/**
 * Search Bar Outline Tipis
 */
@Composable
fun SearchBarGold(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text("Cari nama, NIK, atau no. handphone...", color = GcMuted, style = Typography.bodyMedium)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Pencarian",
                tint = GcGold,
                modifier = Modifier.size(20.dp)
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClearClick) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Hapus teks",
                        tint = GcMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = GcSurface,
            unfocusedContainerColor = GcSurface,
            focusedBorderColor = GcGold,
            unfocusedBorderColor = GcOutline,
            focusedTextColor = GcCream,
            unfocusedTextColor = GcCream
        ),
        textStyle = Typography.bodyMedium
    )
}

/**
 * Dialog Konfirmasi Sebelum Hapus Pelanggan
 */
@Composable
fun DeleteConfirmationDialog(
    customerName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = GcSurface,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.border(0.5.dp, GcError.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
        icon = {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(GcError.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.WarningAmber,
                    contentDescription = null,
                    tint = GcError,
                    modifier = Modifier.size(26.dp)
                )
            }
        },
        title = {
            Text(
                text = "Hapus Data Pelanggan?",
                style = Typography.titleMedium,
                color = GcCream
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Anda akan menghapus data pelanggan:",
                    style = Typography.bodyMedium,
                    color = GcMuted
                )
                Text(
                    text = "• $customerName",
                    style = Typography.titleSmall,
                    color = GcGoldLight
                )
                Text(
                    text = "Tindakan ini tidak dapat dibatalkan. Riwayat transaksi terkait akan tetap tersimpan dalam audit log.",
                    style = Typography.bodySmall,
                    color = GcMuted
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GcError,
                    contentColor = GcCream
                )
            ) {
                Text("Hapus Pelanggan", fontWeight = FontWeight.Bold, style = Typography.labelLarge)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(0.5.dp, GcOutline),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = GcMuted)
            ) {
                Text("Batal", style = Typography.labelLarge)
            }
        }
    )
}

/**
 * Form Tambah / Edit Pelanggan dengan Validasi Lengkap
 */
@Composable
fun CustomerFormDialog(
    isEditing: Boolean,
    nama: String,
    onNamaChange: (String) -> Unit,
    namaError: String?,
    nik: String,
    onNikChange: (String) -> Unit,
    nikError: String?,
    hp: String,
    onHpChange: (String) -> Unit,
    hpError: String?,
    alamat: String,
    onAlamatChange: (String) -> Unit,
    alamatError: String?,
    isSaving: Boolean,
    onSubmit: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { if (!isSaving) onDismiss() },
        containerColor = GcSurface,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(0.5.dp, GcGold, RoundedCornerShape(12.dp)),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = if (isEditing) Icons.Outlined.Edit else Icons.Outlined.PersonAdd,
                    contentDescription = null,
                    tint = GcGold
                )
                Text(
                    text = if (isEditing) "Ubah Data Pelanggan" else "Tambah Pelanggan Baru",
                    style = Typography.titleMedium,
                    color = GcCream
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Field Nama
                Column {
                    OutlinedTextField(
                        value = nama,
                        onValueChange = onNamaChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Nama Lengkap Nasabah") },
                        leadingIcon = {
                            Icon(Icons.Outlined.Person, contentDescription = null, tint = GcGold)
                        },
                        isError = namaError != null,
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = GcCream,
                            unfocusedTextColor = GcCream,
                            focusedBorderColor = GcGold,
                            unfocusedBorderColor = GcOutline,
                            errorBorderColor = GcError,
                            focusedLabelColor = GcGold,
                            unfocusedLabelColor = GcMuted
                        )
                    )
                    namaError?.let {
                        Text(it, color = GcError, style = Typography.bodySmall, modifier = Modifier.padding(start = 4.dp, top = 2.dp))
                    }
                }

                // Field NIK (Validasi 16 Digit)
                Column {
                    OutlinedTextField(
                        value = nik,
                        onValueChange = { if (it.length <= 16 && it.all { char -> char.isDigit() }) onNikChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Nomor Induk Kependudukan (NIK)") },
                        leadingIcon = {
                            Icon(Icons.Outlined.Badge, contentDescription = null, tint = GcGold)
                        },
                        trailingIcon = {
                            Text(
                                "${nik.length}/16",
                                color = if (nik.length == 16) GcSuccess else GcMuted,
                                style = Typography.labelSmall,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        },
                        isError = nikError != null,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = GcCream,
                            unfocusedTextColor = GcCream,
                            focusedBorderColor = GcGold,
                            unfocusedBorderColor = GcOutline,
                            errorBorderColor = GcError,
                            focusedLabelColor = GcGold,
                            unfocusedLabelColor = GcMuted
                        )
                    )
                    nikError?.let {
                        Text(it, color = GcError, style = Typography.bodySmall, modifier = Modifier.padding(start = 4.dp, top = 2.dp))
                    }
                }

                // Field No. Handphone (Validasi min 10 Digit)
                Column {
                    OutlinedTextField(
                        value = hp,
                        onValueChange = { if (it.length <= 15) onHpChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Nomor Handphone (WhatsApp)") },
                        leadingIcon = {
                            Icon(Icons.Outlined.Phone, contentDescription = null, tint = GcGold)
                        },
                        trailingIcon = {
                            Text(
                                "${hp.length} digit",
                                color = if (hp.length >= 10) GcSuccess else GcMuted,
                                style = Typography.labelSmall,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        },
                        isError = hpError != null,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = GcCream,
                            unfocusedTextColor = GcCream,
                            focusedBorderColor = GcGold,
                            unfocusedBorderColor = GcOutline,
                            errorBorderColor = GcError,
                            focusedLabelColor = GcGold,
                            unfocusedLabelColor = GcMuted
                        )
                    )
                    hpError?.let {
                        Text(it, color = GcError, style = Typography.bodySmall, modifier = Modifier.padding(start = 4.dp, top = 2.dp))
                    }
                }

                // Field Alamat
                Column {
                    OutlinedTextField(
                        value = alamat,
                        onValueChange = onAlamatChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Alamat Lengkap Domisili") },
                        leadingIcon = {
                            Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = GcGold)
                        },
                        isError = alamatError != null,
                        maxLines = 3,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = GcCream,
                            unfocusedTextColor = GcCream,
                            focusedBorderColor = GcGold,
                            unfocusedBorderColor = GcOutline,
                            errorBorderColor = GcError,
                            focusedLabelColor = GcGold,
                            unfocusedLabelColor = GcMuted
                        )
                    )
                    alamatError?.let {
                        Text(it, color = GcError, style = Typography.bodySmall, modifier = Modifier.padding(start = 4.dp, top = 2.dp))
                    }
                }
            }
        },
        confirmButton = {
            // Sesuai panduan: Tombol utama berlatar emas #C9A24B dengan teks hitam #0F0F0F
            Button(
                onClick = onSubmit,
                enabled = !isSaving,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GcGold,
                    contentColor = GcBlack
                )
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = GcBlack,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Menyimpan...", fontWeight = FontWeight.Bold, color = GcBlack)
                } else {
                    Text(
                        if (isEditing) "Simpan Perubahan" else "Simpan Pelanggan",
                        fontWeight = FontWeight.Bold,
                        color = GcBlack
                    )
                }
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                enabled = !isSaving,
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(0.5.dp, GcOutline),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = GcMuted)
            ) {
                Text("Batal", style = Typography.labelLarge)
            }
        }
    )
}

/**
 * Empty State Mewah
 */
@Composable
fun CustomerEmptyState(
    isSearching: Boolean,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(GcSurface)
                .border(0.5.dp, GcOutline, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isSearching) Icons.Outlined.SearchOff else Icons.Outlined.PersonAddDisabled,
                contentDescription = null,
                tint = GcGold,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isSearching) "Pelanggan Tidak Ditemukan" else "Belum Ada Data Pelanggan",
            style = Typography.titleMedium,
            color = GcCream
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = if (isSearching) "Coba periksa kembali kata kunci pencarian nama, NIK, atau nomor handphone." else "Mulai daftarkan nasabah emas pertama Anda ke dalam sistem Cloud Firestore.",
            style = Typography.bodyMedium,
            color = GcMuted,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        if (!isSearching) {
            Spacer(modifier = Modifier.height(18.dp))
            Button(
                onClick = onAddClick,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GcGold, contentColor = GcBlack)
            ) {
                Icon(Icons.Outlined.PersonAdd, contentDescription = null, modifier = Modifier.size(18.dp), tint = GcBlack)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Tambah Pelanggan Baru", fontWeight = FontWeight.Bold, color = GcBlack)
            }
        }
    }
}

/**
 * Loading State Mewah
 */
@Composable
fun CustomerLoadingState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = GcGold,
            strokeWidth = 3.dp,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Sinkronisasi Data Firestore...",
            style = Typography.bodyMedium,
            color = GcGoldLight
        )
    }
}
