package com.banking.transactions.domain.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.*

data class AccountDTO(
    @Valid

    @field:NotNull(message="ID is required")
    @field:Positive(message="ID must be positive")
    var id: Long,

    @field:NotNull(message="Bank is required")
    @field:NotBlank(message="Bank is required")
    var bank: String,

    @field:NotNull(message="Country is required")
    @field:NotBlank(message="Country is required")
    var country: String,

    var balance: Double = 0.0,

    val transactionsReceived: MutableList<TransactionDTO>? = null,
    val transactionsSent: MutableList<TransactionDTO>? = null,
)
