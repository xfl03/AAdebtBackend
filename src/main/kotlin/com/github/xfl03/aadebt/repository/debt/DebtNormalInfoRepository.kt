package com.github.xfl03.aadebt.repository.debt

import com.github.xfl03.aadebt.entity.debt.DebtNormalInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DebtNormalInfoRepository : JpaRepository<DebtNormalInfo,Int>{
    fun findAllByGroupId(groupId: Int): List<DebtNormalInfo>
    fun findAllByGroupIdOrderByIdDesc(groupId: Int): List<DebtNormalInfo>
    fun findAllByGroupIdOrderByDateDesc(groupId: Int): List<DebtNormalInfo>
    fun findByIdAndGroupId(id: Int, droupId: Int): Optional<DebtNormalInfo>
}
