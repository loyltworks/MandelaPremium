package com.loyltworks.mandelapremium.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

/*WhatsNew Response*/
@JsonClass(generateAdapter = true)
data class GetProductResponse(
    var ReturnMessage: Any? = null,
    var ReturnValue: Int? = null,
    var TotalRecords: Int? = null,
)

@JsonClass(generateAdapter = true)
data class LstProductsList(
    // current position show for index
    var currentIndex: Boolean? = null
): Serializable
