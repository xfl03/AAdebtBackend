package com.github.xfl03.aadebt.dao

import com.github.xfl03.aadebt.entity.DebtGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DebtGroupRepository : JpaRepository<DebtGroup, Int>