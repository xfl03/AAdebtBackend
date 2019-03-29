package com.github.xfl03.aadebt.repository.debt

import com.github.xfl03.aadebt.entity.debt.DebtGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DebtGroupRepository : JpaRepository<DebtGroup, Int> {
    fun findAllByOwnerIdOrderByIdDesc(ownerId: Int): List<DebtGroup>
    fun findByIdAndOwnerId(id: Int, ownerId: Int): Optional<DebtGroup>
}