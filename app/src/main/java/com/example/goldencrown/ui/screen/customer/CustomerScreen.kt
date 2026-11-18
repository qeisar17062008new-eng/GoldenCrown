package com.example.goldencrown.ui.screen.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldencrown.ui.components.*
import com.example.goldencrown.ui.theme.*

/**
 * Layar Kelola Pelanggan (CRUD) GoldenCrow
 * Arsitektur MVVM murni: Mengamati StateFlow dari CustomerViewModel,
 * tidak menaruh logika bisnis di dalam Composable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerScreen(
    viewModel: CustomerViewModel,
    onNavigateBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Tampilkan snackbar jika ada pesan sukses atau error
    LaunchedEffect(uiState.successMessage, uiState.errorMessage) {
        uiState.successMessage?.let { msg ->
            snackbarHostState.showSnackbar(
                message = "✓ $msg",
                duration = SnackbarDuration.Short
            )
            viewModel.clearMessages()
        }
        uiState.errorMessage?.let { err ->
            snackbarHostState.showSnackbar(
                message = "⚠ $err",
                duration = SnackbarDuration.Long
            )
            viewModel.clearMessages()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = GcBlack,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    containerColor = GcSurfaceHigh,
                    contentColor = GcCream,
                    shape = RoundedCornerShape(8.dp),
                    snackbarData = data
                )
            }
        },
        topBar = {
            Surface(
                color = GcSurface,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (onNavigateBack != null) {
                            IconButton(onClick = onNavigateBack) {
                                Icon(
                                    imageVector = Icons.Outlined.ArrowBack,
                                    contentDescription = "Kembali",
                                    tint = GcGold
                                )
                            }
                        }

                        // Logo Resmi GoldenCrow dari logo crow.avif
                        GoldenCrowLogo(size = 42.dp)

                        Column {
                            Text(
                                text = "Kelola Pelanggan",
                                style = Typography.titleLarge,
                                color = GcCream
                            )
                            Text(
                                text = "Toko Perhiasan & Pegadaian GoldenCrow",
                                style = Typography.bodySmall,
                                color = GcMuted
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            // Tombol utama: Latar emas #C9A24B dengan teks/ikon hitam #0F0F0F
            ExtendedFloatingActionButton(
                onClick = { viewModel.openAddDialog() },
                containerColor = GcGold,
                contentColor = GcBlack,
                shape = RoundedCornerShape(12.dp),
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.PersonAdd,
                        contentDescription = null,
                        tint = GcBlack
                    )
                },
                text = {
                    Text(
                        text = "Tambah Pelanggan",
                        fontWeight = FontWeight.Bold,
                        color = GcBlack,
                        style = Typography.labelLarge
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(2.dp))

            // 1. Kartu Ringkasan Total Pelanggan
            CustomerSummaryCard(
                totalCustomers = uiState.customers.size,
                searchActive = uiState.searchQuery.isNotEmpty(),
                filteredCount = uiState.filteredCustomers.size
            )

            // 2. Baris Pencarian Tipis
            SearchBarGold(
                query = uiState.searchQuery,
                onQueryChange = { viewModel.onSearchQueryChange(it) },
                onClearClick = { viewModel.clearSearch() }
            )

            // 3. Konten Utama: Loading, Empty, atau Daftar Pelanggan
            when {
                uiState.isLoading -> {
                    CustomerLoadingState(modifier = Modifier.weight(1f))
                }
                uiState.filteredCustomers.isEmpty() -> {
                    CustomerEmptyState(
                        isSearching = uiState.searchQuery.isNotEmpty(),
                        onAddClick = { viewModel.openAddDialog() },
                        modifier = Modifier.weight(1f)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(bottom = 88.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = uiState.filteredCustomers,
                            key = { it.id }
                        ) { customer ->
                            CustomerCard(
                                customer = customer,
                                onEditClick = { viewModel.openEditDialog(it) },
                                onDeleteClick = { viewModel.requestDelete(it) }
                            )
                        }
                    }
                }
            }
        }
    }

    // Modal Form Dialog: Tambah atau Ubah Pelanggan
    if (uiState.isFormOpen) {
        CustomerFormDialog(
            isEditing = uiState.editingCustomer != null,
            nama = uiState.formNama,
            onNamaChange = { viewModel.onFormNamaChange(it) },
            namaError = uiState.namaError,
            nik = uiState.formNik,
            onNikChange = { viewModel.onFormNikChange(it) },
            nikError = uiState.nikError,
            hp = uiState.formHp,
            onHpChange = { viewModel.onFormHpChange(it) },
            hpError = uiState.hpError,
            alamat = uiState.formAlamat,
            onAlamatChange = { viewModel.onFormAlamatChange(it) },
            alamatError = uiState.alamatError,
            isSaving = uiState.isSaving,
            onSubmit = { viewModel.submitForm() },
            onDismiss = { viewModel.closeFormDialog() }
        )
    }

    // Modal Konfirmasi Hapus Pelanggan
    uiState.customerToDelete?.let { targetCustomer ->
        DeleteConfirmationDialog(
            customerName = targetCustomer.nama,
            onConfirm = { viewModel.confirmDelete() },
            onDismiss = { viewModel.cancelDelete() }
        )
    }
}
