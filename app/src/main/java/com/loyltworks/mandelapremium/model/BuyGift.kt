package com.loyltworks.mandelapremium.model

import com.squareup.moshi.JsonClass
import java.io.Serializable


/*BuyGift Request*/
@JsonClass(generateAdapter = true)
data class GetBuyGiftRequest(
    var ActionType: String? = null,
    var ActorId: String? = null
)


/*BuyGift Response*/
@JsonClass(generateAdapter = true)
data class GetBuyGiftResponse(
    var lstMerchantinfo: List<String>? = null,
    val lstVoucherDetails: List<LstVoucherDetails>? = null
)

/*BuyGift Response List*/
@JsonClass(generateAdapter = true)
data class LstVoucherDetails(
    var ActionType: Int? = null,
    var ActorId: Int? = null,
    var ActorRole: String? = null,
    var IsActive: Boolean? = null,
    var CardCatName: String? = null,
    var CardName: String? = null,
    var CardTypeId: Int? = null,
    var ExpiryDate: String? = null,
    var Expirydays: Int? = null,
    var FixedValue: Int? = null,
    var GiftCardType: String? = null,
    var ImageUrl: String? = null,
    var Instruction: String? = null,
    var IsEncashBalance: Boolean? = null,
    var LocationName: String? = null,
    var MaxValue: Int? = null,
    var Message: String? = null,
    var MinValue: Int? = null,
    var TermsAndConditions: String? = null
) : Serializable

/*Cash back Value Request*/
@JsonClass(generateAdapter = true)
data class GetCashBackRequest(
    var Points_Required: String? = null,
    var MerchantID: String? = null,
    var MerchantUserName: String? = null
)

/*Cash back Value Response*/
@JsonClass(generateAdapter = true)
data class GetCashBackResponse(
    var ActionType: Int? = null,
    var ActorId: Int? = null,
    var ActorRole: String? = null,
    var IsActive: Boolean? = null,
    var CashBack_Value: Int? = null,
    var Cashback_Output: Int? = null,
    var MerchantID: Int? = null,
    var MerchantUserName: String? = null,
    var Points_Required: Int? = null
)


/*Save Gift Card Request */
@JsonClass(generateAdapter = true)
data class GetSaveGiftCardRequest(
    var GiftCardIssueDetails: GiftCardIssueDetails,
    var ActorId: Int,
    var ActionType: Int

)

@JsonClass(generateAdapter = true)
data class GiftCardIssueDetails(
    var GiftCardTypeId: Int? = null,
    var GiftCardIssueId: Int? = null,
    var LoyaltyId: String? = null,
    var CardNumber: String? = null,
    var TopUpAmount: Int? = null,
    var IssuedEncashBalance: Boolean? = null,
    var Is_Active: Boolean? = null,
    var IsUsingLoyaltyPoints: Boolean? = null,
    var MerchantName: String? = null,
    var MobileNo: String? = null,
    var GiftCardCatName: String? = null
)


/*Save Gift Card Response */
@JsonClass(generateAdapter = true)
data class GetSaveGiftCardResponse(
    var ReturnMessage: String? = null
)


