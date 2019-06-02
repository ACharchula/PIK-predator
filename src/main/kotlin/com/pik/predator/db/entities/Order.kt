package com.pik.predator.db.entities

import com.pik.predator.db.dto.BasicProductInfo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Document(collection = "Orders")
data class Order(
    @Id var orderId: Int,
    var userId: String,
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
    var date: String,
    var isPaid: Boolean = false
) {
    companion object {
        @Transient const val SEQUENCE_NAME = "order_sequence"
        @Transient val DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    }
}