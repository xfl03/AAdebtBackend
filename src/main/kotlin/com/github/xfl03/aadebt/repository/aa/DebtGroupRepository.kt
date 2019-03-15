package com.github.xfl03.aadebt.repository.aa

import com.github.xfl03.aadebt.entity.aa.DebtGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DebtGroupRepository : JpaRepository<DebtGroup, Int>