package com.banking.transactions.utils.validations

import com.banking.transactions.domain.dto.TransactionToProcessDTO
import com.banking.transactions.persistance.crud.AccountCrudRepository
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.factory.annotation.Autowired

class TransactionAccountValidator : ConstraintValidator<ValidTransactionAccounts, TransactionToProcessDTO> {

    @Autowired private lateinit var accountRepository: AccountCrudRepository

    override fun isValid(transactionToProcessDTO: TransactionToProcessDTO, context: ConstraintValidatorContext): Boolean {
        val originExists = accountRepository.existsById(transactionToProcessDTO.originAccountId)
        val destinationExists = accountRepository.existsById(transactionToProcessDTO.destinationAccountId)

        if (!originExists) {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate("Origin account not found")
                .addPropertyNode("originAccountId")
                .addConstraintViolation()
            return false
        }

        if (!destinationExists) {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate("Destination account not found")
                .addPropertyNode("destinationAccountId")
                .addConstraintViolation()
            return false
        }

        if (transactionToProcessDTO.originAccountId == transactionToProcessDTO.destinationAccountId) {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate("Origin and destination accounts must be different")
                .addPropertyNode("originAccountId")
                .addConstraintViolation()
            return false
        }

        return true
    }
}