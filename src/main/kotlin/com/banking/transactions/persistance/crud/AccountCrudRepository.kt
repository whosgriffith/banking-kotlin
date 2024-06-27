package com.banking.transactions.persistance.crud

import com.banking.transactions.persistance.entities.AccountEntity
import org.springframework.data.repository.CrudRepository

interface AccountCrudRepository : CrudRepository<AccountEntity, Long>
