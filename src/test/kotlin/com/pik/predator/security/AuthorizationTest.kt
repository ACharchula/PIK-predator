package com.pik.predator.security

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorizationTest {

    @Autowired private lateinit var rest: TestRestTemplate

    private fun TestRestTemplate.tryAccessNoAuth(path: String): HttpStatus =
        getForEntity<String>(path).statusCode

    private fun TestRestTemplate.tryAccessWithToken(path: String, token: String): HttpStatus {
        val headers = HttpHeaders().apply { add(HttpHeaders.AUTHORIZATION, token) }
        return exchange(path, HttpMethod.GET, HttpEntity<String>(headers), String::class.java).statusCode
    }

    @Test
    fun `when no auth then access to catalog is allowed`() {
        assertNotEquals(
            HttpStatus.UNAUTHORIZED,
            rest.tryAccessNoAuth("/catalog")
        )
    }

    @Test
    fun `when no auth then access to users is permitted`() {
        assertEquals(
            HttpStatus.UNAUTHORIZED,
            rest.tryAccessNoAuth("/users")
        )
    }

    @Test
    fun `when no auth then access to orders is permitted`() {
        assertEquals(
            HttpStatus.UNAUTHORIZED,
            rest.tryAccessNoAuth("/orders")
        )
    }

    @Test
    fun `when valid authorization token then access to users is allowed`() {
        assertNotEquals(
            HttpStatus.UNAUTHORIZED,
            rest.tryAccessWithToken("/users/1/cart", TEST_AUTH_TOKEN)
        )
    }

    @Test
    fun `when invalid authorization token then access to users is permitted`() {
        assertEquals(
            HttpStatus.UNAUTHORIZED,
            rest.tryAccessWithToken("/users/1/cart", TEST_AUTH_TOKEN + "abc")
        )
    }

    @Test
    fun `when valid authorization token then access to orders is allowed`() {
        assertNotEquals(
            HttpStatus.UNAUTHORIZED,
            rest.tryAccessWithToken("/orders/1", TEST_AUTH_TOKEN)
        )
    }

    @Test
    fun `when invalid authorization token then access to orders is permitted`() {
        assertEquals(
            HttpStatus.UNAUTHORIZED,
            rest.tryAccessWithToken("/orders/1", TEST_AUTH_TOKEN + "abc")
        )
    }
}