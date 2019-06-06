package com.pik.predator.controller.cart

import com.pik.predator.db.dto.BasicProductInfo
import com.pik.predator.db.dto.CheckoutRequest
import com.pik.predator.db.entities.Cart
import com.pik.predator.db.entities.Order
import com.pik.predator.db.dto.mapToBasicInfo
import com.pik.predator.db.repository.CartRepository
import com.pik.predator.db.repository.OrderRepository
import com.pik.predator.db.repository.ProductRepository
import com.pik.predator.helpers.*
import com.pik.predator.service.SequenceGenerator
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import javax.servlet.http.HttpServletResponse

@RestController
class CartController(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val sequenceGenerator: SequenceGenerator
) {
    /**
     * Returns all products in the cart
     * @param userId id of the user who owns this cart
     * @return list of basic infos about products in cart
     */
    @GetMapping("/users/{userId}/cart")
    @CrossOrigin
    fun getProductsInCart(@PathVariable userId: String, response: HttpServletResponse): List<BasicProductInfo>? {

        return cartRepository.findByUserId(userId)?.items
            .doOnNull {
                response.notFound()
            }
    }

    /**
     * Adds list of products to the cart
     * @param userId id of the user who owns this cart
     * @param productIds list of ids of the products to be added (given in request body)
     */
    @PostMapping("/users/{userId}/cart")
    @CrossOrigin
    fun addProductsToCart(@PathVariable userId: String, @RequestBody productIds: List<Int>, response: HttpServletResponse) {

        val cart = cartRepository.findByUserId(userId)
            ?: createCartFotUser(userId).also { response.created() }

        for (productId in productIds) {
            val product = productRepository.getById(productId)

            if (product != null)
                cart.items.add(product.mapToBasicInfo())
        }
        cartRepository.save(cart)
    }

    private fun createCartFotUser(userId: String): Cart =
        Cart(sequenceGenerator.nextId(Cart.SEQUENCE_NAME), userId, emptyMutableList())

    /**
     * Removes given product from cart
     * @param userId id of the user who owns this cart
     * @param productId id of the product to be removed
     */
    @DeleteMapping("users/{userId}/cart/{productId}")
    @CrossOrigin
    fun removeProductFromCart(@PathVariable userId: String, @PathVariable productId: Int, response: HttpServletResponse) {

        val cart = cartRepository.findByUserId(userId)

        if (cart != null) {
            val removed = cart.items.removeIf { it.productId == productId }

            if (removed)
                cartRepository.save(cart)
        }
        else response.notFound()
    }

    /**
     * Clears all contents from the cart
     * @param userId id of the user who owns this cart
     */
    @DeleteMapping("/users/{userId}/cart")
    @CrossOrigin
    fun clearCart(@PathVariable userId: String, response: HttpServletResponse) {

        val cart = cartRepository.findByUserId(userId)

        if (cart != null) {
            cartRepository.save(cart.copy(items = emptyMutableList()))
        }
        else response.notFound()
    }

    /**
     * Creates order from this cart's contents and returns its URI location in response
     * @param userId id of the user who owns this cart
     */
    @PostMapping("/users/{userId}/cart/checkout")
    @CrossOrigin
    fun checkout(@PathVariable("userId") userId: String, @RequestBody checkoutRequest: CheckoutRequest, response: HttpServletResponse) {

        val cart = cartRepository.findByUserId(userId)

        if (cart != null) {
            val order = createOrderFrom(userId, checkoutRequest, cart)
            orderRepository.save(order)

            clearCart(userId, response)

            response.created()
            response.setHeader("Access-Control-Expose-Headers", "Location")
            response.setHeader("Location", "${order.orderId}")
        }
        else response.notFound()
    }

    private fun createOrderFrom(userId: String, checkoutRequest: CheckoutRequest, cart: Cart): Order =
         Order(
            sequenceGenerator.nextId(Order.SEQUENCE_NAME),
            userId,
            checkoutRequest.firstName,
            checkoutRequest.lastName,
            checkoutRequest.email,
            checkoutRequest.street,
            checkoutRequest.houseNumber,
            checkoutRequest.localNumber,
            checkoutRequest.postalCode,
            checkoutRequest.city,
            checkoutRequest.paymentMethod,
            cart.items,
            LocalDate.now().format(Order.DATE_FORMATTER)
        )
}