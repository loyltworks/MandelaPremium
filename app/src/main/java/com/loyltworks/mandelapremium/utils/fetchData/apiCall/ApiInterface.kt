package com.loyltworks.mandelapremium.utils.fetchData.apiCall

import com.loyltworks.mandelapremium.model.AttributeRequest
import com.loyltworks.mandelapremium.model.AttributeResponse
import com.loyltworks.mandelapremium.model.CancelRequest
import com.loyltworks.mandelapremium.model.CancelResponse
import com.loyltworks.mandelapremium.model.CityRequest
import com.loyltworks.mandelapremium.model.CityResponse
import com.loyltworks.mandelapremium.model.CountryDetailResponse
import com.loyltworks.mandelapremium.model.CountryDetailsRequest
import com.loyltworks.mandelapremium.model.DashboardCustomerRequest
import com.loyltworks.mandelapremium.model.DashboardCustomerResponse
import com.loyltworks.mandelapremium.model.DashboardRequest
import com.loyltworks.mandelapremium.model.DashboardResponse
import com.loyltworks.mandelapremium.model.DeleteAccountRequest
import com.loyltworks.mandelapremium.model.DeleteAccountResponse
import com.loyltworks.mandelapremium.model.EmailCheckRequest
import com.loyltworks.mandelapremium.model.EmailCheckResponse
import com.loyltworks.mandelapremium.model.FeedbackRequest
import com.loyltworks.mandelapremium.model.FeedbackResponse
import com.loyltworks.mandelapremium.model.ForgotPasswordRequest
import com.loyltworks.mandelapremium.model.ForgotPasswordResponse
import com.loyltworks.mandelapremium.model.GetAlbumsWithImagesRequest
import com.loyltworks.mandelapremium.model.GetAlbumsWithImagesResponse
import com.loyltworks.mandelapremium.model.GetBuyGiftRequest
import com.loyltworks.mandelapremium.model.GetBuyGiftResponse
import com.loyltworks.mandelapremium.model.GetCashBackRequest
import com.loyltworks.mandelapremium.model.GetCashBackResponse
import com.loyltworks.mandelapremium.model.GetGiftCardRequest
import com.loyltworks.mandelapremium.model.GetGiftCardResponse
import com.loyltworks.mandelapremium.model.GetHelpTopicResponse
import com.loyltworks.mandelapremium.model.GetPromotionResponse
import com.loyltworks.mandelapremium.model.GetReceiverIDRequest
import com.loyltworks.mandelapremium.model.GetReceiverIDResponse
import com.loyltworks.mandelapremium.model.GetSaveGiftCardRequest
import com.loyltworks.mandelapremium.model.GetSaveGiftCardResponse
import com.loyltworks.mandelapremium.model.GetWhatsNewRequest
import com.loyltworks.mandelapremium.model.HelpTopicRetrieveRequest
import com.loyltworks.mandelapremium.model.HistoryNotificationDetailsRequest
import com.loyltworks.mandelapremium.model.HistoryNotificationRequest
import com.loyltworks.mandelapremium.model.HistoryNotificationResponse
import com.loyltworks.mandelapremium.model.LoginRequest
import com.loyltworks.mandelapremium.model.LoginResponse
import com.loyltworks.mandelapremium.model.MyVoucherGiftCardSubmitRequest
import com.loyltworks.mandelapremium.model.MyVoucherGiftCardSubmitResponse
import com.loyltworks.mandelapremium.model.PostChatStatusRequest
import com.loyltworks.mandelapremium.model.PostChatStatusResponse
import com.loyltworks.mandelapremium.model.QueryChatElementRequest
import com.loyltworks.mandelapremium.model.QueryChatElementResponse
import com.loyltworks.mandelapremium.model.QueryListingRequest
import com.loyltworks.mandelapremium.model.QueryListingResponse
import com.loyltworks.mandelapremium.model.RedemptionRequest
import com.loyltworks.mandelapremium.model.RedemptionResponse
import com.loyltworks.mandelapremium.model.RegistrationRequest
import com.loyltworks.mandelapremium.model.RegistrationResponse
import com.loyltworks.mandelapremium.model.SaveNewTicketQueryRequest
import com.loyltworks.mandelapremium.model.SaveNewTicketQueryResponse
import com.loyltworks.mandelapremium.model.ScanRequest
import com.loyltworks.mandelapremium.model.ScanResponse
import com.loyltworks.mandelapremium.model.TransactionHistoryRequest
import com.loyltworks.mandelapremium.model.TransactionHistoryResponse
import com.loyltworks.mandelapremium.model.UpdateProfileImageRequest
import com.loyltworks.mandelapremium.model.UpdateProfileImageResponse
import com.loyltworks.mandelapremium.model.UpdateProfileRequest
import com.loyltworks.mandelapremium.model.UpdateProfileResponse
import com.loyltworks.mandelapremium.model.ValidateScratchCodeRequest
import com.loyltworks.mandelapremium.model.ValidateScratchCodeResponse
import com.loyltworks.mandelapremium.model.WishPointRequest
import com.loyltworks.mandelapremium.model.WishPointsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

