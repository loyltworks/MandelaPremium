package com.loyltworks.mandelapremium.model
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

//Delete Account Request Response
@JsonClass(generateAdapter = true)
data class DeleteAccountRequest(
    @Json(name = "ActionType")
    var actionType: Int? = null,
    @Json(name = "UserName")
    var userName: String? = null,
    @Json(name = "userid")
    var userid: String? = null
)

@JsonClass(generateAdapter = true)
data class DeleteAccountResponse(
    @Json(name = "isActive")
    var isActive: Boolean? = null,
    @Json(name = "returnMessage")
    var returnMessage: String? = null,
    @Json(name = "returnValue")
    var returnValue: Int? = null,
    @Json(name = "totalRecords")
    var totalRecords: Int? = null
)

//Delete Account Cancel Request Response
@JsonClass(generateAdapter = true)
data class CancelRequest(
    @Json(name = "ActionType")
    var actionType: Int? = null,
    @Json(name = "EmailOrMobile")
    var emailOrMobile: String? = null,
    @Json(name = "FirstName")
    var firstName: String? = null,
    @Json(name = "UserName")
    var userName: String? = null,
    @Json(name = "userid")
    var userid: Int? = null
)

@JsonClass(generateAdapter = true)
data class CancelResponse(
    @Json(name = "isActive")
    var isActive: Boolean? = null,
    @Json(name = "returnMessage")
    var returnMessage: String? = null,
    @Json(name = "returnValue")
    var returnValue: Int? = null,
    @Json(name = "totalRecords")
    var totalRecords: Int? = null
)