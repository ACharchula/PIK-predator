package com.pik.predator.controller.catalog

import com.pik.predator.db.data.BasicProductInfo
import com.pik.predator.db.data.Product
import com.pik.predator.db.data.mapToBasicInfo
import com.pik.predator.db.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class CatalogController(
    @Autowired private val productRepository: ProductRepository
) {
    /**
     * Returns all products from the catalog
     * @return list of basic infos about the products
     */
    @CrossOrigin
    @GetMapping("/catalog/all")
    fun getAllProducts(): List<BasicProductInfo> {
        return productRepository.findAll().mapToBasicInfo()
    }

    /**
     * Returns detail info about specific product
     * @param productId the id of the product
     * @return all information about the product
     */
    @CrossOrigin
    @GetMapping("/catalog/{productId}")
    fun getProductDetails(@PathVariable productId: Int): Product? {
        return productRepository.findById(productId)
            .let { product ->
                if (product.isPresent) product.get()
                else null
            }
    }

    /**
     * Filters products by given filters
     * Example query: /catalog?priceFrom=1000&priceTo=2000&manufacturer1=Asus&manufacturer2=Lenovo
     * which coresponds to following filters:
     * - price between 1000 and 2000
     * - mnanufacturer Asus or Lenovo
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
                    .mapToBasicInfo()
            }
    }
}