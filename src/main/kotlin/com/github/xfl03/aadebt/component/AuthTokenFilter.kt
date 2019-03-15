package com.github.xfl03.aadebt.component

import com.github.xfl03.aadebt.entity.auth.AuthUserDetail
import com.github.xfl03.aadebt.repository.auth.AuthTokenRepository
import com.github.xfl03.aadebt.repository.auth.AuthUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken


@Component
class AuthTokenFilter : OncePerRequestFilter() {
    @Autowired
    private lateinit var tokenRepo: AuthTokenRepository
    @Autowired
    private lateinit var userRepo: AuthUserRepository
    private val authHeader = "Authorization"
    private val tokenPrefix = "Bearer"
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        //Check Header
        val authHeader = request.getHeader(authHeader)
        if (authHeader == null || !authHeader.startsWith(tokenPrefix)) {
            filterChain.doFilter(request, response)
            return
        }
        //Check Token
        val token = authHeader.substring(tokenPrefix.length).trim()
        val authToken = tokenRepo.findByToken(token)
        if (!authToken.isPresent) {
            filterChain.doFilter(request, response)
            return
        }

        //Check User
        val authUser = userRepo.findById(authToken.get().userId)
        if (!authUser.isPresent) {
            filterChain.doFilter(request, response)
            return
        }

        //Create User
        val user = authUser.get()
        val userDetails = AuthUserDetail(user.id, user.name, user.account, user.password, token)

        //Auth User
        val authentication = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities)
        authentication.details = WebAuthenticationDetailsSource().buildDetails(
                request)
        SecurityContextHolder.getContext().authentication = authentication

        filterChain.doFilter(request, response)
    }
}