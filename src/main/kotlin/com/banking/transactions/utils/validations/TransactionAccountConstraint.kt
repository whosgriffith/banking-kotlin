package com.banking.transactions.utils.validations

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [TransactionAccountValidator::class])
annotation class ValidTransactionAccounts(
    val message: String = "Invalid accounts",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
