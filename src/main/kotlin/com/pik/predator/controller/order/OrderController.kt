package com.pik.predator.controller.order

import com.pik.predator.db.repository.OrderRepository
import com.pik.predator.helpers.getById
import com.pik.predator.helpers.letNullable
import com.pik.predator.helpers.notFound
import com.pik.predator.helpers.ok
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.HttpServletResponse

class OrderController(
    private val orderRepository: OrderRepository
) {
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