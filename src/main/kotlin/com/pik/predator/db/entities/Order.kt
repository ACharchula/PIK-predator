package com.pik.predator.db.entities

import com.pik.predator.db.dto.BasicProductInfo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Orders")
data class Order(
    @Id var orderId: Int,
    var firstName: String,
    var lastName: String,
    var email: String,
    var street: String,
    var houseNumber: String,
    var localNumber: String,
    var postalCode: String,
    var city: String,
    var paymentMethod: String,
    var products: List<BasicProductInfo>,
    var isPaid: Boolean = false
) {
    companion object {
        @Transient const val SEQUENCE_NAME = "order_sequence"
    }
}