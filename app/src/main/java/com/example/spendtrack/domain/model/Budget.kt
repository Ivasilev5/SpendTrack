package com.example.spendtrack.domain.model

import java.math.BigDecimal

data class Budget(
    val id : Long,
    val categoryId : Long?,
    val limitAmount: BigDecimal,
    val period: BudgetPeriod
)

enum class BudgetPeriod {
    MONTHLY
}