package com.github.xfl03.aadebt.service

import com.github.xfl03.aadebt.entity.auth.AuthUserDetail
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MockUserDetailService : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        return AuthUserDetail(0, "test", "test", "test")
    }

    @Bean
    fun mockUserDetailsServiceBean(): UserDetailsService {
        return this
    }
}