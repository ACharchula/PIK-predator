package com.pik.predator.db.dto

import com.pik.predator.db.entities.Product
import java.math.BigDecimal
import java.time.LocalDate

data class BasicProductInfo(
    var productId: Int,
    var manufacturer: String,
    var model: String,
    var price: BigDecimal,
    var imageUrl: String
)

fun Product.mapToBasicInfo() =
    BasicProductInfo(productId, manufacturer, model, price, imageUrl)

fun List<Product>.mapToBasicInfoList() =
    map { it.mapToBasicInfo() }