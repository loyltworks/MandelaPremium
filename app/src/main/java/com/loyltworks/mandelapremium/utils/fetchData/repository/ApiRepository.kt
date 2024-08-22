package com.loyltworks.mandelapremium.utils.fetchData.repository


import com.loyltworks.mandelapremium.model.*
import com.loyltworks.mandelapremium.utils.fetchData.apiCall.ApiInterface
import retrofit2.await


class ApiRepository(private val apiInterface: ApiInterface) : BaseRepository() {

    // Login Check api call
    suspend fun getLoginData(loginRequest: LoginRequest): LoginResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.fetchLoginDataAsync(loginRequest).await() },
            error = "Error fetching Login Details"
            //convert to mutable list
        )
    }

    // DashBoard api call
    suspend fun getDashBoardData(dashboardRequest: DashboardRequest): DashboardResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.fetchDashBoardAsync(dashboardRequest).await() },
            error = "Error fetching Dashboard Details"
            //convert to mutable list
        )
    }

    // DashBoard 2 api call
    suspend fun getDashBoardData2(dashboardCustomerRequest: DashboardCustomerRequest): DashboardCustomerResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.fetchDashBoardDetailsAsync(dashboardCustomerRequest).await() },
            error = "Error fetching Dashboard Details 2"
            //convert to mutable list
        )
    }


/*
    // OTP api call
    suspend fun getOTP(otpRequest: OTPRequest): OTPResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getOTPAsync(otpRequest).await() },
            error = "Error OTP trigger"
            //convert to mutable list
        )
    }
*/

    // email api call
    suspend fun getEmailExist(emailCheckRequest: EmailCheckRequest): EmailCheckResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getEmailExistAsync(emailCheckRequest).await() },
            error = "Error email check"
            //convert to mutable list
        )
    }

   /*

    // email domain api call
    suspend fun getEmailDomain(emailDomainCheckRequest: EmailDomainCheckRequest): EmailDomainCheckResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getEmailDomainCheckAsync(emailDomainCheckRequest).await() },
            error = "Error email domain check"
            //convert to mutable list
        )
    }*/



    // get attributes data
    suspend fun getAttributeDetails(attributeRequest: AttributeRequest): AttributeResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getAttributeDetailsAsync(attributeRequest).await() },
            error = "Error Customer Type"
            //convert to mutable list
        )
    }

  /*  // get Country data
    suspend fun getCountryDetails(countryDetailsRequest: CountryDetailsRequest): CountryDetailResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getCountryDetailsAsync(countryDetailsRequest).await() },
            error = "Error Country details retrieve"
            //convert to mutable list
        )
    }*/

    // Scan code save api call
    suspend fun saveScanCodeData(scanRequest: ScanRequest): ScanResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.saveScanCodeResponseAsync(scanRequest).await() },
            error = "Error scan code save"
            //convert to mutable list
        )
    }

    suspend fun validateScratchCodeRequest(validateScratchCodeRequest: ValidateScratchCodeRequest): ValidateScratchCodeResponse? {
        return safeApiCall(
            call = {
                apiInterface.getValidateScratchCodeRequest(validateScratchCodeRequest).await()
            },
            error = "Error ValidateScratchCodeRequest Trigger"
        )
    }


  //QueryList for support api call
    suspend fun getQueryListData(queryListingRequest: QueryListingRequest): QueryListingResponse? {

        return safeApiCall(
            call = { apiInterface.qetQueryListingQueryResponse(queryListingRequest).await() },
            error = "Error Query List trigger"
        )

    }

//    Help topic listing
    suspend fun getHelpTopic(topicListRequest: HelpTopicRetrieveRequest): GetHelpTopicResponse? {
        return safeApiCall(
            call = { apiInterface.getHelpTopicHeadersResponse(topicListRequest).await() },
            error = "Error help topic list trigger"
        )
    }

//    QueryChat for specific queryID
   suspend fun getChatQuery(chatQuery: QueryChatElementRequest): QueryChatElementResponse? {
        return safeApiCall(
                call = { apiInterface.getQueryChatElementResponse(chatQuery).await()},
                error = "Error Query Chat trigger"
        )
    }

