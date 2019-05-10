package com.github.xfl03.aadebt.service

import com.github.xfl03.aadebt.entity.auth.AuthUserDetail
import com.github.xfl03.aadebt.entity.debt.DebtGroup
import com.github.xfl03.aadebt.entity.debt.DebtNormalInfo
import com.github.xfl03.aadebt.json.CommonResponse
import com.github.xfl03.aadebt.json.debt.*
import com.github.xfl03.aadebt.repository.debt.DebtGroupRepository
import com.github.xfl03.aadebt.repository.debt.DebtNormalInfoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.sql.Date

@Service
class DebtService {
    @Autowired
    private lateinit var groupRepo: DebtGroupRepository
    @Autowired
    private lateinit var infoRepo: DebtNormalInfoRepository

    fun getDebts(req: DebtDebtRequest): DebtDebtResponse {
        val debts = ArrayList<DebtDetailInfo>()
        infoRepo.findAllByGroupIdOrderByIdDesc(req.groupId).forEach {
            debts.add(DebtDetailInfo(it.id, it.name, it.amount, it.type, it.date))
        }
        return DebtDebtResponse(debts)
    }

    fun addGroup(req: DebtNewRequest): CommonResponse {
        val obj = SecurityContextHolder.getContext().authentication.principal as? AuthUserDetail
                ?: return CommonResponse("Internal Error", -500)

        val group = DebtGroup(-1, req.name, false, obj.id, 1)
        groupRepo.save(group)

        return CommonResponse("OK", 200)
    }

    fun addDebt(req: DebtAddRequest): CommonResponse {
        val info = DebtNormalInfo(-1, req.groupId, req.name, req.amount, req.type, req.date)
        infoRepo.save(info)

        return CommonResponse("OK", 200)
    }

    fun calculate(req: DebtCalRequest): DebtCalResponse {
        var total = 0
        val typeMap = HashMap<Int, Int>()
        val dateMap = HashMap<Date, Int>()

        infoRepo.findAllByGroupId(req.groupId).forEach {
            typeMap[it.type] = it.amount + (typeMap[it.type] ?: 0)
            dateMap[it.date] = it.amount + (dateMap[it.date] ?: 0)
            total += it.amount
        }

        val types = typeMap.map { DebtTypeInfo(it.key, it.value) }.sortedBy { it.type }
        val dates = dateMap.map { DebtDateInfo(it.key, it.value) }.sortedBy { it.date }

        return DebtCalResponse(total, total / dates.size, types, dates)
    }

    fun checkId(type: AADebtService.Id, id: Int, more: Int = -1): CommonResponse {
        when (type) {
            AADebtService.Id.GROUP -> {
                if (more < 0 && !groupRepo.findById(id).isPresent) {
                    return CommonResponse("Group not exists", -404)
                }
                if (more >= 0 && !groupRepo.findByIdAndOwnerId(id, more).isPresent) {
                    return CommonResponse("Not group owner", -403)
                }
            }
            AADebtService.Id.DEBT -> {
                if (more < 0 && !infoRepo.findById(id).isPresent) {
                    return CommonResponse("Debt not exists", -404)
                }
                if (more >= 0 && !infoRepo.findByIdAndGroupId(id, more).isPresent) {
                    return CommonResponse("Not in group", -403)
                }
            }
        }
        return CommonResponse("OK", 0)
    }

    fun delete(req: DebtDelRequest): CommonResponse {
        infoRepo.deleteById(req.debtId)
        return CommonResponse("OK", 0)
    }
}
