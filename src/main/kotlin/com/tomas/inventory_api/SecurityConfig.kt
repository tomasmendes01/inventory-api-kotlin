package com.tomas.inventory_api

// Solução temporária (recomendada enquanto aprendo)
//
// implementation("org.springframework.boot:spring-boot-starter-security")
// Quando adiciono essa dependência, o Spring protege todas as rotas por defeito.

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.anyRequest().permitAll()
            }

        return http.build()
    }
}