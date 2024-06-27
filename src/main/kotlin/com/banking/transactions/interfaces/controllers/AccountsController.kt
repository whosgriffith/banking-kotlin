package com.banking.transactions.interfaces.controllers

import com.banking.transactions.domain.dto.AccountDTO
import com.banking.transactions.domain.services.AccountService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val accountService: AccountService,
) {

    @GetMapping
    fun getAllAccounts(): List<AccountDTO> = accountService.getAll()

    @PostMapping
    fun createAccount(@Valid @RequestBody accountDTO: AccountDTO): ResponseEntity<AccountDTO> {
        val savedAccount = accountService.create(accountDTO)
        return ResponseEntity(savedAccount, HttpStatus.CREATED)
    }
}


@RestController
@RequestMapping("/api/account")
class TestAccountController(
    private val accountService: AccountService,
) {
    @GetMapping
    fun getTestAccount(): AccountDTO = accountService.getTestAccount()
}