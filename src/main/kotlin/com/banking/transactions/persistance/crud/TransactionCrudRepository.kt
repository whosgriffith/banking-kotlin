package com.banking.transactions.persistance.crud

import com.banking.transactions.persistance.entities.TransactionEntity
import org.springframework.data.repository.CrudRepository

interface TransactionCrudRepository : CrudRepository<TransactionEntity, Long>
