package com.pik.predator

import com.pik.predator.controller.HelloController
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.junit.Assert.*

@RunWith(SpringRunner::class)
@SpringBootTest
class PredatorApplicationTests {

    @Test
    fun testHelloController() {
        val helloController = HelloController()
        assertEquals("tak to ja, PREDATOR", helloController.hello())
    }
}