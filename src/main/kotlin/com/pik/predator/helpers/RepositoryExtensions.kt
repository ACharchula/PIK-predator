package com.pik.predator.helpers

import org.springframework.data.mongodb.repository.MongoRepository

fun <T, ID> MongoRepository<T, ID>.getById(id: ID): T? =
    findById(id).orElseGet { null }