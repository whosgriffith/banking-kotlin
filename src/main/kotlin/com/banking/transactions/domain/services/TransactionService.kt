package com.banking.transactions.domain.services

import com.banking.transactions.domain.dto.TransactionDTO
import com.banking.transactions.persistance.crud.TransactionCrudRepository
import com.banking.transactions.utils.mapper.TransactionMapper
import org.springframework.stereotype.Service
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import java.io.File
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

@Service
@EnableAsync
class TransactionService(
    private val transactionRepository: TransactionCrudRepository,
    private val transactionMapper: TransactionMapper,
    private val accountService: AccountService,
) {
    private val executor = ThreadPoolExecutor( // Basic configuration, could be optimized
        20,
        50,
        60L,
        TimeUnit.SECONDS,
        LinkedBlockingQueue(1000)
    )

    fun queueTransaction(transactionDTO: TransactionDTO) {
        executor.submit {
            processTransaction(transactionDTO)
        }
    }

    @Async
    fun processTransaction(transactionDTO: TransactionDTO) {
        try {
            val tax = transactionDTO.calcularImpuestos()
            transactionDTO.amount -= transactionDTO.amount * tax
            val transactionEntity = transactionMapper.toEntity(transactionDTO)
            val savedTransaction = transactionRepository.save(transactionEntity)
            accountService.applyTransaction(transactionDTO)
            logTransaction(savedTransaction.id, transactionDTO, "Completed")
        }
        catch (e: Exception) {
            logTransaction(null, transactionDTO, "Error")
        }
    }

    private fun logTransaction(
        transactionId: Long? = null,
        transaction: TransactionDTO,
        status: String
    ) {
        val logFile = File("transactions.log")
        logFile.appendText(
            "Transaction ID ${transactionId}, " +
                    "Origin ID ${transaction.originAccountDTO.id}, " +
                    "Destination ID ${transaction.destinationAccountDTO.id}, " +
                    "Amount ${transaction.amount}, " +
                    "Status: ${status}\n"
        )
    }
}
