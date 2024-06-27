package com.banking.transactions.persistance.entities

import jakarta.persistence.*

@Entity
data class TransactionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name="origin_account")
    val originAccount: AccountEntity,
    @ManyToOne
    @JoinColumn(name="destination_account")
    val destinationAccount: AccountEntity,
    val amount: Double
)
