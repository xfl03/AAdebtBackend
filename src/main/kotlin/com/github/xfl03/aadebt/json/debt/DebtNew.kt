package com.github.xfl03.aadebt.json.debt

import javax.validation.constraints.NotBlank

data class DebtNewRequest(
        @field:NotBlank(message = "Name requested")
        var name: String
)