// Live
//const val mobileApi: String = "MobileApp/MobileApi.svc/json/"
//const val scanCodeUpload_mobileApi: String = "MobileApp/CustomizedMobileApp/CustomizedMobileApi.svc/json/"

// Demo
const val mobileApi: String = "MobileApp/MobileApi.svc/JSON/"
const val scanCodeUpload_mobileApi: String = "MobileApp/MobileApi.svc/JSON/"

interface ApiInterface {

    // Login Check api call
    @POST("${mobileApi}CheckIsAuthenticatedMobileApp")
    fun fetchLoginDataAsync(@Body loginRequest: LoginRequest): Deferred<Response<LoginResponse>>

    // save scan code  api call
    @POST("${scanCodeUpload_mobileApi}SaveQRCodeDetailsBulk")
    fun saveScanCodeResponseAsync(@Body scanRequest: ScanRequest): Deferred<Response<ScanResponse>>

    //  scan code check api call
    @POST("${scanCodeUpload_mobileApi}ValidateScratchCode")
    fun getValidateScratchCodeRequest(@Body validateScratchCodeRequest: ValidateScratchCodeRequest): Deferred<Response<ValidateScratchCodeResponse>>


    // DashBoard api call
    @POST("${mobileApi}GetMerchantEmailDetailsMobileApp")
    fun fetchDashBoardAsync(@Body dashboardRequest: DashboardRequest): Deferred<Response<DashboardResponse>>

    // get Dashboard data 2 api call
    @POST("${mobileApi}getCustomerDashboardDetailsMobileApp")
    fun fetchDashBoardDetailsAsync(@Body dashboardCustomerRequest: DashboardCustomerRequest): Deferred<Response<DashboardCustomerResponse>>


/*
    // OTP api call
    @POST("${mobileApi}SaveAndGetOTPDetails")
    fun getOTPAsync(@Body otpRequest: OTPRequest): Deferred<Response<OTPResponse>>


     // Email Domain check api call
    @POST("${mobileApi}GetEmailDomain")
    fun getEmailDomainCheckAsync(@Body emailDomainCheckRequest: EmailDomainCheckRequest): Deferred<Response<EmailDomainCheckResponse>>
*/


    // Email check api call
    @POST("${mobileApi}CheckUserNameExists")
    fun getEmailExistAsync(@Body emailCheckRequest: EmailCheckRequest): Deferred<Response<EmailCheckResponse>>


    // get attribute api call
    @POST("${mobileApi}GetAttributeDetails")
    fun getAttributeDetailsAsync(@Body attributeRequest: AttributeRequest): Deferred<Response<AttributeResponse>>


    //Query listing api call
    @POST("${mobileApi}SaveCustomerQueryTicket")
    fun qetQueryListingQueryResponse(@Body queryListingRequest: QueryListingRequest): Deferred<Response<QueryListingResponse>>

