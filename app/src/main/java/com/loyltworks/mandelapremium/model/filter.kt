package com.loyltworks.mandelapremium.model

import com.squareup.moshi.JsonClass

/*FilterStatus */
@JsonClass(generateAdapter = true)
data class FilterStatus(
    var filterName: String? = null,
    var filterID: Int? = null,
)
