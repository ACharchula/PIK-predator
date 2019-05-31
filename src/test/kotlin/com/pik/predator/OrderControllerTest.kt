package com.pik.predator

import com.nhaarman.mockitokotlin2.whenever
import com.pik.predator.controller.order.OrderController
import com.pik.predator.db.entities.Order
import com.pik.predator.db.repository.OrderRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Spy
import java.util.*
import javax.servlet.http.HttpServletResponse
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner
import javax.servlet.http.HttpServletResponse.*

@RunWith(SpringRunner::class)
class OrderControllerTest {

    private val order = Order(
        1, "John", "Rambo", "john.rambo@vietnam.com", "Wallstreet", "120", "18", "06-300", "New York", "PayPal",
        productListFromIds(1, 3)
    )

    //tested object
    private lateinit var orderController: OrderController

    //dependencies
    @Spy lateinit var orderRepository: SpyOrderRepository

    //other mocks
    @Mock lateinit var response: HttpServletResponse

    @Before
    fun setup() {
        whenever(orderRepository.findById(1)).thenReturn(Optional.of(order))
        orderController = OrderController(orderRepository)
    }

    //helpers
    private lateinit var savedOrder: Order

    abstract inner class SpyOrderRepository : OrderRepository {
        override fun <S : Order> save(order: S): S {
            savedOrder = order
            return order
        }
    }

    @Test
    fun `when mark order as paid then order is marked as paid`() {
        orderController.markOrderAsPaid(1, response)
        assertEquals(true, savedOrder.isPaid)
    }

    @Test
    fun `when mark existing order as paid then response status is 200 OK`() {
        orderController.markOrderAsPaid(1, response)
        response.verifyStatus(SC_OK)
    }

    @Test
    fun `when mark non existing order as paid then response status is 404 NOT FOUND`() {
        orderController.markOrderAsPaid(2, response)
        response.verifyStatus(SC_NOT_FOUND)
    }
}