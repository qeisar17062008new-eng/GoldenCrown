package com.example.goldencrown.ui.screen.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goldencrown.data.model.Customer
import com.example.goldencrown.data.repository.CustomerRepository
import com.example.goldencrown.domain.model.CustomerValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CustomerUiState(
    val isLoading: Boolean = true,
    val customers: List<Customer> = emptyList(),
    val filteredCustomers: List<Customer> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String? = null,
    val successMessage: String? = null,
    // Dialog Form State
    val isFormOpen: Boolean = false,
    val editingCustomer: Customer? = null,
    val formNama: String = "",
    val formNik: String = "",
    val formHp: String = "",
    val formAlamat: String = "",
    val namaError: String? = null,
    val nikError: String? = null,
    val hpError: String? = null,
    val alamatError: String? = null,
    val isSaving: Boolean = false,
    // Dialog Konfirmasi Hapus
    val customerToDelete: Customer? = null,
    val isDeleting: Boolean = false
)

class CustomerViewModel(
    private val repository: CustomerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CustomerUiState())
    val uiState: StateFlow<CustomerUiState> = _uiState.asStateFlow()

    init {
        loadCustomers()
    }

    private fun loadCustomers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                repository.getCustomers().collect { customerList ->
                    _uiState.update { state ->
                        val filtered = filterList(customerList, state.searchQuery)
                        state.copy(
                            isLoading = false,
                            customers = customerList,
                            filteredCustomers = filtered
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Gagal memuat data pelanggan: ${e.localizedMessage ?: "Terjadi kesalahan"}"
                    )
                }
            }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _uiState.update { state ->
            val filtered = filterList(state.customers, newQuery)
            state.copy(searchQuery = newQuery, filteredCustomers = filtered)
        }
    }

    fun clearSearch() {
        onSearchQueryChange("")
    }

    private fun filterList(list: List<Customer>, query: String): List<Customer> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return list
        return list.filter {
            it.nama.lowercase().contains(q) ||
            it.nik.contains(q) ||
            it.hp.contains(q) ||
            it.alamat.lowercase().contains(q)
        }
    }

    fun openAddDialog() {
        _uiState.update {
            it.copy(
                isFormOpen = true,
                editingCustomer = null,
                formNama = "",
                formNik = "",
                formHp = "",
                formAlamat = "",
                namaError = null,
                nikError = null,
                hpError = null,
                alamatError = null
            )
        }
    }

    fun openEditDialog(customer: Customer) {
        _uiState.update {
            it.copy(
                isFormOpen = true,
                editingCustomer = customer,
                formNama = customer.nama,
                formNik = customer.nik,
                formHp = customer.hp,
                formAlamat = customer.alamat,
                namaError = null,
                nikError = null,
                hpError = null,
                alamatError = null
            )
        }
    }

    fun closeFormDialog() {
        _uiState.update {
            it.copy(
                isFormOpen = false,
                editingCustomer = null,
                namaError = null,
                nikError = null,
                hpError = null,
                alamatError = null
            )
        }
    }

    fun onFormNamaChange(value: String) {
        _uiState.update { it.copy(formNama = value, namaError = null) }
    }

    fun onFormNikChange(value: String) {
        _uiState.update { it.copy(formNik = value, nikError = null) }
    }

    fun onFormHpChange(value: String) {
        _uiState.update { it.copy(formHp = value, hpError = null) }
    }

    fun onFormAlamatChange(value: String) {
        _uiState.update { it.copy(formAlamat = value, alamatError = null) }
    }

    fun submitForm() {
        val state = _uiState.value

        // Validasi form lengkap
        val vNama = CustomerValidator.validateNama(state.formNama)
        val vNik = CustomerValidator.validateNik(state.formNik)
        val vHp = CustomerValidator.validateHp(state.formHp)
        val vAlamat = CustomerValidator.validateAlamat(state.formAlamat)

        if (!vNama.isValid || !vNik.isValid || !vHp.isValid || !vAlamat.isValid) {
            _uiState.update {
                it.copy(
                    namaError = vNama.errorMessage,
                    nikError = vNik.errorMessage,
                    hpError = vHp.errorMessage,
                    alamatError = vAlamat.errorMessage
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            val isEdit = state.editingCustomer != null
            val customerToSave = Customer(
                id = state.editingCustomer?.id ?: "",
                nama = state.formNama.trim(),
                nik = state.formNik.trim(),
                hp = state.formHp.trim(),
                alamat = state.formAlamat.trim(),
                createdAt = state.editingCustomer?.createdAt ?: System.currentTimeMillis()
            )

            val result = if (isEdit) {
                repository.updateCustomer(customerToSave)
            } else {
                repository.addCustomer(customerToSave)
            }

            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        isFormOpen = false,
                        editingCustomer = null,
                        successMessage = if (isEdit) "Data pelanggan ${customerToSave.nama} berhasil diperbarui!" else "Pelanggan baru ${customerToSave.nama} berhasil ditambahkan!"
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage = "Gagal menyimpan data: ${result.exceptionOrNull()?.localizedMessage ?: "Kesalahan tidak diketahui"}"
                    )
                }
            }
        }
    }

    fun requestDelete(customer: Customer) {
        _uiState.update { it.copy(customerToDelete = customer) }
    }

    fun cancelDelete() {
        _uiState.update { it.copy(customerToDelete = null) }
    }

    fun confirmDelete() {
        val target = _uiState.value.customerToDelete ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isDeleting = true) }
            val result = repository.deleteCustomer(target.id)
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        isDeleting = false,
                        customerToDelete = null,
                        successMessage = "Pelanggan ${target.nama} berhasil dihapus dari sistem."
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isDeleting = false,
                        customerToDelete = null,
                        errorMessage = "Gagal menghapus data: ${result.exceptionOrNull()?.localizedMessage ?: "Terjadi kesalahan"}"
                    )
                }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(errorMessage = null, successMessage = null) }
    }
}
