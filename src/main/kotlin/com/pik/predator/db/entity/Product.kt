package com.pik.predator.db.entity

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Products")
data class Product(
    val name: String
)