package com.banking.transactions.persistance.entities

import jakarta.persistence.*

@Entity
data class AccountEntity (
    @Id
    val id: Long,
    val bank: String,
    val country: String,
    var balance: Double,
    @OneToMany(mappedBy="destinationAccount")
    val transactionsReceived: MutableList<TransactionEntity>? = null,
    @OneToMany(mappedBy="originAccount")
    val transactionsSent: MutableList<TransactionEntity>? = null
)
