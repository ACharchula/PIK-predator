package com.pik.predator.db.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "Products")
data class Product(
        @Id var productId: Int,
        var description: String,
        var price: BigDecimal,
        var image: String,
        var processor: String,
        var processorSpeed: String,
        var type: String,
        var producer: String,
        var model: String,
        var operatingSystem: String,
        var portTypes: String,
        var hardDrive: String,
        var memorySize: String,
        var graphicCoprocessor: String,
        var graphicVRAM: String,
        var wirelessConnection: String,
        var itemDimensions: String,
        var ramType: String,
        var ramSize: String,
        var weight: Float,
        var displayType: String,
        var displayResolution: String,
        var screenSize: String,
        var battery: String,
        var camera: String,
        var colour: String,
        var guarantee: String,
        var quantityInMagazine: Int
)