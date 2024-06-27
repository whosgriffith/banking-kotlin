    package com.banking.transactions.domain.dto

    import com.banking.transactions.utils.validations.ValidTransactionAccounts
    import jakarta.validation.Valid
    import jakarta.validation.constraints.*

    data class TransactionDTO(
        @Valid

        var id: Long?,

        @field:NotNull(message = "Origin account must be specified")
        var originAccountDTO: AccountDTO,

        @field:NotNull(message = "Destination account must be specified")
        var destinationAccountDTO: AccountDTO,

        @field:Positive(message="Amount must be positive")
        var amount: Double = 0.0
    ) {
        fun calcularImpuestos(): Double {
            val tax: Double = if (originAccountDTO.bank == destinationAccountDTO.bank || amount == 0.0) {
                0.0
            } else if (originAccountDTO.country == destinationAccountDTO.country) {
                0.01
            } else {
                0.05
            }
            return tax
        }
    }

    @ValidTransactionAccounts
    data class TransactionToProcessDTO(
        @Valid

        var id: Long?,

        @field:NotNull(message = "Origin account ID must be specified")
        @field:Positive
        var originAccountId: Long,

        @field:NotNull(message = "Destination account ID must be specified")
        @field:Positive
        var destinationAccountId: Long,

        @field:Positive(message="Amount must be positive")
        var amount: Double = 0.0
    )
