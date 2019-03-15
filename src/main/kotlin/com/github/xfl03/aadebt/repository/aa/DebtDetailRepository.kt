package com.github.xfl03.aadebt.repository.aa

import com.github.xfl03.aadebt.entity.aa.DebtDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DebtDetailRepository : JpaRepository<DebtDetail, Int> {
    fun findAllByGroupId(groupId: Int): List<DebtDetail>
    fun findAllByDebtId(debtId: Int): List<DebtDetail>
}