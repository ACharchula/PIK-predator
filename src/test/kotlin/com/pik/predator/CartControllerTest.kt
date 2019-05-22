package com.pik.predator

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pik.predator.controller.CartController
import com.pik.predator.controller.CheckoutRequest
import com.pik.predator.db.data.*
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

@RunWith(SpringRunner::class)
class CartControllerTest {

    private val products = listOf(
        Product(
            productId = 1,
            description = "fajny laptop",
            price = 5000.toBigDecimal(),
            imageUrl = "google.com",
            processor = "Intel Core i7 8550H",
            processorClock = "2.4-4.5 Ghz",
            type = "Laptop 2w1",
            manufacturer = "Dell",
            model = "UX12345",
            operatingSystem = "Windows 10",
            portTypes = listOf("USB 2.0"),
            hardDriveType = "HDD",
            hardDriveSize = 1000,
            graphicCard = "Inte HD Graphics 5500",
            graphicVRAM = 2,
            itemDimensions = "20x20x20",
            ramType = "DDR3",
            ramSize = 8,
            weight = 1.5f,
            displayType = "matte",
            displayResolution = "1360x768",
            screenSize = "15",
            battery = "1233123 mah",
            camera = "0.3 Mpx",
            color = "white",
            warranty = "1 year",
            quantityInMagazine = 1
        ),
        Product(
            productId = 2,
            description = "bardzo fajny laptop",
            price = 10000.toBigDecimal(),
            imageUrl = "google.com",
            processor = "Intel Core i7 8600U",
            processorClock = "2.4-4.5 Ghz",
            type = "Ultrabook",
            manufacturer = "Lenovo",
            model = "Thinkpad T480s",
            operatingSystem = "Windows 10",
            portTypes = listOf("USB 2.0", "USB 3.0"),
            hardDriveType = "SSD",
            hardDriveSize = 1500,
            graphicCard = "AMD Radeon R9 380",
            graphicVRAM = 4,
            itemDimensions = "20x20x20",
            ramType = "DDR4",
            ramSize = 16,
            weight = 2.0f,
            displayType = "matte",
            displayResolution = "1920x1080",
            screenSize = "14",
            battery = "1233123 mah",
            camera = "0.3 Mpx",
            color = "black",
            warranty = "2 years",
            quantityInMagazine = 12
        ),
        Product(
            productId = 3,
            description = "super fajny laptop",
            price = 20000.toBigDecimal(),
            imageUrl = "google.com",
            processor = "Intel Core i9 9900T",
            processorClock = "3.6-5.0 Ghz",
            type = "Gaming",
            manufacturer = "Asus",
            model = "Predator",
            operatingSystem = "Windows 10",
            portTypes = listOf("USB 2.0", "USB 3.0", "USB C"),
            hardDriveType = "SSD",
            hardDriveSize = 2000,
            graphicCard = "Nvidia 2080 Ti",
            graphicVRAM = 8,
            itemDimensions = "20x20x20",
            ramType = "DDR4X",
            ramSize = 32,
            weight = 2.0f,
            displayType = "super-matte",
            displayResolution = "4096x3112",
            screenSize = "17",
            battery = "1233123 mah",
            camera = "0.3 Mpx",
            color = "black-red",
            warranty = "5 years",
            quantityInMagazine = 12
        )
    )

    private val cart = Cart(1, 1, emptyMutableList())

    //tested object
    private lateinit var cartController: CartController

    //dependencies
    @Spy lateinit var cartRepository: SpyCartRepository
    @Mock lateinit var productRepository: ProductRepository
    @Mock lateinit var orderRepository: OrderRepository
    @Mock lateinit var sequenceGenerator: SequenceGenerator

    //other mocks
    @Mock lateinit var response: HttpServletResponse

