package com.miron4dev.sandbox.jwt.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
open class FacebookConfig(

        @Value("\${facebook.clientId}")
        val clientId: String,

        @Value("\${facebook.clientSecret}")
        val clientSecret: String,

        @Value("\${facebook.userAuthorizationUri}")
        val userAuthorizationUri: String,

        @Value("\${facebook.accessTokenUri}")
        val accessTokenUri: String,

        @Value("\${facebook.userInfoUri}")
        val userInfoUri: String
)