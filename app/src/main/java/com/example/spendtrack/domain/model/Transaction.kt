package com.example.spendtrack.domain.model

import java.math.BigDecimal
import java.time.LocalDate

data class Transaction(
    val id: Long,
    val amount: BigDecimal,
    val currency: String,
    val transactionType: TransactionType,
    val categoryId: Long,
    val date: LocalDate,
    val note: String?
)

enum class TransactionType {
    INCOME, EXPENSE
}
