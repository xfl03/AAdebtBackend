package com.github.xfl03.aadebt.controller

import com.github.xfl03.aadebt.json.*
import com.github.xfl03.aadebt.service.AADebtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class AADebtController {

    @Autowired
    lateinit var service:AADebtService

    @RequestMapping(path = ["/aa/list"], method = [RequestMethod.GET, RequestMethod.POST])
    fun list(): AAlistResponce {
        return service.getGroups()
    }

    @RequestMapping(path = ["/aa/new"], method = [RequestMethod.POST])
    fun new(@RequestBody req: AAnewRequest): AAnewResponce {
        return service.addGroup(req)
    }

    @RequestMapping(path = ["/aa/debt"], method = [RequestMethod.POST])
    fun debt(@RequestBody req: AADebtRequest): AADebtResponce {
        return service.getDebts(req)
    }

    @RequestMapping(path = ["/aa/part"], method = [RequestMethod.POST])
    fun part(@RequestBody req: AApartRequest): AApartResponce {
        return service.getParts(req)
    }

    @RequestMapping(path = ["/aa/add"], method = [RequestMethod.POST])
    fun add(@RequestBody req: AAaddRequest): AAaddResponce {
        return service.addDebt(req)
    }

    @RequestMapping(path = ["/aa/lock"], method = [RequestMethod.POST])
    fun lock(@RequestBody req: AAlockRequest): CommonReponce {
        return if (service.lockGroup(req))
            CommonReponce("已变更", 0)
        else
            CommonReponce("未变更", -1)
    }

    @RequestMapping(path = ["/aa/cal"], method = [RequestMethod.POST])
    fun cal(@RequestBody req: AAcalRequest): AAcalResponce {
        return return service.calculate(req)
    }

}