package com.github.xfl03.aadebt.repository.aa

import com.github.xfl03.aadebt.entity.aa.DebtInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DebtInfoRepository : JpaRepository<DebtInfo, Int> {
    fun findAllByGroupId(groupId: Int): List<DebtInfo>
}