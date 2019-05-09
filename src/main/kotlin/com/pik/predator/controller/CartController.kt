package com.pik.predator.controller

import com.pik.predator.db.data.BasicProductInfo
import com.pik.predator.db.data.mapToBasicInfo
import com.pik.predator.db.repository.CartRepository
import com.pik.predator.db.repository.ProductRepository
import org.springframework.web.bind.annotation.*

@RestController
class CartController(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) {

    /**
     * Returns all products in the cart
     * @param userId id of the user who owns this cart
     * @return list of basic infos about products in cart
     */
    @GetMapping("/cart/{userId}")
    @CrossOrigin
    fun getProductsInCart(@PathVariable("userId") userId: Int): List<BasicProductInfo> {
        return cartRepository.findByUserId(userId).items.mapToBasicInfo()
    }

    /**
     * Adds product to the cart
     * @param userId id of the user who owns this cart
     * @param productId id of the product to be added (given in request body)
     */
    @PostMapping("/cart/{userId}")
    @CrossOrigin
    fun addProductToCart(@PathVariable("userId") userId: Int, @RequestBody productId: Int) {
        val cart = cartRepository.findByUserId(userId)
        productRepository.findById(productId).ifPresent { cart.items += it }
    }

    /**
     * Creates order from this cart's contents
     * @param userId id of the user who owns this cart
     */
    @PostMapping("/cart/{userId}/checkout")
    @CrossOrigin
    fun checkout(@PathVariable("userId") userId: Int) {

    }
}