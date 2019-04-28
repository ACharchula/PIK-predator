package com.pik.predator.db.data

import java.math.BigDecimal

data class BasicProductInfo(
        var productId: Int,
        var producer: String,
        var model: String,
        var price: BigDecimal
)

fun Product.mapToBasicInfo() = BasicProductInfo(productId, manufacturer, model, price)
fun List<Product>.mapToBasicInfo() = map { it.mapToBasicInfo() }