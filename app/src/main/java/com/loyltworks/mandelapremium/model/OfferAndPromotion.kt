package com.loyltworks.mandelapremium.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

/*WhatsNew Request*/
@JsonClass(generateAdapter = true)
data class GetWhatsNewRequest(
        var ActionType: String? = null,
        var LocationId: Int? = null,
        var ActorId: String? = null
)

/*WhatsNew detail Request*/
@JsonClass(generateAdapter = true)
data class GetPromotionDetailsRequest(
        var ActorId: String? = null,
        var PromotionId: Int? = null
)

/*WhatsNew Response*/
@JsonClass(generateAdapter = true)
data class GetPromotionResponse(
        var ReturnMessage: Any? = null,
        var ReturnValue: Int? = null,
        var TotalRecords: Int? = null,
        var LstPromotionJsonList: List<LstPromotionList>? = null,
        var LstPromotionUserActionDetails: List<LstPromotionUserActionDetail>? = null,
        var lstPromotionView: Any? = null
)

@JsonClass(generateAdapter = true)
data class LstPromotionList(
        var PromotionId: Int? = null,
        var ActionUrl: Any? = null,
        var AlbumImageID: Int? = null,
        var AllowUnReserve: Boolean? = null,
        var CanClaim: Boolean? = null,
        var CanComment: Boolean? = null,
        var CanReverse: Boolean? = null,
        var CanView: Boolean? = null,
        var CategoryId: Int? = null,
        var CategoryName: Any? = null,
        var Claim: String? = null,
        var CliamIdUsageStatus: Boolean? = null,
        var Comment: Any? = null,
        var FirstName: Any? = null,
        var Footer: String? = null,
        var FullName: Any? = null,
        var IsClaimed: Boolean? = null,
        var IsReservable: Boolean? = null,
        var IsReserved: Boolean? = null,
        var Is_Expired: Boolean? = null,
        var LastName: Any? = null,
        var MemberId: Any? = null,
        var MemberName: Any? = null,
        var MobileNo: Any? = null,
        var PointBalance: Int? = null,
        var ProImage: String? = null,
        var ProLongDesc: String? = null,
        var ProShortDesc: String? = null,
        var PromotionName: String? = null,
        var PromotionTypeName: Any? = null,
        var Promotion_Type: Int? = null,
        var PushUserActionID: Int? = null,
        var OutletName: String? = null,
        var Reserve: Any? = null,
        var TargetCustomers: Int? = null,
        var Title: Any? = null,
        var TotalBlocks: Int? = null,
        var TotalClaims: Int? = null,
        var TotalComment: Int? = null,
        var TotalRecords: Int? = null,
        var TotalResponses: Int? = null,
        var TotalViews: Int? = null,
        var Url: String? = null,
        var ValidFrom: String? = null,
        var ValidTo: String? = null,
        var Validity: String? = null,
        var View: Any? = null,
        var Views: Int? = null,
        var is_Active: Boolean? = null,
        var isFlag: Boolean? = null,
        // current position show for index
        var currentIndex: Boolean? = null
):Serializable

@JsonClass(generateAdapter = true)
data class LstPromotionUserActionDetail(
        var PromotionId: Int? = null,
        var AllowUnReserve: Int? = null,
        var BlockReserve: Int? = null,
        var CanClaim: Boolean? = null,
        var CanComment: Boolean? = null,
        var CanReverse: Boolean? = null,
        var CanView: Boolean? = null,
        var ClaimedPromotion: Int? = null,
        var HasClaimOpt: Boolean? = null,
        var HasData: Boolean? = null,
        var HasReserveOpt: Boolean? = null,
        var HideReserve: Int? = null,
        var MaxBlock: Int? = null,
        var MaxClaims: Int? = null,
        var TotalClaimAndReserved: Int? = null,
        var TotalClaimNow: Int? = null,
        var TotalReservNow: Int? = null,
        var TotalViewsNow: Int? = null
)

/*Save Customer WhatsNew Request*/
@JsonClass(generateAdapter = true)
data class SaveCustomerPromotionRequest (
        var ActionType: String? = null,
        var ActorId: String? = null,
        var Comment: String? = null,
        var PromotioniD: String? = null,
        var CustomerName: String? = null,
        var LoyaltyId: String? = null,
        var Mobile: String? = null,
        var PromotionName: String? = null,
        var IsReserved: String? = null,
        var IsUnReserved: String? = null
)

/*Save Customer WhatsNew Response*/
@JsonClass(generateAdapter = true)
data class SaveCustomerPromotionResponse(
        var ReturnMessage: String? = null,
        var ReturnValue: Int? = null,
        var TotalRecords: Int? = null,
        var LstCoupons: Any? = null,
        var LstPromotions: Any? = null
)
