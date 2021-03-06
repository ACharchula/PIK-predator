package com.pik.predator.controller

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pik.predator.controller.cart.CartController
import com.pik.predator.db.dto.CheckoutRequest
import com.pik.predator.db.dto.BasicProductInfo
import com.pik.predator.db.entities.*
import com.pik.predator.db.repository.CartRepository
import com.pik.predator.db.repository.OrderRepository
import com.pik.predator.db.repository.ProductRepository
import com.pik.predator.helpers.emptyMutableList
import com.pik.predator.service.SequenceGenerator
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.springframework.test.context.junit4.SpringRunner
import org.junit.Assert.*
import org.mockito.Spy
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.*
import java.time.LocalDate

@RunWith(SpringRunner::class)
class CartControllerTest {

    private val cart = Cart(1, "1", emptyMutableList())

    //tested object
    private lateinit var cartController: CartController

    //dependencies
    @Spy lateinit var cartRepository: CartRepositorySpy
    @Mock lateinit var productRepository: ProductRepository
    @Spy lateinit var orderRepository: OrderRepositorySpy
    @Mock lateinit var sequenceGenerator: SequenceGenerator

    //other mocks
    @Spy lateinit var response: HttpServletResponseSpy

    @Before
    fun setup() {
        whenever(cartRepository.findByUserId("1")).thenReturn(cart)
        whenever(cartRepository.findByUserId("2")).thenReturn(null)

        whenever(productRepository.findById(1)).thenReturn(Optional.of(products[0]))
        whenever(productRepository.findById(2)).thenReturn(Optional.of(products[1]))
        whenever(productRepository.findById(3)).thenReturn(Optional.of(products[2]))

        whenever(sequenceGenerator.nextId(Cart.SEQUENCE_NAME)).thenReturn(2)
        whenever(sequenceGenerator.nextId(Order.SEQUENCE_NAME)).thenReturn(1)

        cartController = CartController(cartRepository, productRepository, orderRepository, sequenceGenerator)
    }

    //helpers
    private lateinit var savedCart: Cart
    private lateinit var savedOrder: Order

    abstract inner class CartRepositorySpy : CartRepository {
        override fun <S : Cart> save(cart: S): S {
            savedCart = cart
            return cart
        }
    }

    abstract inner class OrderRepositorySpy : OrderRepository {
        override fun <S : Order> save(order: S): S {
            savedOrder = order
            return order
        }
    }

    // getProductsInCart
    //----------------------------------------------------------------------------------
    @Test
    fun `when get products of cart with valid userId then products are returned`() {
        cart.items = productListFromIds(1, 3)

        assertEquals(
            productListFromIds(1, 3),
            cartController.getProductsInCart(cart.userId, response)
        )
    }

    @Test
    fun `when get products of cart with valid userId then response status is 200 OK`() {
        cartController.getProductsInCart(cart.userId, response)
        response.verifyStatus(SC_OK)
    }

    @Test
    fun `when get products of cart with invalid userId then null is returned`() {
        assertEquals(
            null,
            cartController.getProductsInCart("2", response)
        )
    }

    @Test
    fun `when get products of cart with invalid userId then response status is 404 NOT FOUND`() {
        cartController.getProductsInCart("2", response)
        response.verifyStatus(SC_NOT_FOUND)
    }


    // addProductsToCart
    //----------------------------------------------------------------------------------
    @Test
    fun `when add products to existing cart then products are added`() {
        cartController.addProductsToCart(cart.userId, listOf(1, 2), response)

        assertEquals(
            productListFromIds(1, 2),
            savedCart.items
        )
    }

    @Test
    fun `when add products to existing cart then response status is 200 OK`() {
        cartController.addProductsToCart(cart.userId, listOf(1, 2), response)
        response.verifyStatus(SC_OK)
    }

    @Test
    fun `when add products to non existing cart then new cart is created`() {
        cartController.addProductsToCart("2", listOf(1, 2), response)

        verify(cartRepository).save(
            Cart(
                sequenceGenerator.nextId(Cart.SEQUENCE_NAME),
                "2",
                productListFromIds(1, 2)
            )
        )
    }

    @Test
    fun `when add products to non existing cart then resposne status is 201 CREATED`() {
        cartController.addProductsToCart("2", listOf(1, 2), response)
        response.verifyStatus(SC_CREATED)
    }


