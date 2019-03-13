package com.github.xfl03.aadebt.dao

import com.github.xfl03.aadebt.entity.DebtInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DebtInfoRepository : JpaRepository<DebtInfo, Int> {
    fun findAllByGroupId(groupId: Int): List<DebtInfo>
}