    //Help Topic listing api call
    @POST("${mobileApi}GetHelpTopics")
    fun getHelpTopicHeadersResponse(@Body helpTopicRetrieveRequest: HelpTopicRetrieveRequest): Deferred<Response<GetHelpTopicResponse>>

    @POST("${mobileApi}SaveCustomerQueryTicket")
    fun getSaveNewTicketQueryResponse(@Body saveNewTicketQueryRequest: SaveNewTicketQueryRequest?): Deferred<Response<SaveNewTicketQueryResponse>>

    //QueryChat api call
    @POST("${mobileApi}GetQueryResponseInformation")
    fun getQueryChatElementResponse(@Body queryChatElementRequest: QueryChatElementRequest?): Deferred<Response<QueryChatElementResponse>>

    //PostReplyQueryChatStatus api call
    @POST("${mobileApi}SaveCustomerQueryTicket")
    fun getPostReplyHelpTopicStatus(@Body queryChatElementRequest: PostChatStatusRequest?): Deferred<Response<PostChatStatusResponse>>


   //Feedback api call
    @POST("${mobileApi}SaveCustomerTicketFeedback")
    fun getFeedbackresponse(@Body feedbackRequest: FeedbackRequest?): Deferred<Response<FeedbackResponse>>


/*    @POST("${mobileApi}GetPromotionDetailsMobileApp")
    fun getPromotionDetailsMobileApp(@Body getPromotionRequest: GetWhatsNewRequest?):  Deferred<Response<GetPromotionResponse>>

    @POST("${mobileApi}GetCustomerPromotionDetailsByPromotionID")
    fun getCustomerPromotionDetailsByPromotionID(@Body getPromotionDetailsRequest: GetPromotionDetailsRequest?):  Deferred<Response<GetPromotionResponse>>*/


    @POST("${mobileApi}GetPushHistoryDetails")
    fun getHistoryNotifiation(@Body historyNotificationRequest: HistoryNotificationRequest?): Deferred<Response<HistoryNotificationResponse>>

    @POST("${mobileApi}GetPushHistoryDetails")
    fun getHistoryNotifiationDetailByID(@Body historyNotificationDetailByIDRequest: HistoryNotificationDetailsRequest?): Deferred<Response<HistoryNotificationResponse>>


    //Forgot Password
    @POST("${mobileApi}forgotPasswordMobileApp")
    open fun getForgotPasswordResponse(@Body forgotPasswordRequest: ForgotPasswordRequest?): Deferred<Response<ForgotPasswordResponse>>

    //Transaction history listing api call
    @POST("${mobileApi}GetRewardTransactionDetailsMobileApp")
    fun getTransactionHistoryResponse(@Body transactionHistoryRequest: TransactionHistoryRequest?): Deferred<Response<TransactionHistoryResponse>>


    //Location Wise point call
    @POST("${mobileApi}GetBehaviourWiseEarning")
    fun getWisePointsResponse(@Body wishPointRequest: WishPointRequest?): Deferred<Response<WishPointsResponse>>

    //Transaction history listing api call
    @POST("${mobileApi}GetCustomerRedemptionDetailsMobileApp")
    fun getRedemptionResponse(@Body redemptionRequest: RedemptionRequest?): Deferred<Response<RedemptionResponse>>

    /*Promotion Listing */
    @POST("${mobileApi}GetPromotionDetailsMobileApp")
    fun getPromotionDetailsMobileApp(@Body getPromotionRequest: GetWhatsNewRequest?): Deferred<Response<GetPromotionResponse>>

    /*BuyGift Listing */
    @POST("${mobileApi}GetMerchantDetailsForCustomerEvoucher")
    fun getBuyGiftMerchantDetailsForCustomerEvoucher(@Body getBuyGiftRequest: GetBuyGiftRequest?): Deferred<Response<GetBuyGiftResponse>>

    /*GiftCard Listing */
    @POST("${mobileApi}GetMerchantDetailsForCustomerEvoucher")
    fun getGiftCardMerchantDetailsForCustomerEvoucher(@Body getGiftCardRequest: GetGiftCardRequest?): Deferred<Response<GetGiftCardResponse>>


