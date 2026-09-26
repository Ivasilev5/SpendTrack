package com.example.spendtrack.domain.usecase

import com.example.spendtrack.domain.model.BudgetProgress
import com.example.spendtrack.domain.model.TransactionType
import com.example.spendtrack.domain.repository.BudgetRepository
import com.example.spendtrack.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.YearMonth

class GetBudgetProgressUseCase(
    private val budgetRepository: BudgetRepository,
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke(
    ): Flow<List<BudgetProgress>> {

        return combine(
            budgetRepository.getBudgets(),
            transactionRepository.getTransactions()
        ) { budgets, transactions ->
            val currentMonthTransactions = transactions.filter {
                YearMonth.now() == YearMonth.from(it.date)
            }
            budgets.map { budget ->
                val spent = currentMonthTransactions
                    .filter { it.transactionType == TransactionType.EXPENSE }
                    .filter { budget.categoryId == null || budget.categoryId == it.categoryId }
                    .sumOf { it.amount }
                BudgetProgress(budget, spent)
            }

        }

    }
}