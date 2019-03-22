package com.pik.predator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PredatorApplication

fun main(args: Array<String>) {
    runApplication<PredatorApplication>(*args)
}
