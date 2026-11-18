package com.example.goldencrown.data.repository

import com.example.goldencrown.data.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    fun getCustomers(): Flow<List<Customer>>
    suspend fun getCustomerById(id: String): Customer?
    suspend fun addCustomer(customer: Customer): Result<String>
    suspend fun updateCustomer(customer: Customer): Result<Unit>
    suspend fun deleteCustomer(id: String): Result<Unit>
}
