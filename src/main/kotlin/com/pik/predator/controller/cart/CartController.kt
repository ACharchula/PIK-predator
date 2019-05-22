package com.pik.predator.controller.cart

import com.pik.predator.db.data.BasicProductInfo
import com.pik.predator.db.data.Cart
import com.pik.predator.db.data.Order
import com.pik.predator.db.data.mapToBasicInfo
import com.pik.predator.db.repository.CartRepository
import com.pik.predator.db.repository.OrderRepository
import com.pik.predator.db.repository.ProductRepository
import com.pik.predator.helpers.*
import com.pik.predator.service.SequenceGenerator
import org.springframework.web.bind.annotation.*
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
    fun getProductsInCart(@PathVariable userId: Int, response: HttpServletResponse): List<BasicProductInfo>? {
        return cartRepository.findByUserId(userId)?.items
            .alsoNullable(
                onNotNull = { response.ok() },
                onNull = { response.notFound() }
            )
    }

    /**
     * Adds list of products to the cart
     * @param userId id of the user who owns this cart
     * @param productIds list of ids of the products to be added (given in request body)
     */
    @PostMapping("/users/{userId}/cart")
    @CrossOrigin
    fun addProductsToCart(@PathVariable userId: Int, @RequestBody productIds: List<Int>, response: HttpServletResponse) {
        val cart: Cart =
            cartRepository.findByUserId(userId)
                .letNullable(
                    onNotNull = { cart ->
                        response.ok()
                        cart
                    },
                    onNull = {
                        response.created()
                        Cart(
                            sequenceGenerator.nextId(Cart.SEQUENCE_NAME),
                            userId,
                            emptyMutableList()
                        )
                    }
                )

        for (productId in productIds) {
            productRepository.getById(productId)
                ?.let { product -> cart.items.add(product.mapToBasicInfo()) }
        }
        cartRepository.save(cart)
    }

    /**
     * Removes given product from cart
     * @param userId id of the user who owns this cart
     * @param productId id of the product to be removed
     */
    @DeleteMapping("users/{userId}/cart/{productId}")
    @CrossOrigin
    fun removeProductFromCart(@PathVariable userId: Int, @PathVariable productId: Int, response: HttpServletResponse) {
        cartRepository.findByUserId(userId)
            .letNullable(
                onNotNull = { cart ->
                    val removed = cart.items.removeIf { it.productId == productId }

                    if (removed) {
                        cartRepository.save(cart)
                        response.ok()
                    }
                    else response.notFound()
                },
                onNull = { response.notFound() }
            )
    }

    /**
     * Clears all contents from the cart
     * @param userId id of the user who owns this cart
     */
    @DeleteMapping("/users/{userId}/cart")
    @CrossOrigin
    fun clearCart(@PathVariable userId: Int, response: HttpServletResponse) {
        cartRepository.findByUserId(userId)
            .letNullable(
                onNotNull = { cart ->
                    cart.items.clear()
                    cartRepository.save(cart)
                    response.ok()
                },
                onNull = { response.notFound() }
            )
    }

    /**
     * Creates order from this cart's contents and returns its URI location in response
     * @param userId id of the user who owns this cart
     */
    @PostMapping("/users/{userId}/cart/checkout")
    @CrossOrigin
    fun checkout(@PathVariable("userId") userId: Int, @RequestBody checkoutRequest: CheckoutRequest, response: HttpServletResponse) {
        cartRepository.findByUserId(userId)
            .letNullable(
                onNotNull = { cart ->
                    val order = Order(
                        sequenceGenerator.nextId(Order.SEQUENCE_NAME),
                        checkoutRequest.firstName,
                        checkoutRequest.lastName,
                        checkoutRequest.email,
                        checkoutRequest.street,
                        checkoutRequest.houseNumber,
                        checkoutRequest.localNumber,
                        checkoutRequest.postalCode,
                        checkoutRequest.city,
                        checkoutRequest.paymentMethod,
                        cart.items
                    )
                    orderRepository.save(order)
                    response.created()
                    response.setHeader("Location", "/orders/${order.orderId}")
                },
                onNull = { response.notFound() }
            )
    }
}

data class CheckoutRequest(
    var firstName: String,
    var lastName: String,
    var email: String,
    var street: String,
    var houseNumber: String,
    var localNumber: String,
    var postalCode: String,
    var city: String,
    var paymentMethod: String
)