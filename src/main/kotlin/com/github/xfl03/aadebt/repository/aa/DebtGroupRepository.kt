package com.github.xfl03.aadebt.repository.aa

import com.github.xfl03.aadebt.entity.aa.DebtGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DebtGroupRepository : JpaRepository<DebtGroup, Int> {
    fun findAllByOwnerId(ownerId: Int): List<DebtGroup>
    fun findByIdAndOwnerId(id: Int, ownerId: Int): Optional<DebtGroup>
}