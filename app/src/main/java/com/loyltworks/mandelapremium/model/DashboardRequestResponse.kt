package com.loyltworks.mandelapremium.model

import com.squareup.moshi.JsonClass

// $$$$$$$$$$$$$$$$$$$$ GetMerchantEmailDetailsMobileApp $$$$$$$$$$$$$$$$$$$$$$$$$$
@JsonClass(generateAdapter = true)
data class DashboardRequest(
    val CustomerDetails: CustomerDetails
)

@JsonClass(generateAdapter = true)
data class CustomerDetails(
    val CustomerId: String
)


@JsonClass(generateAdapter = true)
data class DashboardResponse(
    val lstCustomerFeedBackJson: List<LstCustomerDetail>? = null
)

@JsonClass(generateAdapter = true)
data class LstCustomerDetail(
    val Absent_Count: Int? = null,
    val Address: Any? = null,
    val AllowPlanner: Boolean? = null,
    val AttendanceDate: String? = null,
    val BonusDate: Any? = null,
    val BonusName: Any? = null,
    val BonusValue: Any? = null,
    val CallRemarks: Any? = null,
    val CallStatusId: Int? = null,
    val CashBack: Int? = null,
    val Company: String? = null,
    val CustomerEmail: String? = null,
    val CustomerGrade: String? = null,
    val CustomerId: Int? = null,
    val CustomerImage: String? = null,
    val CustomerMobile: String? = null,
    val CustomerStatus: Int? = null,
    val CustomerType: String? = null,
    val CustomerTypeId: Int? = null,
    val Designation: Any? = null,
    val District: Any? = null,
    val EncashBalance: Int? = null,
    val FirstName: String? = null,
    val IsBlacklisted: Int? = null,
    val IsDormant: Int? = null,
    val IsOnHold: Int? = null,
    val IssueDescription: Any? = null,
    val LanguageId: Int? = null,
    val LanguageName: Any? = null,
    val LastActive: Any? = null,
    val LastName: String? = null,
    val LocationId: Int? = null,
    val LocationName: String? = null,
    val LoyaltyId: String? = null,
    val LoyaltyIdQRCode: String? = null,
    val MerchantEmail: String? = null,
    val MerchantId: Int? = null,
    val MerchantMobile: String? = null,
    val Merchantname: String? = null,
    val Month_Name: Any? = null,
    val Month_No: Int? = null,
    val NativeStateId: Int? = null,
    val NativeStateName: Any? = null,
    val OwnerName: String? = null,
    val Password: String? = null,
    val PinStatus: Int? = null,
    val Prefix: Any? = null,
    val Present: Boolean? = null,
    val Present_Count: Int? = null,
    val ProfilePicture: Any? = null,
    val RequiredPoints: Int? = null,
    val StoreName: Any? = null,
    val Tardy: Boolean? = null,
    val Tardy_Count: Int? = null,
    val Title: Any? = null,
    val UserId: Int? = null,
    val VerificationStatus: Any? = null,
    val VerifiedStatus: Int? = null
)


// $$$$$$$$$$$$$$$$$$$$ getCustomerDashboardDetailsMobileApp $$$$$$$$$$$$$$$$$$$$$$$$$$

@JsonClass(generateAdapter = true)
data class DashboardCustomerRequest(
    val ActionType: String?=null,
    val ActorId: String?=null,
    val IsActive: String?=null
)

@JsonClass(generateAdapter = true)
data class DashboardCustomerResponse(
    val ObjActivityDetailsJsonList: List<ObjActivityDetails>?=null,
    val ObjCustomerDashboardList: List<ObjCustomerDashboard>?=null,
    val ObjCustomerDashboardMenuList: Any?=null,
    val ReturnMessage: Any?=null,
    val ReturnValue: Int?=null,
    val TotalRecords: Int?=null,
    val lstUserDashboardDetails: Any?=null
)

@JsonClass(generateAdapter = true)
data class ObjActivityDetails(
    val ActionType: Int?=null,
    val ActivityDate: String?=null,
    val ActorId: Int?=null,
    val ActorRole: Any?=null,
    val IsActive: Boolean?=null,
    val JActivityDate: String?=null,
    val Message: Any?=null,
    val Name: String?=null,
    val Type: String?=null
)

@JsonClass(generateAdapter = true)
data class ObjCustomerDashboard(
    val ActionType: Int?=null,
    val ActorId: Int?=null,
    val ActorRole: Any?=null,
    val BehaviourWisePoints: Int?=null,
    val CustomerType: Any?=null,
    val GiftDonateCount: Int?=null,
    val GiftEvoucherCount: Int?=null,
    val GiftingEvoucherCount: Int?=null,
    val IsActive: Boolean?=null,
    val IsNotionalPoints: Int?=null,
    val LifeTimeEarningsAchi: String?=null,
    val LifeTimeEarningsDamages: String?=null,
    val LifeTimeEarningsDue: String?=null,
    val LifeTimeEarningsMissed: String?=null,
    val LifeTimeEarningsOppor: String?=null,
    val MemberSince: String?=null,
    val MessageCount: Int?=null,
    val NotificationCount: Int?=null,
    val ObjActivityDetailsJsonList: Any?=null,
    val ObjPromotionCommonList: Any?=null,
    val ProfileImage: Any?=null,
    val ProgramBehaviour: Any?=null,
    val ProgramBehaviourId: Int?=null,
    val QR_Code: Any?=null,
    val RedeemPoints: Int?=null,
    val RedeemableEncashBalance: Int?=null,
    val RedeemablePointsBalance: Int?=null,
    val TotalRedeemed: Int?=null,
    val WarningCount: Int?=null
)
