package com.pik.predator.db.dto

import com.pik.predator.db.entities.Order
import java.math.BigDecimal

data class SummaryOrderInfo(
    var orderId: Int,
    var products: List<BasicProductInfo>,
    var totalPrice: BigDecimal,
    var date: String
)

fun Order.mapToSummaryInfo() =
    SummaryOrderInfo(
        orderId,
        products,
            if (products.isNotEmpty())
                products.map { it.price }.reduce { acc, bigDecimal -> acc + bigDecimal }
            else BigDecimal.ZERO,
        date
    )

fun List<Order>.mapToSummaryInfoList() =
    map { it.mapToSummaryInfo() }