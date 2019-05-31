package com.pik.predator.db.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "Products")
data class Product(
    @Id var productId: Int,
    var description: String,
    var price: BigDecimal,
    var imageUrl: String,
    var processor: String,
    var processorClock: String,
    var type: String,
    var manufacturer: String,
    var model: String,
    var operatingSystem: String,
    var portTypes: List<String>,
    var hardDriveType: String,
    var hardDriveSize: Int,
    var graphicCard: String,
    var graphicVRAM: Int,
    var itemDimensions: String,
    var ramType: String,
    var ramSize: Int,
    var weight: Float,
    var displayType: String,
    var displayResolution: String,
    var screenSize: String,
    var battery: String,
    var camera: String,
    var color: String,
    var warranty: String,
    var quantityInMagazine: Int
)