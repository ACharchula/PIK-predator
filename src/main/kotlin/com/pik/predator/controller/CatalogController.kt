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

@RestController
class CatalogController {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @GetMapping("/catalog/all")
    fun getAllProducts(): List<BasicProductInfo> {
        return productRepository.findAll()
            .map { BasicProductInfo(it.productId, it.producer, it.model, it.price) }
    }

    @GetMapping("catalog/{productId}")
    fun getProductDetails(@PathVariable productId: Int): ResponseEntity<Product> {
        return productRepository.findById(productId)
            .let { product ->
                if (product.isPresent) ResponseEntity.ok(product.get())
                else ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
            }
    }

    @GetMapping("/catalog")
    fun filterProducts(
           @RequestParam(required = false) price: BigDecimal?,
           @RequestParam(required = false) processor: String?,
           @RequestParam(required = false) processorSpeed: String?,
           @RequestParam(required = false) type: String?,
           @RequestParam(required = false) producer: String?,
           @RequestParam(required = false) model: String?,
           @RequestParam(required = false) operatingSystem: String?,
           @RequestParam(required = false) portTypes: String?,
           @RequestParam(required = false) hardDrive: String?,
           @RequestParam(required = false) memorySize: String?,
           @RequestParam(required = false) graphicCoprocessor: String?,
           @RequestParam(required = false) graphicVRAM: String?,
           @RequestParam(required = false) wirelessConnection: String?,
           @RequestParam(required = false) itemDimensions: String?,
           @RequestParam(required = false) ramType: String?,
           @RequestParam(required = false) ramSize: String?,
           @RequestParam(required = false) weight: Float?,
           @RequestParam(required = false) displayType: String?,
           @RequestParam(required = false) displayResolution: String?,
           @RequestParam(required = false) screenSize: String?,
           @RequestParam(required = false) battery: String?,
           @RequestParam(required = false) camera: String?,
           @RequestParam(required = false) colour: String?,
           @RequestParam(required = false) guarantee: String?)
    : List<BasicProductInfo> {
        return productRepository.findAll().asSequence()
                .filter { it.price == price || price == null }
                .filter { it.processor == processor || processor == null }
                .filter { it.processorSpeed == processorSpeed || processorSpeed == null }
                .filter { it.type == type || type == null }
                .filter { it.producer == producer || producer == null }
                .filter { it.model == model || model == null }
                .filter { it.operatingSystem == operatingSystem || operatingSystem == null }
                .filter { it.portTypes == portTypes || portTypes == null }
                .filter { it.hardDrive == hardDrive || hardDrive == null }
                .filter { it.memorySize == memorySize || memorySize == null }
                .filter { it.graphicCoprocessor == graphicCoprocessor || graphicCoprocessor == null }
                .filter { it.graphicVRAM == graphicVRAM || graphicVRAM == null }
                .filter { it.wirelessConnection == wirelessConnection || wirelessConnection == null }
                .filter { it.itemDimensions == itemDimensions || itemDimensions == null }
                .filter { it.ramType == ramType || ramType == null }
                .filter { it.ramSize == ramSize || ramSize == null }
                .filter { it.weight == weight || weight == null }
                .filter { it.displayType == displayType || displayType == null }
                .filter { it.displayResolution == displayResolution || displayResolution == null }
                .filter { it.screenSize == screenSize || screenSize == null }
                .filter { it.battery == battery || battery == null }
                .filter { it.camera == camera || camera == null }
                .filter { it.colour == colour || colour == null }
                .filter { it.guarantee == guarantee || guarantee == null }
                .map { BasicProductInfo(it.productId, it.producer, it.model, it.price) }
                .toList()
    }
}