package com.example.spendtrack.domain.repository

import com.example.spendtrack.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories() : Flow<List<Category>>

    suspend fun addCategory(category: Category)

    suspend fun deleteCategory(id: Long)
}