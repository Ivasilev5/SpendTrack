package com.example.spendtrack.domain.repository

import com.example.spendtrack.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

interface SettingRepository {

    fun getSettings(): Flow<AppSettings>
    suspend fun setBaseCurrency(currency: String)
}