package com.github.xfl03.aadebt.controller

import com.github.xfl03.aadebt.entity.auth.AuthUserDetail
import com.github.xfl03.aadebt.json.*
import com.github.xfl03.aadebt.json.aa.*
import com.github.xfl03.aadebt.service.AADebtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
class AADebtController {

    @Autowired
    lateinit var service: AADebtService

    @RequestMapping(path = ["/api/aa/list"], method = [RequestMethod.GET, RequestMethod.POST])
    fun list(): Response {
        return service.getGroups()
    }

    @RequestMapping(path = ["/api/aa/new"], method = [RequestMethod.POST])
    fun new(@RequestBody @Validated req: AAnewRequest, br: BindingResult): Response {
        if (br.hasErrors()) return CommonResponse(br.allErrors.stream().map { it.defaultMessage }.collect(Collectors.joining(" & ")), -400)

        return service.addGroup(req)
    }

    @RequestMapping(path = ["/api/aa/debt"], method = [RequestMethod.POST])
    fun debt(@RequestBody req: AAdebtRequest): Response {
        val obj = SecurityContextHolder.getContext().authentication.principal as? AuthUserDetail
                ?: return CommonResponse("Internal Error", -500)

        val check = service.checkId(AADebtService.Id.GROUP, req.groupId, obj.id)
        if (check.code < 0) return check

        return service.getDebts(req)
    }

    @RequestMapping(path = ["/api/aa/part"], method = [RequestMethod.POST])
    fun part(@RequestBody req: AApartRequest): Response {
        val obj = SecurityContextHolder.getContext().authentication.principal as? AuthUserDetail
                ?: return CommonResponse("Internal Error", -500)

        val check = service.checkId(AADebtService.Id.GROUP, req.groupId, obj.id)
        if (check.code < 0) return check

        return service.getParts(req)
    }

    @RequestMapping(path = ["/api/aa/add"], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated req: AAaddRequest, br: BindingResult): Response {
        if (br.hasErrors()) return CommonResponse(br.allErrors.stream().map { it.defaultMessage }.collect(Collectors.joining(" & ")), -400)

        val obj = SecurityContextHolder.getContext().authentication.principal as? AuthUserDetail
                ?: return CommonResponse("Internal Error", -500)

        var check = service.checkId(AADebtService.Id.GROUP, req.groupId, obj.id)
        if (check.code < 0) return check
        check = service.checkId(AADebtService.Id.PART, req.payerId, req.groupId)
        if (check.code < 0) return check
        req.details.forEach {
            check = service.checkId(AADebtService.Id.PART, it.partId, req.groupId)
            if (check.code < 0) return check
        }

        return service.addDebt(req)
    }

    @RequestMapping(path = ["/api/aa/lock"], method = [RequestMethod.POST])
    fun lock(@RequestBody req: AAlockRequest): Response {
        val obj = SecurityContextHolder.getContext().authentication.principal as? AuthUserDetail
                ?: return CommonResponse("Internal Error", -500)

        val check = service.checkId(AADebtService.Id.GROUP, req.groupId, obj.id)
        if (check.code < 0) return check

        return if (service.lockGroup(req))
            CommonResponse("Changed", 0)
        else
            CommonResponse("Already changed", -1)
    }

    @RequestMapping(path = ["/api/aa/cal"], method = [RequestMethod.POST])
    fun cal(@RequestBody req: AAcalRequest): Response {
        val obj = SecurityContextHolder.getContext().authentication.principal as? AuthUserDetail
                ?: return CommonResponse("Internal Error", -500)

        val check = service.checkId(AADebtService.Id.GROUP, req.groupId, obj.id)
        if (check.code < 0) return check

        return service.calculate(req)
    }
}