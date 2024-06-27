package com.banking.transactions.utils.mapper

import com.banking.transactions.domain.dto.AccountDTO
import com.banking.transactions.domain.dto.TransactionDTO
import com.banking.transactions.persistance.entities.TransactionEntity
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
class TransactionMapper(
    @Lazy val accountMapper: AccountMapper
): Mapper<TransactionDTO, TransactionEntity> {

    override fun toDTO(entity: TransactionEntity): TransactionDTO =
        TransactionDTO(
            entity.id,
            AccountDTO(
                entity.originAccount.id,
                entity.originAccount.bank,
                entity.originAccount.country,
                entity.originAccount.balance
            ),
            AccountDTO(
                entity.destinationAccount.id,
                entity.destinationAccount.bank,
                entity.destinationAccount.country,
                entity.destinationAccount.balance
            ),
            entity.amount
        )

    override fun toEntity(domain: TransactionDTO): TransactionEntity =
        TransactionEntity(
            domain.id,
            accountMapper.toEntity(domain.originAccountDTO),
            accountMapper.toEntity(domain.destinationAccountDTO),
            domain.amount
        )
}