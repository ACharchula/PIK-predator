package com.pik.predator.db.data

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Carts")
data class Cart(
    var userId: Int,
    val items: MutableList<Product>
)