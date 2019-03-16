package com.github.xfl03.aadebt.entity.aa

import javax.persistence.*

/**
 * 账目组
 */
@Entity
data class DebtGroup(

        /**
         * 账目组id
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,

        /**
         * 账目组名称
         */
        @Column(nullable = false)
        var name: String = "",

        /**
         * 是否已销账
         */
        @Column(nullable = false)
        var locked: Boolean = false,

        /**
         * 账目组拥有者id
         */
        @Column(nullable = false)
        var ownerId: Int
)