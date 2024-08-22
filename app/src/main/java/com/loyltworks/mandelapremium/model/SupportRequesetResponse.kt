package com.loyltworks.mandelapremium.model

import com.squareup.moshi.JsonClass
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

/*Query Listing*/
@JsonClass(generateAdapter = true)
data class QueryListingRequest(
        val ActionType: String? = null,
        val ActorId: String? = null,
        val JFromDate: String? = null,
        val JToDate: String? = null,
        val TicketStatusId: String? = null,
        val HelpTopicID: String? = null
)

@JsonClass(generateAdapter = true)
data class QueryListingResponse(
        val returnMessage: Any? = null,
        val returnValue: Int? = null,
        val totalRecords: Int? = null,
        val objCustomerAllQueryJsonList: List<ObjCustomerAllQueryList>? = null
)

@JsonClass(generateAdapter = true)
data class ObjCustomerAllQueryList(
        val CreatedDate: String? = null,
        val JCreatedDate: String? = null,
        val CustomerTicketID: Int? = null,
        val CustomerTicketRefNo: String? = null,
        val DeviceId: Any? = null,
        val HelpTopic: String? = null,
        val HelpTopicID: Int? = null,
        val LastModifiedDate: String? = null,
        val MemberId: Any? = null,
        val MemberName: Any? = null,
        val Mobile: Any? = null,
        val ModifiedBy: String? = null,
        val QueryDetails: String? = null,
        val QuerySource: String? = null,
        val QuerySummary: String? = null,
        val Rating: Any? = null,
        val SubHelpTopic: String? = null,
        val Comments: String? = null,
        val SubHelpTopicID: Int? = null,
        val TicketStatus: String? = null,
        val TotalRows: Int? = null):Serializable
/*Query Listing*/

/*Help topic listing*/
@JsonClass(generateAdapter = true)
data class GetHelpTopicRetrieveRequest(
        val ActionType: String? = null,
        val ActorId: String? = null,
        val IsActive: String? = null
)


@JsonClass(generateAdapter = true)
data class HelpTopicRetrieveRequest(
        val objHelpTopicRetrieveRequest: GetHelpTopicRetrieveRequest? = null
)

@JsonClass(generateAdapter = true)
data class GetHelpTopicResponse(
        val GetHelpTopicsResult: GetHelpTopicsResult
)


@JsonClass(generateAdapter = true)
data class GetHelpTopicsResult(
        val ReturnMessage: Any? = null,
        val ReturnValue: Int? = null,
        val TotalRecords: Int? = null,
        val objHelpTopicList: List<ObjHelpTopicList>? = null
)

@JsonClass(generateAdapter = true)
data class ObjHelpTopicList(
        val CreateDate: String? = null,
        val CustomerView: Boolean? = null,
        val EscalationInHours: Int? = null,
        val HelpTopicId: Int? = null,
        val HelpTopicName: String? = null,
        val IS_ACTIVE: Boolean? = null,
        val SubHelpTopicId: Int? = null,
        val SubHelpTopicName: String? = null,
        val Type: Int? = null
)
/*Help topic listing*/

/*Query Chat */
@JsonClass(generateAdapter = true)
data class QueryChatElementRequest(
        var ActionType: String? = null,
        var ActorId: String? = null,
        var CustomerTicketID: String? = null
)

@JsonClass(generateAdapter = true)
data class QueryChatElementResponse(
        var ReturnMessage: Any? = null,
        var ReturnValue: Int? = null,
        var TotalRecords: Int? = null,
        var objQueryCenterList: Any? = null,
        var objQueryResponseJsonList: List<ObjQueryResponseList>? = null
)

@JsonClass(generateAdapter = true)
data class ObjQueryResponseList(
        var Color: String? = null,
        var CreatedDate: String? = null,
        var JCreatedDate: String? = null,
        var ImageUrl: String? = null,
        var QueryResponseInfo: String? = null,
        var QueryStatus: String? = null,
        var RepliedBy: String? = null,
        var UserType: String? = null
)

/*Save New Ticket Request*/
@JsonClass(generateAdapter = true)
data class SaveNewTicketQueryRequest(
        var ActionType: String? = null,
        var ActorId: String? = null,
        var CustomerName: String? = null,
        var Email: String? = null,
        var HelpTopic: String? = null,
        var HelpTopicID: String? = null,
        var IsQueryFromMobile: String? = null,
        var LoyaltyID: String? = null,
        var Mobile: String? = null,
        var QueryDetails: String? = null,
        var QuerySummary: String? = null,
        var QuerySummaryMultipleQuery: String? = null,
        var SourceType: String? = null,
        var ImageUrl: String? = null,
        var FileType: String? = null
)


/*Save New Ticket Response*/
@JsonClass(generateAdapter = true)
data class SaveNewTicketQueryResponse(
        val ReturnMessage: String? = null,
        val ReturnValue: Int? = null,
        val TotalRecords: Int? = null,
        val objCustomerAllQueryJsonList: Any? = null
)


/*Post Chat reply request */
@JsonClass(generateAdapter = true)
data class PostChatStatusRequest(
        var ActionType: String? = null,
        var FileType: String? = null,
        var ImageUrl: String? = null,
        var ActorId: String? = null,
        var CustomerName: String? = null,
        var CustomerTicketID: String? = null,
        var Email: String? = null,
        var HelpTopic: String? = null,
        var HelpTopicID: String? = null,
        var IsQueryFromMobile: String? = null,
        var LoyaltyID: String? = null,
        var Mobile: String? = null,
        var QueryDetails: String? = null,
        var QueryStatus: String? = null,
        var QuerySummary: String? = null,
        var SourceType: String? = null
)

/*Post chat reply response*/
@JsonClass(generateAdapter = true)
data class PostChatStatusResponse (
     var ReturnMessage: String? = null,
     var ReturnValue: Int? = null,
     var TotalRecords: Int? = null,
     var objCustomerAllQueryJsonList: Any? = null
)


@JsonClass(generateAdapter = true)
data class FeedbackRequest(
    val ActionType: String? = null,
    val ActorId: String? = null,
    val Comments: String? =null,
    val CustomerTicketId: String? = null,
    val Rating: String? = null
)

@JsonClass(generateAdapter = true)
data class FeedbackResponse(
    val ReturnMessage: String? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null
)