package com.pik.predator.controller

import com.nhaarman.mockitokotlin2.whenever
import com.pik.predator.controller.catalog.CatalogController
import com.pik.predator.db.dto.mapToBasicInfoList
import com.pik.predator.db.repository.ProductRepository
import com.pik.predator.helpers.containsIgnoreCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Spy
import org.springframework.test.context.junit4.SpringRunner
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND
import javax.servlet.http.HttpServletResponse.SC_OK

@RunWith(SpringRunner::class)
class CatalogControllerTest {

    //tested object
    private lateinit var catalogController: CatalogController

    //dependencies
    @Mock lateinit var productRepository: ProductRepository

    //other mocks
    @Spy lateinit var response: HttpServletResponseSpy

    @Before
    fun setup() {
        whenever(productRepository.findAll()).thenReturn(products)
        whenever(productRepository.findById(1)).thenReturn(Optional.of(products[0]))
        whenever(productRepository.findById(2)).thenReturn(Optional.of(products[1]))
        whenever(productRepository.findById(3)).thenReturn(Optional.of(products[2]))
        whenever(productRepository.getDistinctValuesForAttribute("displayType")).thenReturn(listOf("matte", "super-matte"))
        whenever(productRepository.getDistinctValuesForAttribute("someCrazyAttribute")).thenReturn(null)
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
    fun `test filter processors`() {
        assertEquals(
            products.filter { it.processor == "Intel Core i7 8550H" || it.processor == "Intel Core i7 8600U" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "processor1" to "Intel Core i7 8550H",
                "processor2" to "Intel Core i7 8600U"
            ), response)
        )
    }

