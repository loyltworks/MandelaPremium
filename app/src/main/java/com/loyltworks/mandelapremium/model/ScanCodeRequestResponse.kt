package com.loyltworks.mandelapremium.model

import com.squareup.moshi.JsonClass


/*Scan Code Request*/

@JsonClass(generateAdapter = true)
data class QRCodeSaveRequestLists(
    var QRCode: String? = null
)

//@JsonClass(generateAdapter = true)
//data class CustomerOtherInFoRequests()

@JsonClass(generateAdapter = true)
data class ScanRequest(
    var Address: String? = null,
//    var CustomerOtherInFoRequest: List<CustomerOtherInFoRequests>? = null,
    var QRCodeSaveRequestList: List<QRCodeSaveRequestLists>? = null,
    var AccessedDate: String? = null,
    var City: String? = null,
    var LoyaltyID: String? = null,
    var Country: String? = null,
    var SourceType: String? = null,
    var Latitude: String? = null,
    var State: String? = null,
    var PinCode: String? = null,
    var Longitude: String? = null
)

/*Scan Code Response*/

@JsonClass(generateAdapter = true)
data class ScanResponse(
    var QRCodeSaveResponseList: List<QRCodeSaveResponseLists>? = null
)

@JsonClass(generateAdapter = true)
data class QRCodeSaveResponseLists(
    var ActionType: Int? = null,
    var ActorId: Int? = null,
    var ActorRole: Any? = null,
    var IsActive: Boolean? = null,
    var Address: Any? = null,
    var City: Any? = null,
    var CodeStatus: Int? = null,
    var Country: Any? = null,
    var IsNotional: Int? = null,
    var IsScanedSource: Int? = null,
    var Latitude: String? = null,
    var Longitude: String? = null,
    var LoyaltyID: String? = null,
    var PinCode: Any? = null,
    var QRCode: String? = null,
    var Remarks: Any? = null,
    var SourceType: Int? = null,
    var State: Any? = null,
    var Status: String? = null
)


/*Validate Scratch Code Request*/

@JsonClass(generateAdapter = true)
data class ValidateScratchCodeRequest (
    var ActionType: String? = null,
    var ScratchCode: String? = null,
    var LoyaltyID: String? = null
)

/*Validate Scratch Code Response*/

@JsonClass(generateAdapter = true)
data class ValidateScratchCodeResponse(
    var ReturnMessage: Any? = null,
    var ReturnValue: Int? = null,
    var TotalRecords: Int? = null,
    var CustomerEmailID: Any? = null,
    var Customer_ID: Any? = null,
    var Customer_Name: Any? = null,
    var Is_Valid: Boolean? = null,
    var ValidationStatus: String? = null
)