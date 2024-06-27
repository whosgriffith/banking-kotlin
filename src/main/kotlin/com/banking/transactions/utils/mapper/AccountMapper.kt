package com.banking.transactions.utils.mapper

import com.banking.transactions.domain.dto.AccountDTO
import com.banking.transactions.persistance.entities.AccountEntity
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
class AccountMapper(
    @Lazy val transactionMapper: TransactionMapper
): Mapper<AccountDTO, AccountEntity> {

    override fun toDTO(entity: AccountEntity): AccountDTO =
        AccountDTO(
            entity.id,
            entity.bank,
            entity.country,
            entity.balance,
            entity.transactionsReceived?.map { transactionMapper.toDTO(it) }?.toMutableList(),
            entity.transactionsSent?.map { transactionMapper.toDTO(it) }?.toMutableList()
        )

    override fun toEntity(domain: AccountDTO): AccountEntity =
        AccountEntity(
            domain.id,
            domain.bank,
            domain.country,
            domain.balance
        )
}