package com.loyltworks.mandelapremium.model

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.squareup.moshi.JsonClass
import org.jetbrains.annotations.NotNull

// ################# OTP #########################

@JsonClass(generateAdapter = true)
data class OTPRequest(
    val EmailID: String? = null,
    val MerchantUserName: String? = null,
    val Name: String? = null,
    val OTPAlertType: String? = null,
    val OTPType: String? = null,
    val UserId: String? = null,
    val UserName: String? = null
)

@JsonClass(generateAdapter = true)
data class OTPResponse(
    val AdminList: Any? = null,
    val MerchantEmailSMSDetails: Any? = null,
    val MerchantEmailSMSParameterDetails: Any? = null,
    val ReturnMessage: String? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null
)

// ################# Email Check #########################

@JsonClass(generateAdapter = true)
data class EmailCheckRequest(
    val ActionType: String,
    val Location: Location
)

@JsonClass(generateAdapter = true)
data class Location(
    val UserName: String
)

@JsonClass(generateAdapter = true)
data class EmailCheckResponse(
    val CheckUserNameExistsResult: CheckUserNameExistsResult
)

@JsonClass(generateAdapter = true)
data class CheckUserNameExistsResult(
    val ReturnMessage: Any? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null
)

// ###################  Email Domain validate ########################

@JsonClass(generateAdapter = true)
data class EmailDomainCheckRequest(
    val DomainName: String? = null
)

@JsonClass(generateAdapter = true)
data class EmailDomainCheckResponse(
    val ReturnMessage: Any? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null,
    val lstEmailDomain: List<LstEmailDomain>? = null
)

@JsonClass(generateAdapter = true)
data class LstEmailDomain(
    val Email: String? = null,
    val Mobile: Any? = null
)