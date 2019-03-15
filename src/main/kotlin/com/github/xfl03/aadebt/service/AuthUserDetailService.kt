package com.github.xfl03.aadebt.service

import com.github.xfl03.aadebt.entity.auth.AuthUserDetail
import com.github.xfl03.aadebt.repository.auth.AuthUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthUserDetailService : UserDetailsService {
    @Autowired
    private lateinit var userRepo: AuthUserRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) throw UsernameNotFoundException("Account is null")
        val user = userRepo.findByAccount(username)
        if (!user.isPresent) throw UsernameNotFoundException("Account not found")
        val u = user.get()
        return AuthUserDetail(u.id, u.name, u.account, u.password)
    }
}