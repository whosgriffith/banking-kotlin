package com.banking.transactions.utils.mapper

interface Mapper<Domain, Entity> {
    fun toDTO(entity: Entity): Domain
    fun toEntity(domain: Domain): Entity
}