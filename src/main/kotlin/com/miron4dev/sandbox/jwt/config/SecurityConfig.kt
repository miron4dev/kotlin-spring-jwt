package com.miron4dev.sandbox.jwt.config

import com.miron4dev.sandbox.jwt.security.JwtTokenAuthenticationProcessingFilter
import com.miron4dev.sandbox.jwt.security.JwtTokenFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.http.HttpServletRequest

@Configuration
@EnableWebSecurity
open class SecurityConfig : WebSecurityConfigurerAdapter() {

    companion object {
        private val ANONYMOUS_URIS = arrayOf(
                "/login",
                "/facebook",
                "/facebook/callback**",
                "/error**")

        const val JWT_TOKEN_COOKIE = "JWT_COOKIE"
    }

    @Autowired
    private lateinit var jwtTokenFactory: JwtTokenFactory

    override fun configure(http: HttpSecurity) {
        http
                .cors()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(*ANONYMOUS_URIS).permitAll()
                .anyRequest().authenticated()
                .and().formLogin().successForwardUrl("/").failureForwardUrl("/login").loginPage("/login")
                .and().addFilterBefore(jwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    private fun jwtTokenAuthenticationProcessingFilter(): JwtTokenAuthenticationProcessingFilter {
        val requestMatcher = object : RequestMatcher {
            val matchers: OrRequestMatcher = OrRequestMatcher(ANONYMOUS_URIS.map { AntPathRequestMatcher(it) })
            val processingMatcher: RequestMatcher = AntPathRequestMatcher("/**")

            override fun matches(request: HttpServletRequest): Boolean {
                return !matchers.matches(request) && processingMatcher.matches(request)
            }
        }
        return JwtTokenAuthenticationProcessingFilter(requestMatcher, jwtTokenFactory)
    }
}
