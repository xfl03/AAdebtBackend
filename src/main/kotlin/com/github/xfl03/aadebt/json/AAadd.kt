package com.github.xfl03.aadebt.json

import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class AAaddRequest(
        var groupId: Int,
        @field:NotBlank(message = "Name requested")
        var name: String,
        var payerId: Int,
        @field:NotEmpty(message = "Detail requested")
        @field:Valid
        var details: List<DetailInfo>
)

data class AAaddResponse(var groupId: Int, var debtId: Int, var name: String, var payer: PartInfo, var amount: Int): Response()