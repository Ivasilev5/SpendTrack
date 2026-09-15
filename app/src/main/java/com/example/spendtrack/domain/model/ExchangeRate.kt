package com.example.spendtrack.domain.model

import kotlin.time.Instant


data class ExchangeRate(
    val baseCurrency: String,
    val targetCurrency: String,
    val rate: Double,
    val updatedAt: Instant
)