    @Before
    fun setup() {
        whenever(cartRepository.findByUserId(1)).thenReturn(cart)
        whenever(cartRepository.findByUserId(2)).thenReturn(null)

        whenever(productRepository.findById(1)).thenReturn(Optional.of(products[0]))
        whenever(productRepository.findById(2)).thenReturn(Optional.of(products[1]))
        whenever(productRepository.findById(3)).thenReturn(Optional.of(products[2]))

        whenever(sequenceGenerator.nextId(Cart.SEQUENCE_NAME)).thenReturn(2)
        whenever(sequenceGenerator.nextId(Order.SEQUENCE_NAME)).thenReturn(1)

        cartController = CartController(cartRepository, productRepository, orderRepository, sequenceGenerator)
    }

    //helpers
    private lateinit var savedCart: Cart

    abstract inner class SpyCartRepository : CartRepository {
        override fun <S : Cart> save(cart: S): S {
            savedCart = cart
            return cart
        }
    }

    private fun productListFromIds(vararg ids: Int) = ids.map { id -> products[id-1].mapToBasicInfo() }.toMutableList()

    private fun verifyResponseStatus(status: Int) {
        verify(response).status = status
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
        verifyResponseStatus(SC_OK)
    }

    @Test
    fun `when get products of cart with invalid userId then null is returned`() {
        assertEquals(
            null,
            cartController.getProductsInCart(2, response)
        )
    }

    @Test
    fun `when get products of cart with invalid userId then response status is 404 NOT FOUND`() {
        cartController.getProductsInCart(2, response)
        verifyResponseStatus(SC_NOT_FOUND)
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
        verifyResponseStatus(SC_OK)
    }

    @Test
    fun `when add products to non existing cart then new cart is created`() {
        cartController.addProductsToCart(2, listOf(1, 2), response)

        verify(cartRepository).save(
            Cart(
                sequenceGenerator.nextId(Cart.SEQUENCE_NAME),
                2,
                productListFromIds(1, 2)
            )
        )
    }

    @Test
    fun `when add products to non existing cart then resposne status is 201 CREATED`() {
        cartController.addProductsToCart(2, listOf(1, 2), response)
        verifyResponseStatus(SC_CREATED)
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
        verifyResponseStatus(SC_OK)
    }

    @Test
    fun `when remove non-existing product from existing cart then response status is 404 NOT FOUND`() {
        cart.items = productListFromIds(1, 2, 3)

        cartController.removeProductFromCart(cart.userId, 4, response)
        verifyResponseStatus(SC_NOT_FOUND)
    }

    @Test
    fun `when remove product from non-existing cart then response status is 404 NOT FOUND`() {
        cartController.removeProductFromCart(2, 1, response)
        verifyResponseStatus(SC_NOT_FOUND)
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
        verifyResponseStatus(SC_OK)
    }

    @Test
    fun `when clear non existing cart then response status is 404 NOT FOUND`() {
        cartController.clearCart(2, response)
        verifyResponseStatus(SC_NOT_FOUND)
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

        verify(orderRepository).save(
            Order(
                sequenceGenerator.nextId(Order.SEQUENCE_NAME),
                "John", "Rambo", "john.rambo@vietnam.com", "Wallstreet", "120", "18", "06-300", "New York", "PayPal",
                productListFromIds(1, 3))
        )
    }

    @Test
    fun `when checkout existing cart then response status is 201 CREATED`() {
        cartController.checkout(
            cart.userId,
            CheckoutRequest("John", "Rambo", "john.rambo@vietnam.com", "Wallstreet", "120", "18", "06-300", "New York", "PayPal"),
            response
        )

        verifyResponseStatus(SC_CREATED)
    }

    @Test
    fun `when checkout existing cart then response header contains appropriate order location`() {
        cartController.checkout(
            cart.userId,
            CheckoutRequest("John", "Rambo", "john.rambo@vietnam.com", "Wallstreet", "120", "18", "06-300", "New York", "PayPal"),
            response
        )

        verify(response).setHeader("Location", "/orders/${sequenceGenerator.nextId(Order.SEQUENCE_NAME)}")
    }

    @Test
    fun `when checkout non existing cart then response status is 404 NOT FOUND`() {
        cartController.checkout(
            2,
            CheckoutRequest("John", "Rambo", "john.rambo@vietnam.com", "Wallstreet", "120", "18", "06-300", "New York", "PayPal"),
            response
        )

        verifyResponseStatus(SC_NOT_FOUND)
    }
}