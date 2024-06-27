package com.banking.transactions.interfaces.controllers

import com.banking.transactions.domain.dto.TransactionDTO
import com.banking.transactions.domain.dto.TransactionToProcessDTO
import com.banking.transactions.domain.services.AccountService
import com.banking.transactions.domain.services.TransactionService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/transaction")
class TransactionController(
    private val transactionService: TransactionService,
    private val accountService: AccountService,
) {
    @PostMapping
    fun processTransaction(@Valid @RequestBody transactionToProcessDTO: TransactionToProcessDTO): ResponseEntity<out Any> {

        val transactionDTO = TransactionDTO(
            null,
            accountService.findById(transactionToProcessDTO.originAccountId),
            accountService.findById(transactionToProcessDTO.destinationAccountId),
            transactionToProcessDTO.amount
        )

        transactionService.queueTransaction(transactionDTO)
        return ResponseEntity("Transaction queued for processing", HttpStatus.ACCEPTED)
    }
}
