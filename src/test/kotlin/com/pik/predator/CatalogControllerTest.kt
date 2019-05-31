package com.pik.predator

import com.nhaarman.mockitokotlin2.whenever
import com.pik.predator.controller.catalog.CatalogController
import com.pik.predator.db.dto.mapToBasicInfoList
import com.pik.predator.db.repository.ProductRepository
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner
import org.junit.Before
import org.mockito.Mock
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.*

@RunWith(SpringRunner::class)
class CatalogControllerTest {

    //tested object
    private lateinit var catalogController: CatalogController

    //dependencies
    @Mock lateinit var productRepository: ProductRepository

    //other mocks
    @Mock lateinit var response: HttpServletResponse

    @Before
    fun setup() {
        whenever(productRepository.findAll()).thenReturn(products)
        whenever(productRepository.findById(1)).thenReturn(Optional.of(products[0]))
        whenever(productRepository.findById(2)).thenReturn(Optional.of(products[1]))
        catalogController = CatalogController(productRepository)
    }

    @Test
    fun `when get all products then products are returned`() {
        assertEquals(
            products.mapToBasicInfoList(),
            catalogController.getAllProducts(response)
        )
    }

    @Test
    fun `when get all products then response status is 200 OK`() {
        catalogController.getAllProducts(response)
        response.verifyStatus(SC_OK)
    }

    @Test
    fun `when get product details then details are returned`() {
        assertEquals(products[0], catalogController.getProductDetails(1, response))
    }

    @Test
    fun `when get details of existing product then response status is 200 OK`() {
        catalogController.getProductDetails(1, response)
        response.verifyStatus(SC_OK)
    }

    @Test
    fun `when get details of non existing product then response status is 404 NOT FOUND`() {
        catalogController.getProductDetails(8, response)
        response.verifyStatus(SC_NOT_FOUND)
    }

    @Test
    fun `when filter then response is 200 OK`() {
        catalogController.getProducts(emptyMap(), response)
        response.verifyStatus(SC_OK)
    }

    @Test
    fun testFilterProcessors() {
        assertEquals(
            products.filter { it.processor == "Intel Core i7 8550H" || it.processor == "Intel Core i7 8600U" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "processor1" to "Intel Core i7 8550H",
                "processor2" to "Intel Core i7 8600U"
            ), response)
        )
    }

    @Test
    fun testFilterTypes() {
        assertEquals(
            products.filter { it.type == "Ultrabook" || it.type == "Gaming" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "type1" to "Ultrabook",
                "type2" to "Gaming"
            ), response)
        )
    }

    @Test
    fun testFilterManufacturers() {
        assertEquals(
            products.filter { it.manufacturer == "Lenovo" || it.manufacturer == "Asus" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "manufacturer1" to "Lenovo",
                "manufacturer2" to "Asus"
            ), response)
        )
    }

    @Test
    fun testFilterHardDriveType() {
        assertEquals(
            products.filter { it.hardDriveType == "HDD" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "hardDriveType" to "HDD"
            ), response)
        )
    }

    @Test
    fun testFilterRamType() {
        assertEquals(
            products.filter { it.ramType == "DDR4" || it.ramType == "DDR4X" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "ramType1" to "DDR4",
                "ramType2" to "DDR4X"
            ), response)
        )
    }

    @Test
    fun testFilterDisplayType() {
        assertEquals(
            products.filter { it.displayType == "matte" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "displayType1" to "matte"
            ), response)
        )
    }

    @Test
    fun testFilterDisplayResolution() {
        assertEquals(
            products.filter { it.displayResolution == "1920x1080" || it.displayResolution == "4096x3112" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "displayResolution1" to "1920x1080",
                "displayResolution2" to "4096x3112"
            ), response)
        )
    }

    @Test
    fun testFilterScreenSzie() {
        assertEquals(
            products.filter { it.screenSize == "14" || it.screenSize == "15" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "screenSize1" to "14",
                "screenSize2" to "15"
            ), response)
        )
    }

    @Test
    fun testFilterColor() {
        assertEquals(
            //czarny jak piekło, czerwony jak ogień
            products.filter { it.color == "black" || it.color == "black-red" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "color1" to "black",
                "color2" to "black-red"
            ), response)
        )
    }

    @Test
    fun testFilterWarranty() {
        assertEquals(
            products.filter { it.warranty == "2 years" || it.warranty == "5 years" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "warranty1" to "2 years",
                "warranty2" to "5 years"
            ), response)
        )
    }

    @Test
    fun testFilterGraphicCardManufacturer() {
        assertEquals(
            products.filter { it.graphicCard.contains("NVIDIA") || it.graphicCard.contains("AMD") }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "graphicCardManufacturer1" to "NVIDIA",
                "graphicCardManufacturer2" to "AMD"
            ), response)
        )
    }

    @Test
    fun testFilterPortTypes() {
        assertEquals(
            products.filter { it.portTypes.containsAll(listOf("USB 2.0", "USB 3.0")) }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "portType1" to "USB 2.0",
                "portType2" to "USB 3.0"
            ), response)
        )
    }

    @Test
    fun testFilterPrice() {
        assertEquals(
            products.filter { it.price in 2000.toBigDecimal()..8000.toBigDecimal() }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "priceFrom" to "2000",
                "priceTo" to "8000"
            ), response)
        )
    }

    @Test
    fun testFilterWeight() {
        assertEquals(
            products.filter { it.weight in 1.8f..2.2f }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "weightFrom" to "1.8",
                "weightTo" to "2.2"
            ), response)
        )
    }

    @Test
    fun testFilterHardDriveSize() {
        assertEquals(
            products.filter { it.hardDriveSize in 1000..1500 }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "hardDriveSizeFrom" to "1000",
                "hardDriveSizeTo" to "1500"
            ), response)
        )
    }

    @Test
    fun testFilterGraphicVRAM() {
        assertEquals(
            products.filter { it.graphicVRAM in 2..4 }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "graphicVRAMFrom" to "2",
                "graphicVRAMTo" to "4"
            ), response)
        )
    }

    @Test
    fun testFilterRamSize() {
        assertEquals(
            products.filter { it.ramSize in 16..32 }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "ramSizeFrom" to "16",
                "ramSizeTo" to "32"
            ), response)
        )
    }


}