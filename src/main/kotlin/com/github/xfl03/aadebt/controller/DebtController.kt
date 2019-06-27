package com.github.xfl03.aadebt.controller

import com.github.xfl03.aadebt.entity.auth.AuthUserDetail
import com.github.xfl03.aadebt.json.CommonResponse
import com.github.xfl03.aadebt.json.Response
import com.github.xfl03.aadebt.json.debt.*
import com.github.xfl03.aadebt.service.AADebtService.Id
import com.github.xfl03.aadebt.service.DebtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
class DebtController {
    @Autowired
    private lateinit var service: DebtService

    @RequestMapping(path = ["/api/debt/new"], method = [RequestMethod.POST])
    fun new(@RequestBody @Validated req: DebtNewRequest, br: BindingResult): Response {
        if (br.hasErrors()) return CommonResponse(br.allErrors.stream().map { it.defaultMessage }.collect(Collectors.joining(" & ")), -400)

        return service.addGroup(req)
    }

    @RequestMapping(path = ["/api/debt/debt"], method = [RequestMethod.POST])
    fun debt(@RequestBody req: DebtDebtRequest): Response {
        val obj = SecurityContextHolder.getContext().authentication.principal as? AuthUserDetail
                ?: return CommonResponse("Internal Error", -500)

        var check = service.checkId(Id.GROUP, req.groupId, obj.id)
        if (check.code < 0) return check

        return service.getDebts(req)
    }

    @RequestMapping(path = ["/api/debt/add"], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated req: DebtAddRequest): Response {
        val obj = SecurityContextHolder.getContext().authentication.principal as? AuthUserDetail
                ?: return CommonResponse("Internal Error", -500)

        var check = service.checkId(Id.GROUP, req.groupId, obj.id)
        if (check.code < 0) return check

        return service.addDebt(req)
    }

    @RequestMapping(path = ["/api/debt/cal"], method = [RequestMethod.POST])
    fun cal(@RequestBody req: DebtCalRequest, br: BindingResult): Response {
        if (br.hasErrors()) return CommonResponse(br.allErrors.stream().map { it.defaultMessage }.collect(Collectors.joining(" & ")), -400)

        val obj = SecurityContextHolder.getContext().authentication.principal as? AuthUserDetail
                ?: return CommonResponse("Internal Error", -500)

        var check = service.checkId(Id.GROUP, req.groupId, obj.id)
        if (check.code < 0) return check

        return service.calculate(req)
    }

    @RequestMapping(path = ["/api/debt/del"], method = [RequestMethod.POST])
    fun del(@RequestBody req: DebtDelRequest): Response {
        val obj = SecurityContextHolder.getContext().authentication.principal as? AuthUserDetail
                ?: return CommonResponse("Internal Error", -500)

        var check = service.checkId(Id.GROUP, req.groupId, obj.id)
        if (check.code < 0) return check
        check = service.checkId(Id.DEBT, req.debtId, req.groupId)
        if (check.code < 0) return check

        return service.delete(req)
    }

    @RequestMapping(path = ["/api/debt/edit"], method = [RequestMethod.POST])
    fun edit(@RequestBody @Validated req: DebtEditRequest): Response {
        val obj = SecurityContextHolder.getContext().authentication.principal as? AuthUserDetail
                ?: return CommonResponse("Internal Error", -500)

        var check = service.checkId(Id.GROUP, req.groupId, obj.id)
        if (check.code < 0) return check
        check = service.checkId(Id.DEBT, req.debtId, req.groupId)
        if (check.code < 0) return check

        return service.edit(req)
    }

}