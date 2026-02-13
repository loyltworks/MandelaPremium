package com.loyltworks.mandelapremium.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    var Password: String? = null,
    var UserName: String? = null,
    var UserActionType: String? = null,
    var Browser: String? = null,
    var LoggedDeviceName: String? = null,
    var PushID: String? = null,
    var UserType: String? = null
)

@JsonClass(generateAdapter = true)
data class LoginResponse(
        var MerchantImageDetails: String? = null,
        var UserId: Int? = null,
        var UserList: List<UserList>? = null,
        var lstMerchantImageDetails: String? = null,
        var objUserDetailedInfo: String? = null
)

@JsonClass(generateAdapter = true)
data class UserList(
    val ActionType: Int? = null,
    val C_MerchantId: Int? = null,
    val CityName: Any? = null,
    val CommonUserMobile: Any? = null,
    val CommonUserName: Any? = null,
    val Country: Any? = null,
    val CountryCode: Any? = null,
    val Currency: Any? = null,
    val CustAccountNumber: String? = null,
    val CustAccountType: Any? = null,
    val CustomerGrade: Any? = null,
    val CustomerTypeID: Int? = null,
    val DOB: Any? = null,
    val Email: String? = null,
    val Encrypted_OTP_PIN: Any? = null,
    val IsActive: Int? = null,
    val IsUserActive: Int? = null,
    val IsBlacklisted: Int? = null,
    val IsDormant: Int? = null,
    val IsGeofenceActive: Int? = null,
    val IsOnHold: Int? = null,
    val LocationCountryID: Int? = null,
    val LocationId: Int? = null,
    val LocationName: String? = null,
    val LocationType: Any? = null,
    val MemberSince: Any? = null,
    val MerchantEmailID: String? = null,
    val MerchantId: Int? = null,
    val MerchantMobileNo: String? = null,
    val MerchantName: Any? = null,
    val Merchant_logo: Any? = null,
    val Mobile: String? = null,
    val Name: String? = null,
    val ParentLocationId: Int? = null,
    val ParentLocationName: Any? = null,
    val Password: String? = null,
    val PinStatus: Int? = null,
    val Prefix: String? = null,
    val Result: Int? = null,
    val RoleName: Any? = null,
    val Status: String? = null,
    val UserGender: Any? = null,
    val UserId: Int? = null,
    val UserImage: Any? = null,
    val UserLastName: Any? = null,
    val UserName: String? = null,
    val UserType: String? = null,
    val VerifiedStatus: Int? = null,
    @Json(name = "MembershipID")
    val membershipID: String?=null,
    @Json(name = "CustomerName")
    val customerName: String?=null,
    @Json(name = "DeletedDate")
    val deletedDate: String?=null,
    @Json(name = "AccountStatus")
    val accountStatus: String?=null,
    @Json(name = "IsDelete")
    val isDelete: Int? = null
)