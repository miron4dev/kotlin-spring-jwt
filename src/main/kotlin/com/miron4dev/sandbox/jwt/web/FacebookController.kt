package com.miron4dev.sandbox.jwt.web

import com.github.benmanes.caffeine.cache.Caffeine
import com.miron4dev.sandbox.jwt.config.FacebookConfig
import com.miron4dev.sandbox.jwt.config.SecurityConfig.Companion.JWT_TOKEN_COOKIE
import com.miron4dev.sandbox.jwt.security.FacebookService
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import java.util.concurrent.TimeUnit
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/facebook")
class FacebookController(private val facebookConfig: FacebookConfig,

                         private val facebookService: FacebookService) {

    private val validState = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build<String, Boolean>()

    @GetMapping
    fun login(response: HttpServletResponse) {
        val uuid = UUID.randomUUID().toString()
        val state = Base64.getEncoder().encodeToString(uuid.toByteArray())
        validState.put(uuid, true)

        val authorizationUri = UriComponentsBuilder.fromUriString(facebookConfig.userAuthorizationUri)
                .queryParam("client_id", facebookConfig.clientId)
                .queryParam("redirect_uri", ServletUriComponentsBuilder.fromCurrentRequestUri().path("/callback").toUriString())
                .queryParam("state", state)
                .toUriString()

        response.sendRedirect(authorizationUri)
    }

    @GetMapping("/callback")
    fun loginCallback(request: HttpServletRequest, response: HttpServletResponse) {
        val code = request.getParameter("code")
        val state = String(Base64.getDecoder().decode(request.getParameter("state")))

        if (validState.getIfPresent(state) == null) {
            throw AuthenticationServiceException("Provided state is incorrect or expired")
        }

        val redirectUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()
        val jwtToken = facebookService.generateJwtToken(code, redirectUrl)

        val cookie = Cookie(JWT_TOKEN_COOKIE, jwtToken)
        cookie.path = "/"
        response.addCookie(cookie)
        response.sendRedirect("/")
    }
}