    @Test
    fun `test filter types`() {
        assertEquals(
            products.filter { it.type == "Ultrabook" || it.type == "Gaming" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "type1" to "Ultrabook",
                "type2" to "Gaming"
            ), response)
        )
    }

    @Test
    fun `test filter manufacturers`() {
        assertEquals(
            products.filter { it.manufacturer == "Lenovo" || it.manufacturer == "Asus" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "manufacturer1" to "Lenovo",
                "manufacturer2" to "Asus"
            ), response)
        )
    }

    @Test
    fun `test filter hard drive types`() {
        assertEquals(
            products.filter { it.hardDriveType == "HDD" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "hardDriveType" to "HDD"
            ), response)
        )
    }

    @Test
    fun `test filter ram types`() {
        assertEquals(
            products.filter { it.ramType == "DDR4" || it.ramType == "DDR4X" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "ramType1" to "DDR4",
                "ramType2" to "DDR4X"
            ), response)
        )
    }

    @Test
    fun `test filter display types`() {
        assertEquals(
            products.filter { it.displayType == "matte" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "displayType1" to "matte"
            ), response)
        )
    }

    @Test
    fun `test filter display resolutions`() {
        assertEquals(
            products.filter { it.displayResolution == "1920x1080" || it.displayResolution == "4096x3112" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "displayResolution1" to "1920x1080",
                "displayResolution2" to "4096x3112"
            ), response)
        )
    }

    @Test
    fun `test filter screen sizes`() {
        assertEquals(
            products.filter { it.screenSize == "14" || it.screenSize == "15" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "screenSize1" to "14",
                "screenSize2" to "15"
            ), response)
        )
    }

    @Test
    fun `test filter colors`() {
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
    fun `test filter warranties`() {
        assertEquals(
            products.filter { it.warranty == "2 years" || it.warranty == "5 years" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "warranty1" to "2 years",
                "warranty2" to "5 years"
            ), response)
        )
    }

    @Test
    fun `test filter graphic card manufacturers`() {
        assertEquals(
            products.filter { it.graphicCard.contains("Nvidia") || it.graphicCard.contains("AMD") }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "graphicCardManufacturer1" to "NVIDIA",
                "graphicCardManufacturer2" to "AMD"
            ), response)
        )
    }

    @Test
    fun `test filter port types`() {
        assertEquals(
            products.filter { it.portTypes.containsAll(listOf("USB 2.0", "USB 3.0")) }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "portType1" to "USB 2.0",
                "portType2" to "USB 3.0"
            ), response)
        )
    }

    @Test
    fun `test filter price`() {
        assertEquals(
            products.filter { it.price in 2000.toBigDecimal()..8000.toBigDecimal() }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "priceFrom" to "2000",
                "priceTo" to "8000"
            ), response)
        )
    }

    @Test
    fun `test filter weight`() {
        assertEquals(
            products.filter { it.weight in 1.8f..2.2f }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "weightFrom" to "1.8",
                "weightTo" to "2.2"
            ), response)
        )
    }

    @Test
    fun `test filter hard drive sizes`() {
        assertEquals(
            products.filter { it.hardDriveSize in 1000..1500 }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "hardDriveSizeFrom" to "1000",
                "hardDriveSizeTo" to "1500"
            ), response)
        )
    }

    @Test
    fun `test filter graphic VRAM`() {
        assertEquals(
            products.filter { it.graphicVRAM in 2..4 }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "graphicVRAMFrom" to "2",
                "graphicVRAMTo" to "4"
            ), response)
        )
    }

    @Test
    fun `test filter RAM sizes`() {
        assertEquals(
            products.filter { it.ramSize in 16..32 }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "ramSizeFrom" to "16",
                "ramSizeTo" to "32"
            ), response)
        )
    }

    @Test
    fun `when apply ListedValuesFilter then filtering should not be case sensitive`() {
        assertEquals(
            products.filter { it.color == "black" || it.color == "black-red" }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "color1" to "Black",
                "color2" to "black-RED"
            ), response)
        )
    }

    @Test
    fun `when apply ListToListFilter then filtering should not be case sensitive`() {
        assertEquals(
            products.filter { it.portTypes.containsAll(listOf("USB 2.0", "USB 3.0")) }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "portType1" to "usb 2.0",
                "portType2" to "Usb 3.0"
            ), response)
        )
    }

    @Test
    fun `when apply IsContainedByAtLeastOneAcceptedValueFilter then filtering should not be case sensitive`() {
        assertEquals(
            products.filter { it.graphicCard.contains("Nvidia") || it.graphicCard.contains("AMD") }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "graphicCardManufacturer1" to "NVIDIA",
                "graphicCardManufacturer2" to "amd"
            ), response)
        )
    }

    @Test
    fun `test search by model and manufacturer`() {
        assertEquals(
            products.filter { it.model.containsIgnoreCase("as") || it.manufacturer.containsIgnoreCase("as") }.mapToBasicInfoList(),
            catalogController.getProducts(mapOf(
                "query" to "as"
            ), response)
        )
    }

    @Test
    fun `test search without filtering`() {
        assertEquals(
            productListFromIds(1, 2),
            catalogController.getProducts(mapOf(
                "query" to "l"
            ), response)
        )
    }

    @Test
    fun `test filter without searching`() {
        assertEquals(
            productListFromIds(2, 3),
            catalogController.getProducts(mapOf(
                "ramSizeFrom" to "16",
                "ramSizeTo" to "32"
            ), response)
        )
    }

    @Test
    fun `test search with filtering`() {
        assertEquals(
            productListFromIds(2),
            catalogController.getProducts(mapOf(
                "ramSizeFrom" to "16",
                "ramSizeTo" to "32",
                "query" to "l"
            ), response)
        )
    }

    @Test
    fun `when get distinct values for attribute then proper values are returned`() {
        assertEquals(
            listOf("matte", "super-matte"),
            catalogController.getProductDistinctValuesForAttribute("displayType", response)
        )
    }

    @Test
    fun `when get distinct values for not existing attribute then null is returned`() {
        assertEquals(
            null,
            catalogController.getProductDistinctValuesForAttribute("someCrazyAttribute", response)
        )
    }

    @Test
    fun `when get distinct values then response status is 200 OK`() {
        catalogController.getProductDistinctValuesForAttribute("displayType", response)
        response.verifyStatus(SC_OK)
    }

    @Test
    fun `when get distinct values for not existing attribute then response status is 404 NOT FOUND`() {
        catalogController.getProductDistinctValuesForAttribute("someCrazyAttribute", response)
        response.verifyStatus(SC_NOT_FOUND)
    }

}