package com.example.spendtrack.domain.model

import java.math.BigDecimal

data class BudgetProgress(
    val budget: Budget,
    val spentAmount: BigDecimal
) {
    val remainingAmount: BigDecimal
        get() = budget.limitAmount - spentAmount
}
