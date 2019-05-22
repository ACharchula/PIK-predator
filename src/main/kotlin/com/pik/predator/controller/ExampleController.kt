package com.pik.predator.controller

import com.pik.predator.db.data.BasicProductInfo
import com.pik.predator.db.data.Product
import com.pik.predator.db.data.Order
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.pik.predator.db.repository.ProductRepository
import com.pik.predator.db.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin

@RestController
class ExampleController {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @CrossOrigin
    @RequestMapping("/api/hello")
    fun hello(): String {
        return "tak to ja, PREDATOR"
    }

    @RequestMapping("/api/notebook")
    fun exampleNotebook(): List<Product> {
        productRepository.deleteAll()

        productRepository.save(Product(1, "ZenBook 3 overcomes the challenge of combining " +
                "high-performance components into the smallest chassis yet for the ZenBook series. The result is an" +
                " unbelievably powerful laptop, with the latest Intel Core i7 processor, 16GB of 2133MHz LPDDR3 RAM," +
                " and 512GB SATA SSD and the latest USB-C port that supports display. So there’s no need to worry " +
                "about what you can or can’t do with ZenBook 3-it’s practically unstoppable. ",
                5000.toBigDecimal(), "https://www.asus.com/media/global/items/RFg3mM0IrjWtUMLG/BMKAkwkgXRLjrqxt_setting_fff_1_90_end_500.png",
                "Intel® Core™ i7 7500U ", "2.7-3.5 Ghz", "Ultrabook",
                "Asus", "Vivobook UX390UA", "Windows 10", listOf("USB 3.1 C"),
                "SSD", 512, " Intel HD Graphics", 32,
                "296 x 191.2 x 11.9", "DDR3", 16,
                0.91f, "matte", "1920x1080", "12.5",
                "1 Lithium Polymer batteries required.", "0.3 Mpx", "black", "2 years", 12))

        productRepository.save(Product(2, "The XPS 13 cleverly fits a stunning 13.3-Inch display in an 11-inch size laptop," +
                " making the it the smallest 13-inch laptop on the planet. The XPS 13 has a unique virtually borderless InfinityEdge display, " +
                "with only a 5.2mm bezel on the top and both sides. That's the thinnest frame on a notebook today. ", 10000.toBigDecimal(),
                "https://m.media-amazon.com/images/S/aplus-media/vc/b401d03a-66dd-4906-a62d-3465271b68ed.jpg",
                "Intel Core i7 ", "3.5 Ghz", "Ultrabook",
                "Dell", "XPS9360-4841SLV", "Windows 10", listOf("USB 3.0","USB 3.1"),
                "SSD", 256, " Intel HD Graphics 620", 4,
                "300x200x15", "DDR3", 8,
                1.3f, "matte", " 3200x1800", "13.3",
                "1 Lithium Polymer batteries required.", "0.3 Mpx", " Machined Aluminum Display Back & Base In Silver", "2 years", 15))

        productRepository.save(Product(3, "Evolving perfection is tough, but not impossible, and the new zenbook S13 provides conclusive proof." +
                " Built to combine timeless elegance with state-of-the-art performance, Its breathtakingly beautiful 13.9-Inch NanoEdge " +
                "display has the world's slimmest bezels and highest 97% screen-to-body ratio. Powered by the latest " +
                "Intel Core i7 processors, zenbook S13 is also the world's slimmest laptop with discrete NVIDIA MX150 " +
                "graphics. All this power is contained in an incredibly compact — and totally gorgeous — aluminum unibody" +
                " exclusively finished in sophisticated the new utopia Blue color. All of this weighs just 2.4lbs. " ,
                10000.toBigDecimal(), "https://images-na.ssl-images-amazon.com/images/I/71p6Dto2keL._SL1500_.jpg",
                " Intel i7-8565U ", "4.6 Ghz", "Ultrabook",
                "Asus", "ZenBook S13 UX392FN-XS71 ", "Windows 10", listOf("USB 3.0"),
                "SSD", 512, "NVIDIA GeForce MX150 ", 2,
                "300x200x15", "DDR3",  8 ,
                1.3f, "matte", " 1920x1080", "13.9",
                "1 Lithium Polymer batteries required.", "0.3 Mpx", "Silver Blue", "2 years", 4))

        productRepository.save(Product(4, "The ASUS Vivo Book F510UA is the perfect combination of " +
                "performance and function. The F510UA is a Windows 10 laptop powered by an 8th Generation Intel Core " +
                "i5-8250U processor, 8GB DDR4 RAM, and ASUS Nano Edge display technology. It's the ideal laptop for " +
                "daily computing and entertainment. 178 degree wide viewing angle. ", 2500.toBigDecimal(),
                "https://images-na.ssl-images-amazon.com/images/I/81JiWgGRqxL._SL1500_.jpg",
                "Intel Intel Core i5  ", "1.6-3.4Ghz", "Ultrabook",
                "Asus", "VivoBook F510UA ", "Windows 10", listOf("USB 3.1 C", "USB 3.0", "USB 2.0","HDMI"),
                "HDD", 1000, "Intel UHD Graphics 620", 8,
                "300x200x15", "DDR3",  8 ,
                1.6f, "matte", " 1920x1080", "15.6",
                "1 Lithium Polymer batteries required.", "0.3 Mpx", "black", "2 years", 4))

        productRepository.save(Product(5, "Very good laptop for you. " ,
                2200.toBigDecimal(), "https://cdn.x-kom.pl/i/setup/images/prod/big/product-large,,2018/9/pr_2018_9_10_14_54_22_795_00.jpg",
                " Intel Core i3-8130U", " 2.2-3.4 GHz,", "Notebook",
                "Lenovo", "Ideapad 330-15  ", "Windows 10", listOf("USB 3.0","USB 3.1", "HDMI"),
                "SSD", 240, "NVIDIA GeForce MX150 ", 2,
                "378x260x23", "DDR4",  8 ,
                1.9f, "matte", "1920x1080", "15.6",
                "1 Lithium Polymer batteries required.", "1.0 Mpx", "black", "2 years", 20))

        productRepository.save(Product(6, "Very heavy computer." ,
                4000.toBigDecimal(), "https://cdn.x-kom.pl/i/setup/images/prod/big/product-large,,2019/1/pr_2019_1_16_15_26_38_678_03.jpg",
                "Intel i5-8300H", "2.3-4 Ghz", "Notebook",
                "HP", "Pavilion Gaming", "Windows 10", listOf("USB 3.0","USB 3.1", "HDMI"),
                "SSD", 240, "NVIDIA GeForce GTX 1050Ti", 4,
                "365x256x25", "DDR4",  8 ,
                2.4f, "matte", " 1920X1080", "15.6",
                "1 Lithium Polymer batteries required.", "1.0 Mpx", "black", "2 years", 7))

        productRepository.save(Product(7, "Evolving perfection is tough, but not impossible, and the new zenbook S13 provides conclusive proof." +
                " Built to combine timeless elegance with state-of-the-art performance, Its breathtakingly beautiful 13.9-Inch NanoEdge " +
                "display has the world's slimmest bezels and highest 97% screen-to-body ratio. Powered by the latest " +
                "Intel Core i7 processors, zenbook S13 is also the world's slimmest laptop with discrete NVIDIA MX150 " +
                "graphics. All this power is contained in an incredibly compact — and totally gorgeous — aluminum unibody" +
                " exclusively finished in sophisticated the new utopia Blue color. All of this weighs just 2.4lbs. " ,
                11699.toBigDecimal(), "https://cdn.x-kom.pl/i/setup/images/prod/big/product-large,,2017/11/pr_2017_11_29_14_56_1_700_08.jpg",
                "Intel i7-8650U ", "1.9-4.2 Ghz", "2 in 1",
                "Microsoft", "Surface Book 2 13", "Windows 10", listOf("USB 3.0","USB 3.1"),
                "SSD", 512, "NVIDIA GeForce GTX 1050", 2,
                "312x232x23", "DDR3",  16 ,
                1.6f, "glossy", " 3000X2000", "13.5",
                "1 Lithium Polymer batteries required.", "5.0 Mpx", "Silver", "2 years", 2))

        productRepository.save(Product(8, "With great power comes great capability.\n" +
                "\n" +
                "MacBook Pro elevates the notebook to a whole new level of performance and portability. Wherever your ideas " +
                "take you, you’ll get there faster than ever with high‑performance processors and memory, advanced graphics, blazing‑fast storage, and more." ,
                10300.toBigDecimal(), "https://cdn.x-kom.pl/i/setup/images/prod/big/product-large,,2018/7/pr_2018_7_12_16_47_4_113_03.jpg",
                "Intel Core i7", "2.2-4.1 Ghz", "Ultrabook",
                "Apple", "MacBook Pro", "mac OS", listOf("USB C"),
                "SSD", 256, "AMD Radeon Pro 555X", 4,
                "349x240x15.5", "DDR4",  16 ,
                1.83f, "glossy", "2880x1800", "15.4",
                "1 Lithium Polymer batteries required.", "-", "Grey", "1 year", 37))

        return productRepository.findAll()
    }

    @RequestMapping("/api/order")
    fun exampleOrder(): List<Order> {

        orderRepository.deleteAll()
        orderRepository.save(Order(1, "a@gmail.com", "Dominik", "W",
                "Marszalkowska", "12", "3A", "02-212", "Warsaw", "Paypal",
                arrayListOf(
                    BasicProductInfo(8, "Asus", "Vivobook UX390UA", 3000.toBigDecimal(),"https://m.media-amazon.com/images/S/aplus-media/vc/b401d03a-66dd-4906-a62d-3465271b68ed.jpg"),
                    BasicProductInfo(9, "Dell", "XPS 13", 1000.toBigDecimal(),"https://www.asus.com/media/global/items/RFg3mM0IrjWtUMLG/BMKAkwkgXRLjrqxt_setting_fff_1_90_end_500.png")
                )
            )
        )


        return orderRepository.findAll()
    }
}