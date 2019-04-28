package com.pik.predator.controller

import com.pik.predator.db.data.BasicProductInfo
import com.pik.predator.db.data.Product
import com.pik.predator.db.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

class Filters(private val map: Map<String, String>) {

    val priceFrom = map["priceFrom"]?.toBigDecimal() ?: BigDecimal.ZERO
    val priceTo = map["priceTo"]?.toBigDecimal() ?: BigDecimal(Int.MAX_VALUE)
    val processors = acceptedValuesOf(Product::processor.name)
    val processorClocks = acceptedValuesOf(Product::processorClock.name)
    val types = acceptedValuesOf(Product::type.name)
    val manufacturers = acceptedValuesOf(Product::manufacturer.name)
    val operatingSystems = acceptedValuesOf(Product::operatingSystem.name)
    val portTypes = acceptedValuesOf(Product::portTypes.name)
    val hardDrive = map[Product::hardDrive.name]
    val memorySizes = acceptedValuesOf(Product::memorySize.name)
    val graphicCardsManufacturers = acceptedValuesOf("graphicCardManufacturer")
    val graphicVRAMs = acceptedValuesOf(Product::graphicVRAM.name)
    val ramTypes = acceptedValuesOf(Product::ramType.name)
    val ramSizes = acceptedValuesOf(Product::ramSize.name)
    val weightFrom = map["weightFrom"]?.toFloat() ?: 0f
    val weightTo = map["weightTo"]?.toFloat() ?: Float.MAX_VALUE
    val displayTypes = acceptedValuesOf(Product::displayType.name)
    val displayResolutions = acceptedValuesOf(Product::displayResolution.name)
    val screenSizes = acceptedValuesOf(Product::screenSize.name)
    val colors = acceptedValuesOf(Product::color.name)
    val warranties = acceptedValuesOf(Product::warranty.name)

    private fun acceptedValuesOf(filterName: String): List<String> {
        return map
                .filterKeys { it.contains(filterName) }
                .mapKeys { it.key.split("[", "]")[1] }
                .values.toList()
    }

    operator fun get(filter: String) = map[filter]
}

@RestController
class CatalogController {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @GetMapping("/catalog/all")
    fun getAllProducts(): List<BasicProductInfo> {
        return productRepository.findAll()
            .map { BasicProductInfo(it.productId, it.manufacturer, it.model, it.price) }
    }

    @GetMapping("/catalog/{productId}")
    fun getProductDetails(@PathVariable productId: Int): ResponseEntity<Product> {
        return productRepository.findById(productId)
            .let { product ->
                if (product.isPresent) ResponseEntity.ok(product.get())
                else ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
            }
    }

    @GetMapping("/catalog")
    fun filterProducts(@RequestParam params: Map<String, String>): List<BasicProductInfo> {
        val filters = Filters(params)

        return productRepository.findAll().asSequence()
                .filter { item -> item.price in filters.priceFrom..filters.priceTo }
                .filter { item -> item.processor in filters.processors }
                .filter { item -> item.processorClock in filters.processorClocks }
                .filter { item -> item.type in filters.types }
                .filter { item -> item.manufacturer in filters.manufacturers }
                .filter { item -> item.operatingSystem in filters.operatingSystems }
                .filter { item -> item.portTypes.containsAll(filters.portTypes) }
                .filter { item -> item.hardDrive == filters.hardDrive || filters.hardDrive == null }
                .filter { item -> item.memorySize in filters.memorySizes }
                .filter { item ->
                    filters.graphicCardsManufacturers.any { manufacturer ->
                        item.graphicCard.contains(manufacturer)
                    }
                }
                .filter { item -> item.graphicVRAM in filters.graphicVRAMs }
                .filter { item -> item.ramType in filters.ramTypes }
                .filter { item -> item.ramSize in filters.ramSizes }
                .filter { item -> item.weight in filters.weightFrom..filters.weightTo }
                .filter { item -> item.displayType in filters.displayTypes }
                .filter { item -> item.displayResolution in filters.displayResolutions }
                .filter { item -> item.screenSize in filters.screenSizes }
                .filter { item -> item.color in filters.colors }
                .filter { item -> item.warranty in filters.warranties }
                .map { BasicProductInfo(it.productId, it.manufacturer, it.model, it.price) }
                .toList()
    }
}