package com.pik.predator.db.entities

import com.pik.predator.db.dto.BasicProductInfo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Carts")
data class Cart(
    @Id var cartId: Int,
    var userId: String,
    var items: MutableList<BasicProductInfo>
) {
    companion object {
        @Transient const val SEQUENCE_NAME = "cart_sequence"
    }
}