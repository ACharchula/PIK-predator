package com.pik.predator

import com.pik.predator.databaseClasses.BasicNotebookInfo
import com.pik.predator.databaseClasses.Notebook
import com.pik.predator.databaseClasses.Order
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.pik.predator.databaseClasses.repositories.NotebooksRepository
import com.pik.predator.databaseClasses.repositories.OrdersRepository
import org.springframework.beans.factory.annotation.Autowired

@RestController
class HelloController {

    @Autowired
    private lateinit var notebooksRepository: NotebooksRepository

    @Autowired
    private lateinit var ordersRepository: OrdersRepository

    @RequestMapping("/api/hello")
    fun hello(): String {
        return "tak to ja, PREDATOR"
    }

    @RequestMapping("/api/notebook")
    fun exampleNotebook(): List<Notebook> {

        notebooksRepository.save(Notebook(1,"fajny laptop", 5000.00, "google.com",
                "Intel Core i7 8550H", "2.4-4.5 Ghz", "Ultrabook",
                "Asus", "UX12345", "Windows 10", "USB 3.0",
                "SSD", "1 TB", "Nvidia 1050", "2 GB",
                "WiFi", "20x20x20", "DDR4", "16 GB",
                1.5f, "matte", "1920x1080", "14",
                "1233123 mah", "0.3 Mpx", "black", "2 years", 12))

        return notebooksRepository.findAll()
    }

    @RequestMapping("/api/order")
    fun exampleOrder(): List<Order> {

        ordersRepository.deleteAll()
        ordersRepository.save(Order(1, "a@gmail.com", "Dominik", "W",
                "Marszalkowska", "12", "3A", "02-212", "Warsaw",
                "Paypal", arrayListOf(BasicNotebookInfo("Asus", "UX123", 3000.0),
                BasicNotebookInfo("Dell","XPS 13", 1000.0))))


        return ordersRepository.findAll()
    }
}