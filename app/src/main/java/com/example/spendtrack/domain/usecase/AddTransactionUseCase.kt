package com.example.spendtrack.domain.usecase


import com.example.spendtrack.domain.model.Transaction
import com.example.spendtrack.domain.repository.TransactionRepository
import java.math.BigDecimal
import java.time.LocalDate
import java.util.Currency

class AddTransactionUseCase(
    val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction): AddTransactionResult {
        val errors = mutableListOf<AddTransactionError>()
        if (transaction.amount <= BigDecimal.ZERO) {
            errors.add(AddTransactionError.INVALID_AMOUNT)
        }
        if (transaction.date.isAfter(LocalDate.now())) {
            errors.add(AddTransactionError.FUTURE_DATE)
        }
        if (!transaction.currency.isValidIso4217()) {
            errors.add(AddTransactionError.INVALID_CURRENCY)
        }

        return if (errors.isEmpty()) {
            transactionRepository.addTransaction(transaction)
            AddTransactionResult.Success
        } else AddTransactionResult.ValidationFailed(errors)
    }
}


sealed class AddTransactionResult {
    data object Success : AddTransactionResult()
    data class ValidationFailed(val errors: List<AddTransactionError>) : AddTransactionResult()
}

enum class AddTransactionError { INVALID_AMOUNT, FUTURE_DATE, INVALID_CURRENCY }


private fun String.isValidIso4217(): Boolean {
    if (this.isBlank()) return false
    return runCatching { Currency.getInstance(this.trim().uppercase()) }.isSuccess
}