package com.miron4dev.sandbox.jwt.web

import com.miron4dev.sandbox.jwt.config.SecurityConfig.Companion.JWT_TOKEN_COOKIE
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest

@Controller
class HomeController {

    @GetMapping("/")
    fun index(model: MutableMap<String, Any>, request: HttpServletRequest): String {
        model["token"] = request.cookies?.find { it.name == JWT_TOKEN_COOKIE }?.value ?: ""
        return "index"
    }

    @GetMapping("/login")
    fun login(): String = "login"
}