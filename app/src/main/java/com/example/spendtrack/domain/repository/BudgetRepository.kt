package com.example.spendtrack.domain.repository

import com.example.spendtrack.domain.model.Budget
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    fun getBudgets() : Flow<List<Budget>>
    suspend fun setBudget(budget: Budget)
    suspend fun deleteBudget(id: Long)
}