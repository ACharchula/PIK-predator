package com.pik.predator.controller.catalog

import com.pik.predator.db.data.BasicProductInfo
import com.pik.predator.db.data.Product
import com.pik.predator.db.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CatalogController(
    @Autowired private val productRepository: ProductRepository
) {

    @GetMapping("/catalog/all")
    fun getAllProducts(): List<BasicProductInfo> {
        return productRepository.findAll()
            .map { BasicProductInfo(it.productId, it.manufacturer, it.model, it.price) }
    }

    @GetMapping("/catalog/{productId}")
    fun getProductDetails(@PathVariable productId: Int): Product? {
        return productRepository.findById(productId)
            .let { product ->
                if (product.isPresent) product.get()
                else null
            }
    }

    @GetMapping("/catalog")
    fun filterProducts(@RequestParam params: Map<String, String>): List<BasicProductInfo> {
        return Filters(params).let { filters ->
            productRepository.findAll()
                .filter { item -> filters.accept(item) }
                .map { BasicProductInfo(it.productId, it.manufacturer, it.model, it.price) }
        }
    }
}