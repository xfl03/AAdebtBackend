package com.github.xfl03.aadebt.repository.debt

import com.github.xfl03.aadebt.entity.debt.DebtNormalInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DebtNormalInfoRepository : JpaRepository<DebtNormalInfo,Int>{
    fun findAllByGroupId(groupId: Int): List<DebtNormalInfo>
    fun findAllByGroupIdOrderByIdDesc(groupId: Int): List<DebtNormalInfo>
}
