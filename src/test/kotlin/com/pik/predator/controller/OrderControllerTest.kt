package com.pik.predator.controller

import com.nhaarman.mockitokotlin2.whenever
import com.pik.predator.controller.order.OrderController
import com.pik.predator.db.dto.SummaryOrderInfo
import com.pik.predator.db.dto.mapToSummaryInfo
import com.pik.predator.db.dto.mapToSummaryInfoList
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
import java.time.LocalDate
import javax.servlet.http.HttpServletResponse.*

@RunWith(SpringRunner::class)
class OrderControllerTest {

    private val orders = listOf(
        Order(
            1, "1", "John", "Rambo", "john.rambo@vietnam.com", "Wallstreet", "120", "18", "06-300", "New York", "PayPal",
            productListFromIds(1, 3), LocalDate.of(2019, 6, 2).format(Order.DATE_FORMATTER)
        ),
        Order(
            1, "1", "Bambo", "Mambo", "bambo.mambo@poranekkojota.com", "Jakastam", "120", "18", "06-300", "Warszawa", "PayPal",
            productListFromIds(1, 2), LocalDate.of(2019, 6, 2).format(Order.DATE_FORMATTER)
        )
    )

    //tested object
    private lateinit var orderController: OrderController

    //dependencies
    @Spy lateinit var orderRepository: OrderRepositorySpy

    //other mocks
    @Spy lateinit var response: HttpServletResponseSpy

    @Before
    fun setup() {
        whenever(orderRepository.findById(1)).thenReturn(Optional.of(orders[0]))
        whenever(orderRepository.findByUserId("1")).thenReturn(orders)
        orderController = OrderController(orderRepository)
    }

    //helpers
    private lateinit var savedOrder: Order

    abstract inner class OrderRepositorySpy : OrderRepository {
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

    @Test
    fun `when get order info then info is returned`() {
        assertEquals(
            orders[0],
            orderController.getOrderInfo(1, response)
        )
    }

    @Test
    fun `when get order info then response status is 200 OK`() {
        orderController.getOrderInfo(1, response)
        response.verifyStatus(SC_OK)
    }

    @Test
    fun `when get order info of not existing order then null is returned`() {
        assertEquals(
            null,
            orderController.getOrderInfo(2, response)
        )
    }

    @Test
    fun `when get order info of not existing order the response status is 404 NOT FOUND`() {
        orderController.getOrderInfo(2, response)
        response.verifyStatus(SC_NOT_FOUND)
    }

    @Test
    fun `test map Order to SummartOrderInfo`() {
        assertEquals(
            SummaryOrderInfo(1, productListFromIds(1, 2), 15000.toBigDecimal(), LocalDate.of(2019, 6, 2).format(Order.DATE_FORMATTER)),
            orders[1].mapToSummaryInfo()
        )
    }

    @Test
    fun `when get orders of user then appropriate orders are returned`() {
        assertEquals(
            orders.mapToSummaryInfoList(),
            orderController.getOrdersOfUser("1", response)
        )
    }

    @Test
    fun `when get orders of user then response status is 200 OK`() {
        orderController.getOrdersOfUser("1", response)
        response.verifyStatus(SC_OK)
    }

    @Test
    fun `when get orders of user who didnt submited any orders then empty list is returned`() {
        assertEquals(
            emptyList<SummaryOrderInfo>(),
            orderController.getOrdersOfUser("2", response)
        )
    }

    @Test
    fun `when get orders of user who didnt submited any orders then response status is 200 OK`() {
        orderController.getOrdersOfUser("2", response)
        response.verifyStatus(SC_OK)
    }
}