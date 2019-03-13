package com.github.xfl03.aadebt.entity

import javax.persistence.*

/**
 * 账目参与者
 */
@Entity
data class DebtPart(

        /**
         * 参与者id
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,

        /**
         * 账目组id
         */
        @Column(nullable = false)
        var groupId: Int = 0,

        /**
         * 参与者名称
         */
        @Column(nullable = false)
        var name: String = ""
)