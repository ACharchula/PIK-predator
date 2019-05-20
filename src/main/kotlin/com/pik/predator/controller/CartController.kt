package com.pik.predator.controller

import com.pik.predator.db.data.BasicProductInfo
import com.pik.predator.db.data.Cart
import com.pik.predator.db.data.mapToBasicInfo
import com.pik.predator.db.repository.CartRepository
import com.pik.predator.db.repository.ProductRepository
import com.pik.predator.helpers.*
import com.pik.predator.service.SequenceGenerator
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse


@RestController
class CartController(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val sequenceGenerator: SequenceGenerator
) {

    /**
     * Returns all products in the cart
     * @param userId id of the user who owns this cart
     * @return list of basic infos about products in cart
     */
    //TODO change to /carts/{cartId}, cart should be created (with location returned) before calling that method
    @GetMapping("/carts/{userId}")
    @CrossOrigin
    fun getProductsInCart(@PathVariable userId: Int, response: HttpServletResponse): List<BasicProductInfo>? {
        return cartRepository.findByUserId(userId)?.items?.mapToBasicInfo()
            .also { items ->
                if (items != null) response.ok()
                else response.notFound()
            }
    }

    /**
     * Adds product to the cart
     * @param userId id of the user who owns this cart
     * @param productId id of the product to be added (given in request body)
     */
    //TODO change to /carts/{cartId}, cart should be created (with location returned) before calling that method
    @PostMapping("/carts/{userId}")
    @CrossOrigin
    fun addProductToCart(@PathVariable userId: Int, @RequestParam productId: Int, response: HttpServletResponse) {
        val cart: Cart = cartRepository.findByUserId(userId)
            .let { cart ->
                if (cart != null) cart.also { response.ok() }
                else createCart(userId).also { response.created() }
            }

        productRepository.getById(productId)
            ?.let { product ->
                cart.items += product
                cartRepository.save(cart)
            }
    }

    /**
     * Creates order from this cart's contents
     * @param userId id of the user who owns this cart
     */
    //TODO change URL to something like /checkout/{cartId}
    @PostMapping("/carts/{userId}/checkout")
    @CrossOrigin
    fun checkout(@PathVariable("userId") userId: Int) {

    }

    private fun createCart(userId: Int): Cart =
        Cart(sequenceGenerator.nextId(Cart.SEQUENCE_NAME), userId, emptyMutableList())
}