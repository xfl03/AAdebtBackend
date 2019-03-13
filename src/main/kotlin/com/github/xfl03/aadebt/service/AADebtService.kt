package com.github.xfl03.aadebt.service

import com.github.xfl03.aadebt.dao.*
import com.github.xfl03.aadebt.entity.DebtDetail
import com.github.xfl03.aadebt.entity.DebtGroup
import com.github.xfl03.aadebt.entity.DebtInfo
import com.github.xfl03.aadebt.entity.DebtPart
import com.github.xfl03.aadebt.json.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AADebtService {

    @Autowired
    lateinit var groupRepo: DebtGroupRepository
    @Autowired
    lateinit var infoRepo: DebtInfoRepository
    @Autowired
    lateinit var partRepo: DebtPartRepository
    @Autowired
    lateinit var detailRepo: DebtDetailRepository

    /**
     * 获得所有账目组
     */
    fun getGroups(): AAlistResponce {
        val groups = ArrayList<GroupInfo>()
        groupRepo.findAll().forEach { groups.add(GroupInfo(it.id, it.name, it.locked)) }
        return AAlistResponce(groups)
    }

    /**
     * 增加新的账目组
     */
    fun addGroup(req: AAnewRequest): AAnewResponce {
        var group = DebtGroup(-1, req.name)
        group = groupRepo.save(group)
        //groupRepo.flush()
        val parts = ArrayList<PartInfo>()
        req.parts.forEach {
            var part = DebtPart(-1, group.id, it)
            part = partRepo.save(part)
            //partRepo.flush()
            parts.add(PartInfo(part.id, it))
        }
        return AAnewResponce(group.id, req.name, parts)
    }

    /**
     * 获得账目组下的所有账目
     */
    fun getDebts(req: AADebtRequest): AADebtResponce {
        val debts = ArrayList<DebtDetailInfo>()
        infoRepo.findAllByGroupId(req.groupId).forEach {
            val part = partRepo.findById(it.payerId).get()
            debts.add(DebtDetailInfo(it.id, it.name, PartInfo(it.payerId, part.name), it.amount))
        }
        return AADebtResponce(req.groupId, debts)
    }

    /**
     * 获得账目组下的所有参与者
     */
    fun getParts(req: AApartRequest): AApartResponce {
        val parts = ArrayList<PartInfo>()
        partRepo.findAllByGroupId(req.groupId).forEach { parts.add(PartInfo(it.id, it.name)) }
        return AApartResponce(req.groupId, parts)
    }

    /**
     * 增加账目
     */
    fun addDebt(req: AAaddRequest): AAaddResponce {
        var amount = 0
        req.details.forEach { amount += it.amount }
        var debt = DebtInfo(-1, req.groupId, req.name, req.payerId, amount)
        debt = infoRepo.save(debt)
        //infoRepo.flush()
        req.details.forEach {
            val detail = DebtDetail(-1, req.groupId, debt.id, it.partId, it.amount)
            detailRepo.save(detail)
        }
        return AAaddResponce(req.groupId, debt.id, req.name, getPartInfo(req.payerId), amount)
    }

    /**
     * 更改账目组销账状态
     */
    fun lockGroup(req: AAlockRequest): Boolean {
        val group = groupRepo.findById(req.groupId).get()
        if (group.locked == req.locked) return false
        group.locked = req.locked
        groupRepo.save(group)
        return true
    }

    /**
     * 计算账目组总账目
     */
    fun calculate(req: AAcalRequest): AAcalResponce {
        //取出参与者、账目、账目细节
        val parts = partRepo.findAllByGroupId(req.groupId)
        val debts = infoRepo.findAllByGroupId(req.groupId)
        val details = detailRepo.findAllByGroupId(req.groupId)

        //建立参与者->付款信息的映射
        val map = HashMap<Int, PartDetailInfo>()
        parts.forEach { map[it.id] = PartDetailInfo(it.id, it.name, 0, 0) }

        //计算参与者实际付款、应当付款
        debts.forEach {
            if (map[it.payerId] != null) {
                map[it.payerId]!!.paid += it.amount
            }
        }
        details.forEach {
            if (map[it.partId] != null) {
                map[it.partId]!!.total += it.amount
            }
        }

        //计算支付方式
        val pays = ArrayList<PartPayInfo>()
        val infos = ArrayList<PartDetailInfo>(map.values)
        val payers = LinkedList<Pair<Int, Int>>()//之后需要付钱的人
        val receivers = LinkedList<Pair<Int, Int>>()//之后需要收钱的人
        infos.forEach {
            if (it.paid > it.total) receivers.add(Pair(it.partId, it.paid - it.total))
            else if (it.paid < it.total) payers.add(Pair(it.partId, it.total - it.paid))
        }
        payers.sortBy { -it.second }
        receivers.sortBy { -it.second }
        while (payers.isNotEmpty() && receivers.isNotEmpty()) {
            val payer = payers.first
            val receiver = receivers.first
            payers.pollFirst()
            receivers.pollFirst()
            val d = payer.second - receiver.second
            when {
                d == 0 -> {//正好支付
                    pays.add(getPartPayInfo(payer.first, receiver.first, payer.second))
                }
                d < 0 -> {//不足以支付
                    receivers.add(Pair(receiver.first, receiver.second - payer.second))
                    pays.add(getPartPayInfo(payer.first, receiver.first, payer.second))
                }
                d > 0 -> {//支付后还有剩余
                    payers.add(Pair(payer.first, payer.second - receiver.second))
                    pays.add(getPartPayInfo(payer.first, receiver.first, receiver.second))
                }
            }
        }

        //返回计算结果
        return AAcalResponce(req.groupId, infos, pays)
    }

    private fun getPartInfo(partId: Int): PartInfo {
        return PartInfo(partId, partRepo.findById(partId).get().name)
    }

    private fun getPartPayInfo(from: Int, to: Int, amount: Int): PartPayInfo {
        return PartPayInfo(getPartInfo(from), getPartInfo(to), amount)
    }
}