package com.banking.transactions

import com.banking.transactions.domain.dto.AccountDTO
import com.banking.transactions.domain.dto.TransactionDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TransactionDTOTest {

    @Test
    fun `calcularImpuestos should return 0 percent tax when the bank its the same`() {
        val originAccount = AccountDTO(id = 1, country = "US", bank = "BankA")
        val destinationAccount = AccountDTO(id = 2, country = "AR", bank = "BankA")
        val transaction = TransactionDTO(
            id = 1,
            originAccountDTO = originAccount,
            destinationAccountDTO = destinationAccount,
            amount = 100.0
        )
        val tax = transaction.calcularImpuestos()
        assertEquals(0.0, tax)
    }

    @Test
    fun `calcularImpuestos should return 1 percent tax when banks are different but countries are the same`() {
        val originAccount = AccountDTO(id = 1, country = "US", bank = "BankA")
        val destinationAccount = AccountDTO(id = 2, country = "US", bank = "BankB")
        val transaction = TransactionDTO(
            id = 1,
            originAccountDTO = originAccount,
            destinationAccountDTO = destinationAccount,
            amount = 100.0
        )
        val tax = transaction.calcularImpuestos()
        assertEquals(0.01, tax)
    }

    @Test
    fun `calcularImpuestos should return zero tax when both banks and countries are the same`() {
        val originAccount = AccountDTO(id = 1, country = "US", bank = "BankA")
        val destinationAccount = AccountDTO(id = 2, country = "US", bank = "BankA")
        val transaction = TransactionDTO(
            id = 1,
            originAccountDTO = originAccount,
            destinationAccountDTO = destinationAccount,
            amount = 100.0
        )
        val tax = transaction.calcularImpuestos()
        assertEquals(0.0, tax)
    }

    @Test
    fun `calcularImpuestos should return 5 percent tax when countries and banks are different`() {
        val originAccount = AccountDTO(id = 1, country = "US", bank = "BankA")
        val destinationAccount = AccountDTO(id = 2, country = "AR", bank = "BankB")
        val transaction = TransactionDTO(
            id = 1,
            originAccountDTO = originAccount,
            destinationAccountDTO = destinationAccount,
            amount = 100.0
        )
        val tax = transaction.calcularImpuestos()
        assertEquals(0.05, tax)
    }

    @Test
    fun `calcularImpuestos should handle zero amount correctly`() {
        val originAccount = AccountDTO(id = 1, country = "US", bank = "BankA")
        val destinationAccount = AccountDTO(id = 2, country = "AR", bank = "BankB")
        val transaction = TransactionDTO(
            id = 1,
            originAccountDTO = originAccount,
            destinationAccountDTO = destinationAccount,
            amount = 0.0
        )
        val tax = transaction.calcularImpuestos()
        assertEquals(0.0, tax)
    }
}
