package com.pik.predator.controller

import com.pik.predator.db.data.BasicProductInfo
import com.pik.predator.db.data.Product
import com.pik.predator.db.data.Order
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.pik.predator.db.repository.ProductRepository
import com.pik.predator.db.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired

@RestController
class ExampleController {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @RequestMapping("/api/hello")
    fun hello(): String {
        return "tak to ja, PREDATOR"
    }

    @RequestMapping("/api/notebook")
    fun exampleNotebook(): List<Product> {

        productRepository.save(Product(1, "fajny laptop", 5000.toBigDecimal(), "google.com",
                "Intel Core i7 8550H", "2.4-4.5 Ghz", "Ultrabook",
                "Asus", "UX12345", "Windows 10", listOf("USB 3.0"),
                "SSD", 1000, "Nvidia 1050", 2,
                "20x20x20", "DDR4", 8,
                1.5f, "matte", "1920x1080", "14",
                "1233123 mah", "0.3 Mpx", "black", "2 years", 12))

        productRepository.save(Product(2, "bardzo fajny laptop", 10000.toBigDecimal(), "google.com",
                "Intel Core i7 8550H", "2.4-4.5 Ghz", "Ultrabook",
                "Asus", "UX12345", "Windows 10", listOf("USB 3.0"),
                "SSD", 1500, "Nvidia 1050", 4,
                "20x20x20", "DDR4", 16,
                1.5f, "matte", "1920x1080", "14",
                "1233123 mah", "0.3 Mpx", "black", "2 years", 12))

        return productRepository.findAll()
    }

    @RequestMapping("/api/order")
    fun exampleOrder(): List<Order> {

        orderRepository.deleteAll()
        orderRepository.save(Order(1, "a@gmail.com", "Dominik", "W",
                "Marszalkowska", "12", "3A", "02-212", "Warsaw", "Paypal",
                arrayListOf(
                    BasicProductInfo(8, "Asus", "UX123", 3000.toBigDecimal()),
                    BasicProductInfo(9, "Dell", "XPS 13", 1000.toBigDecimal())
                )
            )
        )


        return orderRepository.findAll()
    }
}