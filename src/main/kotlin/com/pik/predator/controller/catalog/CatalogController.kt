package com.pik.predator.controller.catalog

import com.pik.predator.db.data.BasicProductInfo
import com.pik.predator.db.data.Product
import com.pik.predator.db.data.mapToBasicInfoList
import com.pik.predator.db.repository.ProductRepository
import com.pik.predator.helpers.getById
import org.springframework.web.bind.annotation.*

@RestController
class CatalogController(
    private val productRepository: ProductRepository
) {
    /**
     * Returns all products from the catalog
     * @return list of basic infos about the products
     */
    @CrossOrigin
    @GetMapping("/catalog/all")
    fun getAllProducts(): List<BasicProductInfo> {
        return productRepository.findAll().mapToBasicInfoList()
    }

    /**
     * Returns detail info about specific product
     * @param productId the id of the product
     * @return all information about the product
     */
    @CrossOrigin
    @GetMapping("/catalog/{productId}")
    fun getProductDetails(@PathVariable productId: Int): Product? {
        return productRepository.getById(productId)
    }

    /**
     * Filters products by given filters
     * Example query: /catalog?priceFrom=1000&priceTo=2000&manufacturer1=Asus&manufacturer2=Lenovo
     * which corresponds to following filters:
     * - price between 1000 and 2000
     * - manufacturer Asus or Lenovo
     * @param filterParams the map of filters
     * @return the filtered list of product basic infos
     */
    @CrossOrigin
    @GetMapping("/catalog")
    fun filterProducts(@RequestParam filterParams: Map<String, String>): List<BasicProductInfo> {
        return Filters(filterParams)
            .let { filters ->
                productRepository.findAll()
                    .filter { item -> filters.accept(item) }
                    .mapToBasicInfoList()
            }
    }
}