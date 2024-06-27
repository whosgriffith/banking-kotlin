package com.banking.transactions.domain.services

import com.banking.transactions.domain.dto.AccountDTO
import com.banking.transactions.domain.dto.TransactionDTO
import com.banking.transactions.utils.mapper.AccountMapper
import com.banking.transactions.persistance.crud.AccountCrudRepository
import com.banking.transactions.utils.exceptions.ValidationException
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountCrudRepository,
    private val accountMapper: AccountMapper,
) {

    fun findById(id: Long): AccountDTO {
        val accountEntity = accountRepository.findById(id).orElse(null)
        return accountMapper.toDTO(accountEntity)
    }

    fun getTestAccount(): AccountDTO {
        val accountEntity = accountRepository.findById(1).orElse(null)
        if (accountEntity != null) {
            return accountMapper.toDTO(accountEntity)
        } else {
            throw Exception("Error searching the test account.")
        }
    }

    fun getAll(): List<AccountDTO> =
        accountRepository.findAll().map{ entity -> accountMapper.toDTO(entity) }

    fun create(accountDTO: AccountDTO): AccountDTO {
        if (accountRepository.existsById(accountDTO.id)) {
            throw ValidationException("An account with that ID already exists.")
        }

        val savedAccount = accountRepository.save(accountMapper.toEntity(accountDTO))
        return accountMapper.toDTO(savedAccount)
    }

    fun applyTransaction(transaction: TransactionDTO): Boolean {
        val originAccountEntity = accountRepository.findById(transaction.originAccountDTO.id).orElse(null)
        val destinationAccountEntity = accountRepository.findById(transaction.destinationAccountDTO.id).orElse(null)

        if (originAccountEntity == null) {
            throw ValidationException("Origin account does not exist.")
        }
        if (destinationAccountEntity == null) {
            throw ValidationException("Destination account does not exist.")
        }

        if (originAccountEntity.balance < transaction.amount) {
            throw ValidationException("Insufficient funds in the origin account.")
        }

        originAccountEntity.balance -= transaction.amount
        destinationAccountEntity.balance += transaction.amount

        accountRepository.save(originAccountEntity)
        accountRepository.save(destinationAccountEntity)

        return true
    }
}

