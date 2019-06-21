package com.miron4dev.sandbox.jwt

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
open class Application {

    @Bean
    open fun restTemplate(): RestTemplate = RestTemplate()
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}