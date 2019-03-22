package com.pik.predator

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @RequestMapping("/api/hello")
    fun hello(): String {
        return "tak to ja, PREDATOR"
    }
}