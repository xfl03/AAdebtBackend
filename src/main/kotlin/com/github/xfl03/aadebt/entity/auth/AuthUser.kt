package com.github.xfl03.aadebt.entity.auth

import javax.persistence.*

/**
 * User Entity
 */
@Entity
data class AuthUser(
        /**
         * User id
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id:Int,
        /**
         * User display name
         */
        @Column(nullable = false)
        var name:String,
        /**
         * Account id (e.g. email address)
         */
        @Column(nullable = false)
        var account:String,
        /**
         * Encrypted password
         */
        @Column(nullable = false)
        var password:String
)