    // removeProductFromCart
    //----------------------------------------------------------------------------------
    @Test
    fun `when remove product from cart then product is removed`() {
        cart.items = productListFromIds(1, 2, 3)

        cartController.removeProductFromCart(cart.userId, 2, response)
        assertEquals(
            productListFromIds(1, 3),
            savedCart.items
        )
    }

    @Test
    fun `when remove existing product from existing cart then response status is 200 OK`() {
        cart.items = productListFromIds(1, 2, 3)

        cartController.removeProductFromCart(cart.userId, 2, response)
        response.verifyStatus(SC_OK)
    }

    @Test
    fun `when remove non-existing product from existing cart then response status is 200 OK`() {
        cart.items = productListFromIds(1, 2, 3)

        cartController.removeProductFromCart(cart.userId, 4, response)
        response.verifyStatus(SC_OK)
    }

    @Test
    fun `when remove product from non-existing cart then response status is 404 NOT FOUND`() {
        cartController.removeProductFromCart("2", 1, response)
        response.verifyStatus(SC_NOT_FOUND)
    }

    // clearCart
    //----------------------------------------------------------------------------------
    @Test
    fun `when clear existing cart then cart is cleared`() {
        cart.items = productListFromIds(1, 2, 3)
        cartController.clearCart(cart.userId, response)

        assertEquals(
            emptyList<BasicProductInfo>(),
            savedCart.items
        )
    }

    @Test
    fun `when clear existing cart then response status is 200 OK`() {
        cartController.clearCart(cart.userId, response)
        response.verifyStatus(SC_OK)
    }

    @Test
    fun `when clear non existing cart then response status is 404 NOT FOUND`() {
        cartController.clearCart("2", response)
        response.verifyStatus(SC_NOT_FOUND)
    }

    //checkout
    //----------------------------------------------------------------------------------
    @Test
    fun `when checkout existing cart then appropriate order is created `() {
        cart.items = productListFromIds(1, 3)

        cartController.checkout(
            cart.userId,
            CheckoutRequest("John", "Rambo", "john.rambo@vietnam.com", "Wallstreet", "120", "18", "06-300", "New York", "PayPal"),
            response
        )

        assertEquals(
            savedOrder,
            Order(
                sequenceGenerator.nextId(Order.SEQUENCE_NAME),
                cart.userId,
                "John", "Rambo", "john.rambo@vietnam.com", "Wallstreet", "120", "18", "06-300", "New York", "PayPal",
                productListFromIds(1, 3), LocalDate.now().format(Order.DATE_FORMATTER))
        )

    }

    @Test
    fun `when checkout existing cart then this cart is cleared`() {
        cart.items = productListFromIds(1, 2, 3)

        cartController.checkout(
            cart.userId,
            CheckoutRequest("John", "Rambo", "john.rambo@vietnam.com", "Wallstreet", "120", "18", "06-300", "New York", "PayPal"),
            response
        )

        assertEquals(
            emptyList<BasicProductInfo>(),
            savedCart.items
        )
    }

    @Test
    fun `when checkout existing cart then response status is 201 CREATED`() {
        cartController.checkout(
            cart.userId,
            CheckoutRequest("John", "Rambo", "john.rambo@vietnam.com", "Wallstreet", "120", "18", "06-300", "New York", "PayPal"),
            response
        )

        response.verifyStatus(SC_CREATED)
    }

    @Test
    fun `when checkout existing cart then response header contains appropriate order location`() {
        cartController.checkout(
            cart.userId,
            CheckoutRequest("John", "Rambo", "john.rambo@vietnam.com", "Wallstreet", "120", "18", "06-300", "New York", "PayPal"),
            response
        )

        verify(response).setHeader("Location", "${sequenceGenerator.nextId(Order.SEQUENCE_NAME)}")
    }

    @Test
    fun `when checkout non existing cart then response status is 404 NOT FOUND`() {
        cartController.checkout(
            "2",
            CheckoutRequest("John", "Rambo", "john.rambo@vietnam.com", "Wallstreet", "120", "18", "06-300", "New York", "PayPal"),
            response
        )

        response.verifyStatus(SC_NOT_FOUND)
    }
}