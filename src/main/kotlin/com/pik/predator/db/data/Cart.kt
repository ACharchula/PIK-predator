package com.pik.predator.db.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Carts")
data class Cart(
    @Id var cartId: Int,
    var userId: Int,
    val items: MutableList<Product>
) {
    companion object {
        @Transient const val SEQUENCE_NAME = "cart_sequence"
    }
}