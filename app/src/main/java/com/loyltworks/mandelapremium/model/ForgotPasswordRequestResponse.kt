package com.loyltworks.mandelapremium.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForgotPasswordRequest (
    var UserName: String? = null
)

@JsonClass(generateAdapter = true)
data class ForgotPasswordResponse (
    var forgotPasswordMobileAppResult: Boolean? = null
)