package com.github.xfl03.aadebt.json.aa

import com.github.xfl03.aadebt.json.Response
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class AAnewRequest(
        @field:NotBlank(message = "Name requested")
        var name: String,
        @field:Size(min = 2, message = "Parts need 2 people at least")
        var parts: List<String>
)

data class AAnewResponse(var groupId: Int, var name: String, var parts: List<PartInfo>) : Response()