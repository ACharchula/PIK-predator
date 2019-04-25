package com.pik.predator.databaseClasses

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Notebooks")
class Notebook(productId: Int, description: String, price: Double, image: String, processor: String,
               processor_speed: String, type: String, producer: String, model: String, operating_system: String,
               port_types: String, hard_drive: String, memory_size: String, graphic_coprocessor: String,
               graphic_vram: String, wireless_connection: String, item_dimensions: String, ram_type: String,
               ram_size: String, weight: Float, display_type: String, display_resolution: String,
               screen_size: String, battery: String, camera: String, colour: String, guarantee: String,
               quantity_in_magazine: Int) {

    @Id
    var productId: Int = productId
    var description: String = description
    var price: Double = price
    var image: String = image
    var processor: String = processor
    var processor_speed: String = processor_speed
    var type: String = type
    var producer: String = producer
    var model: String = model
    var operating_system: String = operating_system
    var port_types: String = port_types
    var hard_drive: String = hard_drive
    var memory_size: String = memory_size
    var graphic_coprocessor: String = graphic_coprocessor
    var graphic_vram: String = graphic_vram
    var wireless_connection: String = wireless_connection
    var item_dimensions: String = item_dimensions
    var ram_type: String = ram_type
    var ram_size: String = ram_size
    var weight: Float = weight
    var display_type: String = display_type
    var display_resolution: String = display_resolution
    var screen_size: String = screen_size
    var battery: String = battery
    var camera: String = camera
    var colour: String = colour
    var guarantee: String = guarantee
    var quantity_in_magazine: Int = quantity_in_magazine
}