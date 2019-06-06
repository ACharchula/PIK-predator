package com.pik.predator.controller.order

import com.pik.predator.db.dto.SummaryOrderInfo
import com.pik.predator.db.dto.mapToSummaryInfoList
import com.pik.predator.db.entities.Order
import com.pik.predator.db.repository.OrderRepository
import com.pik.predator.helpers.*
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
class OrderController(
    private val orderRepository: OrderRepository
) {
    @GetMapping("/orders/{orderId}")
    @CrossOrigin
    fun getOrderInfo(@PathVariable orderId: Int, response: HttpServletResponse): Order? {

        return orderRepository.getById(orderId)
            .alsoNullable(
                onNotNull = { response.ok() },
                onNull = { response.notFound() }
            )
    }

    @PostMapping("/orders/{orderId}")
    @CrossOrigin
    fun markOrderAsPaid(@PathVariable orderId: Int, response: HttpServletResponse) {

        val order = orderRepository.getById(orderId)

        if (order != null) {
            order.isPaid = true
            orderRepository.save(order)
        }
        else response.notFound()
    }

    @GetMapping("/users/{userId}/orders")
    @CrossOrigin
    fun getOrdersOfUser(@PathVariable userId: String, response: HttpServletResponse): List<SummaryOrderInfo> {

        return orderRepository.findByUserId(userId)
            .mapToSummaryInfoList()
    }
}