package com.pik.predator.db.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "Orders")
data class Order(
        @Id var orderId: Int,
        var email: String,
        var firstName: String,
        var lastName: String,
        var street: String,
        var houseNumber: String,
        var localNumber: String,
        var postalCode: String,
        var city: String,
        var paymentMethod: String,
        var products: List<BasicProductInfo>
)