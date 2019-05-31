package com.pik.predator.db.dto

data class CheckoutRequest(
    var firstName: String,
    var lastName: String,
    var email: String,
    var street: String,
    var houseNumber: String,
    var localNumber: String,
    var postalCode: String,
    var city: String,
    var paymentMethod: String
)