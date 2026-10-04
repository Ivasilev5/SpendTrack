package com.example.spendtrack.fake

import com.example.spendtrack.domain.model.Transaction
import com.example.spendtrack.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTransactionRepository : TransactionRepository {

    private val state = MutableStateFlow<List<Transaction>>(emptyList())

    val transactions: List<Transaction>
        get() = state.value

    override fun getTransactions(): Flow<List<Transaction>> = state

    override suspend fun addTransaction(transaction: Transaction) {
        state.value += transaction
    }

    override suspend fun deleteTransaction(id: Long) {
        state.value = state.value.filterNot { it.id == id }
    }
}
