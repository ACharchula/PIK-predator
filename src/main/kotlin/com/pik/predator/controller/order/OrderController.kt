package com.pik.predator.controller.order

import com.pik.predator.db.entities.Order
import com.pik.predator.db.repository.OrderRepository
import com.pik.predator.helpers.*
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.HttpServletResponse

class OrderController(
    private val orderRepository: OrderRepository
) {
    @GetMapping("orders/{orderId")
    @CrossOrigin
    fun getOrderInfo(@PathVariable orderId: Int, response: HttpServletResponse): Order? {
        return orderRepository.getById(orderId)
            .applyNullable(
                onNotNull = { response.ok() },
                onNull = { response.notFound() }
            )
    }

    @PostMapping("/orders/{orderId}")
    @CrossOrigin
    fun markOrderAsPaid(@PathVariable orderId: Int, response: HttpServletResponse) {
        orderRepository.getById(orderId)
            .letNullable(
                onNotNull = { order ->
                    order.isPaid = true
                    orderRepository.save(order)
                    response.ok()
                },
                onNull = { response.notFound() }
            )
    }
}