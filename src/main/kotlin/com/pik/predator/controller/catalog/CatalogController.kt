package com.pik.predator.controller.catalog

import com.pik.predator.db.data.BasicProductInfo
import com.pik.predator.db.data.Product
import com.pik.predator.db.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CatalogController(
    @Autowired private val productRepository: ProductRepository
) {
    @CrossOrigin
    @GetMapping("/catalog/all")
    fun getAllProducts(): List<BasicProductInfo> {
        return productRepository.findAll()
            .map { BasicProductInfo(it.productId, it.manufacturer, it.model, it.price,it.imageUrl) }
    }

    @CrossOrigin
    @GetMapping("/catalog/{productId}")
    fun getProductDetails(@PathVariable productId: Int): Product? {
        return productRepository.findById(productId)
            .let { product ->
                if (product.isPresent) product.get()
                else null
            }
    }

    @CrossOrigin
    @GetMapping("/catalog")
    fun filterProducts(@RequestParam params: Map<String, String>): List<BasicProductInfo> {
        return Filters(params).let { filters ->
            productRepository.findAll()
                .filter { item -> filters.accept(item) }
                .map { BasicProductInfo(it.productId, it.manufacturer, it.model, it.price,it.imageUrl) }
        }
    }
}