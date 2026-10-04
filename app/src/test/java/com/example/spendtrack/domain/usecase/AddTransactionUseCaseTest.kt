package com.example.spendtrack.domain.usecase

import com.example.spendtrack.domain.model.Transaction
import com.example.spendtrack.domain.model.TransactionType
import com.example.spendtrack.fake.FakeTransactionRepository

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDate

class AddTransactionUseCaseTest {

    private lateinit var repository: FakeTransactionRepository
    private lateinit var useCase: AddTransactionUseCase

    @Before
    fun setUp() {
        repository = FakeTransactionRepository()
        useCase = AddTransactionUseCase(repository)
    }

    private fun validTransaction() = Transaction(
        id = 0,
        amount = BigDecimal("100.00"),
        currency = "USD",
        transactionType = TransactionType.EXPENSE,
        categoryId = 1,
        date = LocalDate.now(),
        note = null
    )

    @Test
    fun `valid transaction is added and returns Success`() = runTest {
        val result = useCase(validTransaction())

        assertEquals(AddTransactionResult.Success, result)
        assertEquals(1, repository.transactions.size)
    }

    @Test
    fun `transaction amount is negative`() = runTest {
        val result =
            useCase(validTransaction().copy(amount = BigDecimal.valueOf(-100))) as AddTransactionResult.ValidationFailed

        assertEquals(listOf(AddTransactionError.INVALID_AMOUNT), result.errors)
        assertTrue(repository.transactions.isEmpty())
    }

    @Test
    fun `transaction amount equals zero`() = runTest {
        val result =
            useCase(validTransaction().copy(amount = BigDecimal.ZERO)) as AddTransactionResult.ValidationFailed

        assertEquals(listOf(AddTransactionError.INVALID_AMOUNT), result.errors)
        assertTrue(repository.transactions.isEmpty())
    }

    @Test
    fun `transaction currency is empty`() = runTest {
        val result =
            useCase(validTransaction().copy(currency = "")) as AddTransactionResult.ValidationFailed

        assertEquals(listOf(AddTransactionError.INVALID_CURRENCY), result.errors)
        assertTrue(repository.transactions.isEmpty())
    }

    @Test
    fun `transaction currency is not exist`() = runTest {
        val result =
            useCase(validTransaction().copy(currency = "XYZ")) as AddTransactionResult.ValidationFailed

        assertEquals(listOf(AddTransactionError.INVALID_CURRENCY), result.errors)
        assertTrue(repository.transactions.isEmpty())
    }

    @Test
    fun `transaction date in the future`() = runTest {
        val result =
            useCase(
                validTransaction().copy(
                    date = LocalDate.now().plusDays(1)
                )
            ) as AddTransactionResult.ValidationFailed

        assertEquals(listOf(AddTransactionError.FUTURE_DATE), result.errors)
        assertTrue(repository.transactions.isEmpty())
    }

    @Test
    fun `several validation errors`() = runTest {
        val result = useCase(
            validTransaction().copy(
                currency = "",
                amount = BigDecimal.ZERO
            )
        ) as AddTransactionResult.ValidationFailed

        assertTrue(AddTransactionError.INVALID_AMOUNT in result.errors)
        assertTrue(AddTransactionError.INVALID_CURRENCY in result.errors)
        assertTrue(repository.transactions.isEmpty())
    }
}