    /*AlbumWithImage Listing */
    @POST("${mobileApi}GetAlbumsWithImages")
    fun getAlbumsWithImagesMerchantDetailsForCustomerEvoucher(@Body getAlbumWithImagesRequest: GetAlbumsWithImagesRequest?): Deferred<Response<GetAlbumsWithImagesResponse>>


    /*AlbumImages By Album ID Listing */
    @POST("${mobileApi}GetAlbumImagesById")
    fun getAlbumsImagesByAlbumsID(@Body getAlbumWithImagesRequest: GetAlbumsWithImagesRequest?): Deferred<Response<GetAlbumsWithImagesResponse>>


    /*Receiver ID*/
    @POST("${mobileApi}getGiftCardIssueMobileApp")
    fun getReceiverID(@Body getReceiverIDRequest: GetReceiverIDRequest?): Deferred<Response<GetReceiverIDResponse>>

    /* MyVoucher GiftCard Sumbit*/
    @POST("${mobileApi}UpdateGiftCardTransactionMobileApp")
    fun getMyVoucherGiftCardSubmit(@Body myVoucherGiftCardSubmitRequest: MyVoucherGiftCardSubmitRequest?): Deferred<Response<MyVoucherGiftCardSubmitResponse>>

    /*BuyGift CaseBackValue  */
    @POST("${mobileApi}GetCashBackDetails")
    fun getBuyGiftCaseBackValue(@Body getGetCasebackValue: GetCashBackRequest?): Deferred<Response<GetCashBackResponse>>


    /*BuyGift BuyNow CaseBackValue  */
    @POST("${mobileApi}SaveGiftCardIssueDetails")
    fun getBuyGiftBuyNow(@Body getsaveGiftcardRequest: GetSaveGiftCardRequest?): Deferred<Response<GetSaveGiftCardResponse>>

    // Update driver delivery details api call api call
    @POST("${mobileApi}GetCustomerDetailsMobileApp")
    fun getCustomerDetailsByDeviceIDAsync(@Body registrationRequest: RegistrationRequest): Deferred<Response<RegistrationResponse>>

    // get country data api call
    @POST("${mobileApi}GetCompleteCountryDetails")
    fun getCountryDetailsAsync(@Body countryDetailsRequest: CountryDetailsRequest): Deferred<Response<CountryDetailResponse>>

    // get profession data api call
    @POST("${mobileApi}GetAttributeDetailsMobileApp")
    fun getProfessionalDetail(@Body attributeRequest: AttributeRequest): Deferred<Response<AttributeResponse>>

    // get age group data api call
    @POST("${mobileApi}GetAttributeDetailsMobileApp")
    fun getAgeGroupDetail(@Body attributeRequest: AttributeRequest): Deferred<Response<AttributeResponse>>

    // get city data api call
    @POST("${mobileApi}GetCitiesBasedOnCountry")
    fun getCitiesBasedOnCountry(@Body cityRequest: CityRequest): Deferred<Response<CityResponse>>

 // get city data api call
    @POST("${mobileApi}SaveCustomerDetails")
    fun getUpdateProfileRequest(@Body updateProfileRequest: UpdateProfileRequest): Deferred<Response<UpdateProfileResponse>>

    /*updateProfileImage */
    @POST("${mobileApi}UpdateCustomerProfileMobileApp")
    fun getupdateProfileImage(@Body updateProfileImageRequest: UpdateProfileImageRequest?):  Deferred<Response<UpdateProfileImageResponse>>

    //Request for account deletion
    @POST("${mobileApi}CustomerAccountDelete")
    fun requestAccountDeletion(@Body deleteAccountRequest: DeleteAccountRequest): Deferred<Response<DeleteAccountResponse>>


    //Cancel account deletion
    @POST("${mobileApi}CancelationAccountDeleteRequest")
    fun cancelAccountDeletion(@Body cancelRequest: CancelRequest): Deferred<Response<CancelResponse>>

}