package com.pik.predator

import com.nhaarman.mockitokotlin2.whenever
import com.pik.predator.controller.catalog.CatalogController
import com.pik.predator.db.data.Product
import com.pik.predator.db.data.mapToBasicInfo
import com.pik.predator.db.repository.ProductRepository
import junit.framework.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner
import org.junit.Before
import org.mockito.Mock
import java.util.*

@RunWith(SpringRunner::class)
class CatalogControllerTest {

    @Mock
    lateinit var productRepository: ProductRepository
    private lateinit var catalogController: CatalogController

    private val products = listOf(
        Product(
            productId = 0,
            description = "fajny laptop",
            price = 5000.toBigDecimal(),
            imageUrl = "google.com",
            processor = "Intel Core i7 8550H",
            processorClock = "2.4-4.5 Ghz",
            type = "Laptop 2w1",
            manufacturer = "Dell",
            model = "UX12345",
            operatingSystem = "Windows 10",
            portTypes = listOf("USB 3.0"),
            hardDriveType = "HDD",
            hardDriveSize = 1000,
            graphicCard = "Inte HD Graphics 5500",
            graphicVRAM = 2,
            itemDimensions = "20x20x20",
            ramType = "DDR3",
            ramSize = 8,
            weight = 1.5f,
            displayType = "matte",
            displayResolution = "1920x1080",
            screenSize = "14",
            battery = "1233123 mah",
            camera = "0.3 Mpx",
            color = "black",
            warranty = "2 years",
            quantityInMagazine = 1
        ),
        Product(
            productId = 1,
            description = "bardzo fajny laptop",
            price = 10000.toBigDecimal(),
            imageUrl = "google.com",
            processor = "Intel Core i7 8600U",
            processorClock = "2.4-4.5 Ghz",
            type = "Ultrabook",
            manufacturer = "Lenovo",
            model = "Thinkpad T480s",
            operatingSystem = "Windows 10",
            portTypes = listOf("USB 3.0"),
            hardDriveType = "SSD",
            hardDriveSize = 1500,
            graphicCard = "AMD Radeon R9 380",
            graphicVRAM = 4,
            itemDimensions = "20x20x20",
            ramType = "DDR4",
            ramSize = 16,
            weight = 2.0f,
            displayType = "matte",
            displayResolution = "1920x1080",
            screenSize = "14",
            battery = "1233123 mah",
            camera = "0.3 Mpx",
            color = "black",
            warranty = "2 years",
            quantityInMagazine = 12
        ),
        Product(
            productId = 2,
            description = "super fajny laptop",
            price = 20000.toBigDecimal(),
            imageUrl = "google.com",
            processor = "Intel Core i9 9900T",
            processorClock = "3.6-5.0 Ghz",
            type = "Gaming",
            manufacturer = "Asus",
            model = "Predator",
            operatingSystem = "Windows 10",
            portTypes = listOf("USB 3.0"),
            hardDriveType = "SSD",
            hardDriveSize = 2000,
            graphicCard = "Nvidia 2080 Ti",
            graphicVRAM = 8,
            itemDimensions = "20x20x20",
            ramType = "DDR4X",
            ramSize = 32,
            weight = 2.0f,
            displayType = "matte",
            displayResolution = "1920x1080",
            screenSize = "14",
            battery = "1233123 mah",
            camera = "0.3 Mpx",
            color = "black",
            warranty = "2 years",
            quantityInMagazine = 12
        )
    )

    @Before
    fun setup() {
        whenever(productRepository.findAll()).thenReturn(products)
        whenever(productRepository.findById(0)).thenReturn(Optional.of(products[0]))
        whenever(productRepository.findById(1)).thenReturn(Optional.of(products[1]))
        catalogController = CatalogController(productRepository)
    }

    @Test
    fun testGetAllProducts() {
        assertEquals(
            products.mapToBasicInfo(),
            catalogController.getAllProducts()
        )
    }

    @Test
    fun testGetProductDetails() {
        assertEquals(products[0], catalogController.getProductDetails(0))
    }

    @Test
    fun testFilterPrice() {
        assertEquals(
            products.filter { it.price in 2000.toBigDecimal()..8000.toBigDecimal() }.mapToBasicInfo(),
            catalogController.filterProducts(mapOf(
                "priceFrom" to "2000",
                "priceTo" to "8000"
            ))
        )
    }

    @Test
    fun testFilterWeight() {
        assertEquals(
            products.filter { it.weight in 1.8f..2.2f }.mapToBasicInfo(),
            catalogController.filterProducts(mapOf(
                "weightFrom" to "1.8",
                "weightTo" to "2.2"
            ))
        )
    }

    @Test
    fun testFilterProcessors() {
        assertEquals(
            products.filter { it.processor == "Intel Core i7 8550H" || it.processor == "Intel Core i7 8600U" }.mapToBasicInfo(),
            catalogController.filterProducts(mapOf(
                "processor1" to "Intel Core i7 8550H",
                "processor2" to "Intel Core i7 8600U"
            ))
        )
    }

    @Test
    fun testFilterTypes() {
        assertEquals(
            products.filter { it.type == "Ultrabook" || it.type == "Gaming" }.mapToBasicInfo(),
            catalogController.filterProducts(mapOf(
                "type1" to "Ultrabook",
                "type2" to "Gaming"
            ))
        )
    }

    @Test
    fun testFilterManufacturers() {
        assertEquals(
            products.filter { it.manufacturer == "Lenovo" || it.type == "Asus" }.mapToBasicInfo(),
            catalogController.filterProducts(mapOf(
                "manufacturer1" to "Lenovo",
                "manufacturer2" to "Lenovo2"
            ))
        )
    }

    @Test
    fun testFilterHardDriveType() {
        assertEquals(
            products.filter { it.hardDriveType == "HDD" }.mapToBasicInfo(),
            catalogController.filterProducts(mapOf(
                "hardDriveType" to "HDD"
            ))
        )
    }

    @Test
    fun testFilterHardDriveSize() {
        assertEquals(
            products.filter { it.hardDriveSize in 1000..1500 }.mapToBasicInfo(),
            catalogController.filterProducts(mapOf(
                "hardDriveSizeFrom" to "1000",
                "hardDriveSizeTo" to "1500"
            ))
        )
    }

    @Test
    fun testFilterGraphicCardManufacturer() {
        assertEquals(
            products.filter { it.graphicCard.contains("NVIDIA") || it.graphicCard.contains("AMD") }.mapToBasicInfo(),
            catalogController.filterProducts(mapOf(
                "graphicCardManufacturer1" to "NVIDIA",
                "graphicCardManufacturer2" to "AMD"
            ))
        )
    }

    @Test
    fun testFilterGraphicVRAM() {
        assertEquals(
            products.filter { it.graphicVRAM in 2..4 }.mapToBasicInfo(),
            catalogController.filterProducts(mapOf(
                "graphicVRAMFrom" to "2",
                "graphicVRAMTo" to "4"
            ))
        )
    }

    @Test
    fun testFilterRamType() {
        assertEquals(
            products.filter { it.ramType == "DDR4" || it.ramType == "DDR4X" }.mapToBasicInfo(),
            catalogController.filterProducts(mapOf(
                "ramType1" to "DDR4",
                "ramType2" to "DDR4X"
            ))
        )
    }

//    @Test
//    fun testFilterRamSize() {
//        assertEquals(
//            products.findLast { it.ramSize in  }
//        )
//    }
}