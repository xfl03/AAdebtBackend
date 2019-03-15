package com.github.xfl03.aadebt.entity.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class AuthUserDetail(var id: Int, var name: String, var account: String, var pw: String, var token: String = "") : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return Arrays.asList(SimpleGrantedAuthority("USER"))
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return account
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return pw;
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}