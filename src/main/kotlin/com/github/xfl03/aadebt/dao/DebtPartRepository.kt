package com.github.xfl03.aadebt.dao

import com.github.xfl03.aadebt.entity.DebtPart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DebtPartRepository : JpaRepository<DebtPart, Int> {
    fun findAllByGroupId(groupId: Int): List<DebtPart>
    fun findByIdAndGroupId(id: Int, groupId: Int): Optional<DebtPart>
}