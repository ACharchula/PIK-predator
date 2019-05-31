package com.pik.predator.controller.catalog

import com.pik.predator.db.dto.BasicProductInfo
import com.pik.predator.db.entities.Product
import com.pik.predator.db.dto.mapToBasicInfoList
import com.pik.predator.db.repository.ProductRepository
import com.pik.predator.helpers.applyNullable
import com.pik.predator.helpers.getById
import com.pik.predator.helpers.notFound
import com.pik.predator.helpers.ok
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
class CatalogController(
    private val productRepository: ProductRepository
) {
    /**
     * Returns all products from the catalog
     * @return list of basic infos about the products
     */
    @Deprecated("use GET /catalog without parameters")
    @CrossOrigin
    @GetMapping("/catalog/all")
    fun getAllProducts(response: HttpServletResponse): List<BasicProductInfo> {
        return productRepository.findAll().mapToBasicInfoList()
            .also { response.ok() }
    }

    /**
     * Returns detail info about specific product
     * @param productId the id of the product
     * @return all information about the product
     */
    @CrossOrigin
    @GetMapping("/catalog/{productId}")
    fun getProductDetails(@PathVariable productId: Int, response: HttpServletResponse): Product? {
        return productRepository.getById(productId)
            .applyNullable(
                onNotNull = { response.ok() },
                onNull = { response.notFound() }
            )
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
    fun getProducts(@RequestParam filterParams: Map<String, String>, response: HttpServletResponse): List<BasicProductInfo> {
        return Filters(filterParams)
            .let { filters ->
                response.ok()
                productRepository.findAll()
                    .filter { item -> filters.accept(item) }
                    .mapToBasicInfoList()
            }
    }

    @CrossOrigin
    @GetMapping("/catalog/metadata/{attributeName}")
    fun getProductDistinctValuesForAttribute(@PathVariable attributeName: String, response: HttpServletResponse): List<String>? {
        return productRepository.getDistinctValuesForAttribute(attributeName)
            .applyNullable(
                onNotNull = { response.ok() },
                onNull = { response.notFound() }
            )
    }
}