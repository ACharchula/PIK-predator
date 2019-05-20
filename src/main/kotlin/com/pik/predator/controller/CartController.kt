package com.pik.predator.controller

import com.pik.predator.db.data.BasicProductInfo
import com.pik.predator.db.data.Cart
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
     * Adds product to the cart
     * @param userId id of the user who owns this cart
     * @param productIds list of ids of the products to be added (given in request body)
     */
    @PostMapping("/users/{userId}/cart")
    @CrossOrigin
    fun addProductToCart(@PathVariable userId: Int, @RequestBody productIds: List<Int>, response: HttpServletResponse) {
        val cart: Cart =
            cartRepository.findByUserId(userId)
                .letNullable(
                    onNotNull = { cart -> response.ok(); cart },
                    onNull = { response.created(); createCart(userId) }
                )

        for (productId in productIds) {
            productRepository.getById(productId)
                ?.let { product ->
                    cart.items += product.mapToBasicInfo()
                    cartRepository.save(cart)
                }
        }
    }

    /**
     * Deletes all contents from the cart
     * @param userId id of the user who owns this cart
     */
    @DeleteMapping("/users/{userId}/cart")
    @CrossOrigin
    fun clearCart(@PathVariable userId: Int, response: HttpServletResponse) {
        cartRepository.findByUserId(userId)
            .letNullable(
                onNotNull = { cart ->
                    cartRepository.delete(cart)
                    response.ok()
                },
                onNull = { response.notFound() }
            )
    }

    /**
     * Creates order from this cart's contents
     * @param userId id of the user who owns this cart
     */
    @PostMapping("/carts/{userId}/checkout")
    @CrossOrigin
    fun checkout(@PathVariable("userId") userId: Int, response: HttpServletResponse) {
        cartRepository.findByUserId(userId)
            .letNullable(
                onNotNull = { cart ->
                    //TODO create repository and return its location in response
                },
                onNull = { response.notFound() }
            )
    }

    private fun createCart(userId: Int): Cart =
        Cart(sequenceGenerator.nextId(Cart.SEQUENCE_NAME), userId, emptyMutableList())
}