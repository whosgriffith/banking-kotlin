package com.banking.transactions.utils

import com.banking.transactions.persistance.crud.AccountCrudRepository
import com.banking.transactions.persistance.entities.AccountEntity
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer(
    private val accountRepository: AccountCrudRepository
) {

    @EventListener(ContextRefreshedEvent::class)
    fun loadAccountsAfterStartup() {
        println("Loading accounts into the database")

        val account1 = AccountEntity(
            id = 1,
            bank = "Bank A",
            country = "Argentina",
            balance = 1000.0
        )

        val account2 = AccountEntity(
            id = 2,
            bank = "Bank B",
            country = "Brazil",
            balance = 2000.0
        )

        accountRepository.save(account1)
        accountRepository.save(account2)
    }
}