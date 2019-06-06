package com.pik.predator.controller

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.pik.predator.db.entities.Product
import com.pik.predator.db.dto.mapToBasicInfo
import org.junit.Assert
import org.junit.Assert.*
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_OK

internal val products = listOf(
    Product(
        productId = 1,
        description = "fajny laptop",
        price = 5000.toBigDecimal(),
        imageUrl = "google.com",
        processor = "Intel Core i7 8550H",
        processorClock = "2.4-4.5 Ghz",
        type = "Laptop 2w1",
        manufacturer = "Dell",
        model = "UX12345",
        operatingSystem = "Windows 10",
        portTypes = listOf("USB 2.0"),
        hardDriveType = "HDD",
        hardDriveSize = 1000,
        graphicCard = "Inte HD Graphics 5500",
        graphicVRAM = 2,
        itemDimensions = "20x20x20",
        ramType = "DDR3",
        ramSize = 8,
        weight = 1.5f,
        displayType = "matte",
        displayResolution = "1360x768",
        screenSize = "15",
        battery = "1233123 mah",
        camera = "0.3 Mpx",
        color = "white",
        warranty = "1 year",
        quantityInMagazine = 1
    ),
    Product(
        productId = 2,
        description = "bardzo fajny laptop",
        price = 10000.toBigDecimal(),
        imageUrl = "google.com",
        processor = "Intel Core i7 8600U",
        processorClock = "2.4-4.5 Ghz",
        type = "Ultrabook",
        manufacturer = "Lenovo",
        model = "Thinkpad T480s",
        operatingSystem = "Windows 10",
        portTypes = listOf("USB 2.0", "USB 3.0"),
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
        productId = 3,
        description = "super fajny laptop",
        price = 20000.toBigDecimal(),
        imageUrl = "google.com",
        processor = "Intel Core i9 9900T",
        processorClock = "3.6-5.0 Ghz",
        type = "Gaming",
        manufacturer = "Asus",
        model = "Predator",
        operatingSystem = "Windows 10",
        portTypes = listOf("USB 2.0", "USB 3.0", "USB C"),
        hardDriveType = "SSD",
        hardDriveSize = 2000,
        graphicCard = "Nvidia 2080 Ti",
        graphicVRAM = 8,
        itemDimensions = "20x20x20",
        ramType = "DDR4X",
        ramSize = 32,
        weight = 2.0f,
        displayType = "super-matte",
        displayResolution = "4096x3112",
        screenSize = "17",
        battery = "1233123 mah",
        camera = "0.3 Mpx",
        color = "black-red",
        warranty = "5 years",
        quantityInMagazine = 12
    )
)

internal fun productListFromIds(vararg ids: Int) =
    ids.map { id -> products[id-1].mapToBasicInfo() }.toMutableList()

internal fun HttpServletResponse.verifyStatus(status: Int) {
    assertEquals(status, this.status)
}

abstract class HttpServletResponseSpy : HttpServletResponse {
    private var status: Int = SC_OK

    override fun setStatus(status: Int) {
        this.status = status
    }
    override fun getStatus(): Int = status
}