package com.example.spendtrack.domain.repository

import com.example.spendtrack.domain.model.ExchangeRate

interface ExchangeRateRepository {

    suspend fun getExchangeRate() : Result<ExchangeRate>
}