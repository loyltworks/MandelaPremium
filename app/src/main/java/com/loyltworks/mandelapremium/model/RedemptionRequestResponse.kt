package com.loyltworks.mandelapremium.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RedemptionRequest(
    var ActionType: Int? = null,
    var ActorId: String? = null,
    var JFromDate: String? = null,
    var JToDate: String? = null,
    var LocationId: Int? = null,
    var PageSize: Int? = null,
    var StartIndex: Int? = null,
    var StatusId: Int? = null
)

@JsonClass(generateAdapter = true)
data class RedemptionResponse(
    val LstGiftCardIssueDetailsJson: List<LstGiftCardIssueDetailsJson>? = null,
    val ReturnMessage: Any? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class LstGiftCardIssueDetailsJson(
    val Amount: Double? = null,
    val BehaviourName: String? = null,
    val CardIssueDate: Any? = null,
    val CardNumber: String? = null,
    val CardUsedOrNot: Any? = null,
    val CreatedDate: Any? = null,
    val CurrencyName: String? = null,
    val CustomerType: Any? = null,
    val FbAmount: Int? = null,
    val FbPoints: Int? = null,
    val FileStatus: Boolean? = null,
    val GiftCardIssueId: Int? = null,
    val GiftCardTypeId: Int? = null,
    val InvoiceNo: Any? = null,
    val IsUsingLoyaltyPoints: Boolean? = null,
    val Is_Active: Boolean? = null,
    val IssuedEncashBalance: Boolean? = null,
    val JTransactiondate: String? = null,
    val LocationID: Int? = null,
    val LocationName: String? = null,
    val LoyaltyId: String? = null,
    val MerchantName: String? = null,
    val MobileNo: Any? = null,
    val NGO_Id: Int? = null,
    val Name: String? = null,
    val Password: Any? = null,
    val PlanePassword: Any? = null,
    val PointsAwarded: Int? = null,
    val PointsRedeemed: Int? = null,
    val ProgramStartDate: Any? = null,
    val Reference: Any? = null,
    val Remarks: Any? = null,
    val RoomAmount: Int? = null,
    val RoomPoints: Int? = null,
    val RowNo: Int? = null,
    val Status: String? = null,
    val TopUpAmount: Int? = null,
    val TotalPoints: Int? = null,
    val TotalRows: Int? = null,
    val TransactionBalance: Int? = null,
    val TransactionMode: Any? = null,
    val TransactionNo: String? = null,
    val TransactionType: String? = null,
    val Transactiondate: Any? = null,
    val Validity: Any? = null
)