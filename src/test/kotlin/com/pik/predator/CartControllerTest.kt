package com.pik.predator

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pik.predator.controller.CartController
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
import junit.framework.Assert.*
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
    @Mock lateinit var cartRepository: CartRepository
    @Mock lateinit var productRepository: ProductRepository
    @Mock lateinit var orderRepository: OrderRepository
    @Mock lateinit var sequenceGenerator: SequenceGenerator

    //other mocks
    @Mock lateinit var response: HttpServletResponse

    @Before
    fun setup() {
        whenever(cartRepository.findByUserId(1)).thenReturn(cart)

        whenever(productRepository.findById(1)).thenReturn(Optional.of(products[0]))
        whenever(productRepository.findById(2)).thenReturn(Optional.of(products[1]))
        whenever(productRepository.findById(3)).thenReturn(Optional.of(products[2]))

        whenever(sequenceGenerator.nextId(Cart.SEQUENCE_NAME)).thenReturn(2)
        whenever(sequenceGenerator.nextId(Order.SEQUENCE_NAME)).thenReturn(1)

        cartController = CartController(cartRepository, productRepository, orderRepository, sequenceGenerator)
    }

    private fun productListFromIds(vararg ids: Int) = ids.map { id -> products[id-1].mapToBasicInfo() }.toMutableList()

    private fun verifyResponseStatus(status: Int) {
        verify(response).status = status
    }


    // getProductsInCart
    //----------------------------------------------------------------------------------
    @Test
    fun `when getProductsInCart on valid cartId then products are returned`() {
        cart.items = productListFromIds(1, 3)
        assertEquals(
            productListFromIds(1, 3),
            cartController.getProductsInCart(cart.cartId, response)
        )
    }

    @Test
    fun `when getProductsInCart on valid cartId then response status is 200 OK`() {
        cartController.getProductsInCart(cart.cartId, response)
        verifyResponseStatus(SC_OK)
    }

    @Test
    fun `when getProductsInCart on invalid cartId then null is returned`() {
        assertEquals(
            null,
            cartController.getProductsInCart(2, response)
        )
    }

    @Test
    fun `when getProductsInCart on invalid cartId then response status is 404 NOT FOUND`() {
        cartController.getProductsInCart(2, response)
        verifyResponseStatus(SC_NOT_FOUND)
    }

    // addProductsToCart
    //----------------------------------------------------------------------------------
    @Test
    fun `when addProductsToCart on existing cart then products are added`() {
        cartController.addProductsToCart(cart.cartId, listOf(1, 2), response)
        assertEquals(
            productListFromIds(1, 2),
            cart.items
        )
    }

    @Test
    fun `when addProductsToCart on existing cart then response status is 200 OK`() {
        cartController.addProductsToCart(cart.cartId, listOf(1, 2), response)
        verifyResponseStatus(SC_OK)
    }

    @Test
    fun `when addProductsToCart on new cart then new one is created`() {
        //TODO
    }
}