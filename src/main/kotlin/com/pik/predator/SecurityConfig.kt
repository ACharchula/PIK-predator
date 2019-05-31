package com.pik.predator

import com.auth0.spring.security.api.JwtWebSecurityConfigurer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@EnableWebSecurity
@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Value(value = "\${auth0.apiAudience}")
    private lateinit var apiAudience: String

    @Value(value = "\${auth0.issuer}")
    private lateinit var issuer: String

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf("http://localhost:3000")
            allowedMethods = listOf("GET", "POST")
            allowCredentials = true
            addAllowedHeader("Authorization")
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }

    override fun configure(http: HttpSecurity) {
        http.cors()
        JwtWebSecurityConfigurer
            .forRS256(apiAudience, issuer)
            .configure(http)
            .authorizeRequests()
            .antMatchers("/users/**").authenticated()
            .antMatchers("/orders/**").authenticated()
    }
}