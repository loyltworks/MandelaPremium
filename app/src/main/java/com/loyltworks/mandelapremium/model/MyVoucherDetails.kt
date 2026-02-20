package com.loyltworks.mandelapremium.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable


/*Get AlbumWithImage Request*/
@JsonClass(generateAdapter = true)
data class GetAlbumsWithImagesRequest(
    var AlbumId: Int? = null,
)


/*GetAlbumWithImage Response*/
@JsonClass(generateAdapter = true)
data class GetAlbumsWithImagesResponse(
    val ReturnMessage: String? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: String? = null,
    val LstPromotions: List<LstPromotions>? = null
)

/*GetAlbumWithImage Response List*/
@JsonClass(generateAdapter = true)
data class LstPromotions(
    var AlbumId: Int? = null,
    var AlbumImageID: Int? = null,
    var AlbumName: String? = null,
    var ClaimReserveExpiry: Int? = null,
    var CuSegmentId: Int? = null,
    var CuSegmentType: String? = null,
    var ExpiryDate: String? = null,
    var ImageName: String? = null,
    var ImagePath: String? = null,
    var LTaragetAudience: String? = null,
    var LocationId: Int? = null,
    var Responses: String? = null,
    var StartDate: String? = null,
    var Url: String? = null,
    var is_Active: Boolean? = null,
)

/*Get Receiver ID Exist or Not Request*/
@JsonClass(generateAdapter = true)
data class GetReceiverIDRequest(
    var ActionType: String? = null,
    var ActorId: String? = null,
    var LoyaltyId: String? = null
)


/*Get Receiver ID Exist or Not Response */
@JsonClass(generateAdapter = true)
data class GetReceiverIDResponse(
    @Json(name = "LstGiftCardHolderInfoDetails")
    var lstGiftCardHolderInfoDetails: Any? = null,
    @Json(name = "LstGiftCardIssueDetails")
    var lstGiftCardIssueDetails: Any? = null,
    @Json(name = "LstGiftCardNomineeDetails")
    var lstGiftCardNomineeDetails: Any? = null,
    @Json(name = "LstGiftCardType")
    var lstGiftCardType: List<Any?>? = null,
    @Json(name = "LstIssuedGiftCardList")
    var lstIssuedGiftCardList: Any? = null,
    @Json(name = "lstPointbalance")
    var lstPointbalance: Any? = null,
    @Json(name = "ReturnMessage")
    var ReturnMessage: String? = null,
    @Json(name = "ReturnValue")
    var returnValue: Int? = null,
    @Json(name = "TotalRecords")
    var totalRecords: Int? = null
)


/* My VoucherGiftCard Submit Request */
@JsonClass(generateAdapter = true)
data class MyVoucherGiftCardSubmitRequest(
    var Pin: String,
    var GiftCardNomineeDetails: String,
    var LstGiftCardIssueDetails: String,
    var ActorId: Int,
    var ActionType: Int,
    var GiftCardIssueDetails: VGiftCardIssueDetails,
    var GiftCardHolderInfoDetails: GiftCardHolderInfoDetails

)


@JsonClass(generateAdapter = true)
data class VGiftCardIssueDetails(
    var GiftCardIssueId: Int,
    var LoyaltyId: String,
    var CardNumber: String,
    var TopUpAmount: String,
    var IssuedEncashBalance: Boolean,
    var Is_Active: Boolean,
    var CustomerType: String,
    var Password: String,
    var LocationID: Int,
)


@JsonClass(generateAdapter = true)
data class GiftCardHolderInfoDetails(

    var Mobile: String,
    var Email: String,
    var PersonalMessage: String,
    var SenderName: String,
    var RecipentName: String,
    var PinType: String,
    var OwnerEmail: String,
    var EvoucherName: String,
    var EvoucherNumber: String,
    var CardOwnerMobile: String
)

/* My VoucherGiftCard Submit Response */
@JsonClass(generateAdapter = true)
data class MyVoucherGiftCardSubmitResponse(
    var ReturnMessage: String? = null,
    var ReturnValue: Int? = null,
    var TotalRecords: Int? = null
)
