package com.loyltworks.mandelapremium.model

import com.squareup.moshi.JsonClass



  // location wise point request
@JsonClass(generateAdapter = true)
data class WishPointRequest(
      var ActorId: String? = null,
      var ActionType: String? = null,
      var LocationId: String? = null
)

@JsonClass(generateAdapter = true)
data class WishPointsResponse(
    val ObjActivityDetailsJsonList: Any? = null,
    val ObjActivityDetailsList: Any? = null,
    val ObjCustomerDashboardList: List<ObjCustomerDashboards>? = null,
    val ObjCustomerDashboardMenuList: Any? = null,
    val ReturnMessage: Any? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null,
    val lstUserDashboardDetails: Any? = null
)

@JsonClass(generateAdapter = true)
data class ObjCustomerDashboards(
    val ActionType: Int? = null,
    val ActorId: Int? = null,
    val ActorRole: Any? = null,
    val BehaviourWisePoints: Int? = null,
    val CustomerCredit: Int? = null,
    val CustomerGrade: Any? = null,
    val CustomerId: Int? = null,
    val CustomerType: Any? = null,
    val GiftDonateCount: Int? = null,
    val GiftEvoucherCount: Int? = null,
    val GiftingEvoucherCount: Int? = null,
    val IdentityProofsNotification: Int? = null,
    val IsActive: Boolean? = null,
    val IsNotionalPoints: Int? = null,
    val LifeTimeEarningsAchi: Any? = null,
    val LifeTimeEarningsDamages: Any? = null,
    val LifeTimeEarningsDue: Any? = null,
    val LifeTimeEarningsMissed: Any? = null,
    val LifeTimeEarningsOppor: Any? = null,
    val MemberSince: Any? = null,
    val MessageCount: Int? = null,
    val Name: Any? = null,
    val NotificationCount: Int? = null,
    val ObjActivityDetailsList: Any? = null,
    val ObjPromotionCommonList: Any? = null,
    val OverAllPoints: Int? = null,
    val PointExpiryCount: Int? = null,
    val ProfileImage: String? = null,
    val ProgramBehaviour: String? = null,
    val ProgramBehaviourId: Int? = null,
    val QR_Code: Any? = null,
    val RedeemPoints: Int? = null,
    val RedeemableEncashBalance: Int? = null,
    val RedeemablePointsBalance: Int? = null,
    val ReferralBonusPoints: Int? = null,
    val ReferralCount: Int? = null,
    val RetailerId: Int? = null,
    val SubscriberCount: Int? = null,
    val TeirUpgradeMessage: Any? = null,
    val TotalRedeemed: Int? = null,
    val TotalRows: Int? = null,
    val WarningCount: Int? = null
)