package com.loyltworks.mandelapremium.model

import com.squareup.moshi.JsonClass

/*Transaction History Listing*/
@JsonClass(generateAdapter = true)
data class TransactionHistoryRequest (
    var ActorId: String? = null,
    var MerchantId: Int? = null,
    var LocationId: Int? = null,
    var JFromDate: String? = null,
    var CreatedBy: Int? = null,
    var JToDate: String? = null,
    var IsPDF: Int? = null,
    var Name: String? = null,
    var TransactionType: Int? = null,
    var InvoiceNo: String? = null
)

/*LstRewardTransDetail Response*/
@JsonClass(generateAdapter = true)
data class LstRewardTransDetail (
    var Amount: Int? = null,
    var BehaviorId: Int? = null,
    var BonusName: String? = null,
    var BrandName: Any? = null,
    var CashierName: Any? = null,
    var CategoryName: String? = null,
    var CreatedBy: Any? = null,
    var CurrencyName: String? = null,
    var ExpairyOnDate: Any? = null,
    var InvoiceNo: String? = null,
    var JTranDate: String? = null,
    var LocationID: Int? = null,
    var LocationName: String? = null,
    var LoyaltyId: String? = null,
    var LtyBehaviourId: Int? = null,
    var IsNotionalId: Int? = null,
    var MemberName: Any? = null,
    var MerchantID: Int? = null,
    var MerchantName: String? = null,
    var PartyName: Any? = null,
    var PointBalance: Int? = null,
    var PointsGiftedBy: String? = null,
    var ProcessDateTime: Any? = null,
    var ProdCode: String? = null,
    var ProdName: String? = null,
    var Quantity: Int? = null,
    var Remarks: String? = null,
    var RewardPoints: Int? = null,
    var SerialNumber: Any? = null,
    var SkuName: String? = null,
    var TotalRows: Int? = null,
    var TranDate: String? = null,
    var TransactionType: String? = null,
    var VendorName: Any? = null
)

/*LstRewardTransactionBasedonProduct Response*/
@JsonClass(generateAdapter = true)
data class LstRewardTransactionBasedonProduct (
    var Amount: Int? = null,
    var Discount: Int? = null,
    var InvoiceNo: String? = null,
    var Qty: Int? = null,
    var RewardPoints: Int? = null,
    var SerialNumber: String? = null
)

/*TransactionHistory Response*/
@JsonClass(generateAdapter = true)
data class TransactionHistoryResponse (
    var ReturnMessage: Any? = null,
    var ReturnValue: Int? = null,
    var TotalRecords: Int? = null,
    var PDF: String? = null,
    var lstRewardTransJsonDetails: List<LstRewardTransDetail>? = null,
    var lstRewardTransactionBasedonProduct: List<LstRewardTransactionBasedonProduct>? = null

)