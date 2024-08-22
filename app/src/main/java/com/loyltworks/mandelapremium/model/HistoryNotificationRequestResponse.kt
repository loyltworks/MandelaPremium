package com.loyltworks.mandelapremium.model

import com.squareup.moshi.JsonClass
import java.util.*
/*History Notification request*/
@JsonClass(generateAdapter = true)
data class HistoryNotificationRequest(
    var ActionType: String? = null,
    var ActorId: String? = null,
    var LoyaltyId: String? = null
)

/*History Notification response*/
@JsonClass(generateAdapter = true)
data class HistoryNotificationResponse(
    var ReturnMessage: Any? = null,
    var ReturnValue: Int? = null,
    var TotalRecords: Int? = null,
    var lstPushHistoryJson: MutableList<LstPushHistory>? = null
)

@JsonClass(generateAdapter = true)
data class LstPushHistory(
    var CreatedBy: Int? = null,
    var CreatedDate: String? = null,
    var ImagesURL: String? = null,
    var IsActive: Int? = null,
    var IsRead: Int? = null,
    var JCreatedDate: String? = null,
    var LoyaltyId: String? = null,
    var ModifiedBy: Int? = null,
    var PushHistoryId: Int? = null,
    var PushId: String? = null,
    var PushMessage: String? = null,
    var PushType: String? = null,
    var SourceType: String? = null,
    var Title: String? = null
)

/* History Notification detail by id request */
@JsonClass(generateAdapter = true)
data class HistoryNotificationDetailsRequest (
     var ActionType: String? = null,
     var ActorId: String? = null,
     var LoyaltyId: String? = null,
     var PushHistoryIds: String? = null
)

