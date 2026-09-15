package com.example.spendtrack.domain.model

data class Category(
    val id : Long,
    val name: String,
    val icon: String,
    val color: Long,
    val type: TransactionType
)