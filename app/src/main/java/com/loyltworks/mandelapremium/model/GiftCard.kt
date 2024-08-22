package com.loyltworks.mandelapremium.model

import com.squareup.moshi.JsonClass
import java.io.Serializable


/*GiftCard Request*/
@JsonClass(generateAdapter = true)
data class GetGiftCardRequest(
    var ActionType: String? = null,
    var ActorId: String? = null
)


/*GiftCard Response*/
@JsonClass(generateAdapter = true)
data class GetGiftCardResponse(
    val lstMerchantinfo: List<lstMerchantinfo>? = null
)

/*GiftCard Response List*/
@JsonClass(generateAdapter = true)
data class lstMerchantinfo(
    var ActionType: Int? = null,
    var ActorId: Int? = null,
    var ActorRole: String? = null,
    var IsActive: Boolean? = null,
    var GiftCardType: String? = null,
    var GiftNowIsVisible: Boolean? = null,
    var GiftedBy: String? = null,
    var GiftedDate: String? = null,
    var Imageurl: String? = null,
    var GiftedUserId: String? = null,
    var Instruction: String? = null,
    var IsEncash: Boolean? = null,
    var IsGifted: Boolean? = null,
    var ReceiverLoyaltyId: String? = null,
    var Locations: String? = null,
    var MaxValue: Int? = null,
    var MerchantID: Int? = null,
    var MerchantName: String? = null,
    var MinValue: Int? = null,
    var Pin: String? = null,
    var ReceiverName: String? = null,
    var Status: Int? = null,
    var TermsAndConditions: String? = null,
    var VoucherDetails: String? = null
) : Serializable