//     Transaction History Listing
    suspend fun getTansactionHistoryList(transactionHistoryRequest: TransactionHistoryRequest): TransactionHistoryResponse? {
        return safeApiCall(
                call = {
                    apiInterface.getTransactionHistoryResponse(transactionHistoryRequest).await()
                },
                error = "Error Transaction History Trigger"
        )
    }


    //     Location Wise Point balance Listing
    suspend fun getLocationWiseList(wishPointRequest: WishPointRequest): WishPointsResponse? {
        return safeApiCall(
            call = {
                apiInterface.getWisePointsResponse(wishPointRequest).await()
            },
            error = "Error Wise Points Trigger"
        )
    }


//     Redemption Listing
    suspend fun getRedemptionResponse(redemptionRequest: RedemptionRequest): RedemptionResponse? {
        return safeApiCall(
                call = {
                    apiInterface.getRedemptionResponse(redemptionRequest).await()
                },
                error = "Error Redemption"
        )
    }

    /*WhatsNew Api Call*/
    suspend fun getPromotionList(whatsNewRequest: GetWhatsNewRequest): GetPromotionResponse? {
        return safeApiCall(
            call = {
                apiInterface.getPromotionDetailsMobileApp(whatsNewRequest).await()
            },
            error = "Error  Promotion Trigger"
        )
    }

    /* BuyGift Api Call */
    suspend fun getBuyGiftList(getBuyGiftRequest: GetBuyGiftRequest): GetBuyGiftResponse? {
        return safeApiCall(
            call = {
                apiInterface.getBuyGiftMerchantDetailsForCustomerEvoucher(getBuyGiftRequest).await()
            },
            error = "Error  Buy Gift Trigger"
        )
    }


  /* BuyGift Api Call */
    suspend fun getGiftCardList(getBuyGiftRequest: GetGiftCardRequest): GetGiftCardResponse? {
        return safeApiCall(
            call = {
                apiInterface.getGiftCardMerchantDetailsForCustomerEvoucher(getBuyGiftRequest).await()
            },
            error = "Error Gift Card Trigger"
        )
    }

    /* AlbumsWithImages Api Call */
    suspend fun getAlbumsWithImageList(getAlbumWithImagesRequest: GetAlbumsWithImagesRequest): GetAlbumsWithImagesResponse? {
        return safeApiCall(
            call = {
                apiInterface.getAlbumsWithImagesMerchantDetailsForCustomerEvoucher(getAlbumWithImagesRequest).await()
            },
            error = "Error AlbumsWithImage Trigger"
        )
    }


 /* AlbumsImages By Albums ID Api Call */
    suspend fun getAlbumsImageByIDList(getAlbumWithImagesRequest: GetAlbumsWithImagesRequest): GetAlbumsWithImagesResponse? {
        return safeApiCall(
            call = {
                apiInterface.getAlbumsImagesByAlbumsID(getAlbumWithImagesRequest).await()
            },
            error = "Error AlbumsWithImage Trigger"
        )
    }

    /* Receiver ID Api Call */
    suspend fun getReceiverIdName(getReceiverIDRequest: GetReceiverIDRequest): GetReceiverIDResponse? {
        return safeApiCall(
            call = {
                apiInterface.getReceiverID(getReceiverIDRequest).await()
            },
            error = "Error Receiver ID Trigger"
        )
    }


    /* MyVoucher GiftCard Submit Api Call */
    suspend fun getMyVoucherGiftCardSubmit(myVouhcerGiftCardSubmitRequest: MyVoucherGiftCardSubmitRequest): MyVoucherGiftCardSubmitResponse? {
        return safeApiCall(
            call = {
                apiInterface.getMyVoucherGiftCardSubmit(myVouhcerGiftCardSubmitRequest).await()
            },
            error = "Error Receiver ID Trigger"
        )
    }


    /* BuyGift CaseBack value Api Call */
    suspend fun getBuyGiftCaseBackList(getbuyGiftCasebackValue: GetCashBackRequest): GetCashBackResponse? {
        return safeApiCall(
            call = {
                apiInterface.getBuyGiftCaseBackValue(getbuyGiftCasebackValue).await()
            },
            error = "Error  Buy Gift CaseBack Back Trigger"
        )
    }


    /* BuyGift BuyNow Api Call */
    suspend fun getBuyGiftBuyNow(getbuyGiftCardRequest: GetSaveGiftCardRequest): GetSaveGiftCardResponse? {
        return safeApiCall(
            call = {
                apiInterface.getBuyGiftBuyNow(getbuyGiftCardRequest).await()
            },
            error = "Error  Buy Gift Trigger"
        )
    }

   /*
   suspend fun getPromotionDetail(promotionDetailsRequest: GetPromotionDetailsRequest): GetPromotionResponse? {
        return safeApiCall(
            call = {
                apiInterface.getCustomerPromotionDetailsByPromotionID(promotionDetailsRequest).await()
            },
            error = "Error  Promotion Detail Trigger"
        )
    }
    */

    suspend fun getSaveNewTicketQuery(saveNewTicketQueryRequest: SaveNewTicketQueryRequest): SaveNewTicketQueryResponse? {
        return safeApiCall(
                call = {
                    apiInterface.getSaveNewTicketQueryResponse(saveNewTicketQueryRequest).await()
                },
                error = "Error  Promotion Detail Trigger"
        )
    }

    suspend fun getProfile(myProfileRequest: RegistrationRequest): RegistrationResponse? {
        return safeApiCall(
                call = {
                    apiInterface.getCustomerDetailsByDeviceIDAsync(myProfileRequest).await()
                },
                error = "Error  Profile Trigger"
        )
    }


    suspend fun getPostReply(postChatStatusRequest: PostChatStatusRequest): PostChatStatusResponse? {
        return safeApiCall(
                call = {
                    apiInterface.getPostReplyHelpTopicStatus(postChatStatusRequest).await()
                },
                error = "Error PostReplyHelpTopicStatus Trigger"
        )
    }

    suspend fun getFeedbackResponse(feedbackRequest: FeedbackRequest): FeedbackResponse? {
        return safeApiCall(
                call = {
                    apiInterface.getFeedbackresponse(feedbackRequest).await()
                },
                error = "Error FeedbackResponse Trigger"
        )
    }

    suspend fun getHistoryNotificationList(historyNotificationRequest: HistoryNotificationRequest): HistoryNotificationResponse? {
        return safeApiCall(
            call = {
                apiInterface.getHistoryNotifiation(historyNotificationRequest).await()
            },
            error = "Error History Notification Trigger"
        )
    }

    suspend fun getHistoryNotificationDetailByIdList(historyNotificationDetailsRequest: HistoryNotificationDetailsRequest): HistoryNotificationResponse? {
        return safeApiCall(
            call = {
                apiInterface.getHistoryNotifiationDetailByID(historyNotificationDetailsRequest).await()
            },
            error = "Error History Notification Detail By ID Trigger"
        )
    }

    suspend fun getForgotPasswordData(forgotPasswordRequest: ForgotPasswordRequest): ForgotPasswordResponse? {
        return safeApiCall(
                call = {
                    apiInterface.getForgotPasswordResponse(forgotPasswordRequest).await()
                },
                error = "Error Forgot Password Trigger"
        )
    }

    // get Country data
    suspend fun getCountryDetails(countryDetailsRequest: CountryDetailsRequest): CountryDetailResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getCountryDetailsAsync(countryDetailsRequest).await() },
            error = "Error Country details retrieve"
            //convert to mutable list
        )
    }

    suspend fun getProfessionList(attributeRequest: AttributeRequest): AttributeResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getProfessionalDetail(attributeRequest).await() },
            error = "Error profession Request retrieve"
            //convert to mutable list
        )
    }

    suspend fun getAgeGroupList(attributeRequest: AttributeRequest): AttributeResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getAgeGroupDetail(attributeRequest).await() },
            error = "Error age-group Request retrieve"
            //convert to mutable list
        )
    }

    suspend fun getCityList(cityRequest: CityRequest): CityResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getCitiesBasedOnCountry(cityRequest).await() },
            error = "Error cityRequest retrieve"
            //convert to mutable list
        )
    }

    suspend fun getProfileResponse(updateProfileRequest: UpdateProfileRequest): UpdateProfileResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getUpdateProfileRequest(updateProfileRequest).await() },
            error = "Error getUpdateProfileRequest retrieve"
            //convert to mutable list
        )
    }


    /*Update Profile Image callback*/
    suspend fun getUpdateProfileImage(updateProfileImageRequest: UpdateProfileImageRequest): UpdateProfileImageResponse? {
        return safeApiCall(
            call = {
                apiInterface.getupdateProfileImage(updateProfileImageRequest).await()
            },
            error = "Error updateProfileImageRequest Trigger"
        )
    }

}
