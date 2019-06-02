package com.pik.predator.db.dto

import com.pik.predator.db.entities.Order
import com.pik.predator.db.entities.Product
import java.math.BigDecimal
import java.time.LocalDate

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
        products.map { it.price }.reduce { acc, bigDecimal -> acc + bigDecimal },
        date
    )

fun List<Order>.mapToSummaryInfoList() =
    map { it.mapToSummaryInfo() }