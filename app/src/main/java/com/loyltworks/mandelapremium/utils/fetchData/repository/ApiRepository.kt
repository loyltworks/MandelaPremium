package com.loyltworks.mandelapremium.utils.fetchData.repository


import com.loyltworks.mandelapremium.model.*
import com.loyltworks.mandelapremium.utils.fetchData.apiCall.ApiInterface
import retrofit2.await


class ApiRepository(private val apiInterface: ApiInterface) : BaseRepository() {

    // Login Check api call
    suspend fun getLoginData(loginRequest: LoginRequest): LoginResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.fetchLoginDataAsync(loginRequest).await() }
        )
    }

    // DashBoard api call
    suspend fun getDashBoardData(dashboardRequest: DashboardRequest): DashboardResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.fetchDashBoardAsync(dashboardRequest).await() }
        )
    }

    // DashBoard 2 api call
    suspend fun getDashBoardData2(dashboardCustomerRequest: DashboardCustomerRequest): DashboardCustomerResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.fetchDashBoardDetailsAsync(dashboardCustomerRequest).await() }
        )
    }


    /*
        // OTP api call
        suspend fun getOTP(otpRequest: OTPRequest): OTPResponse? {
            return safeApiCall(
                //await the result of deferred type
                call = { apiInterface.getOTPAsync(otpRequest).await() }
            )
        }
    */

    // email api call
    suspend fun getEmailExist(emailCheckRequest: EmailCheckRequest): EmailCheckResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getEmailExistAsync(emailCheckRequest).await() }
        )
    }

    /*

     // email domain api call
     suspend fun getEmailDomain(emailDomainCheckRequest: EmailDomainCheckRequest): EmailDomainCheckResponse? {
         return safeApiCall(
             //await the result of deferred type
             call = { apiInterface.getEmailDomainCheckAsync(emailDomainCheckRequest).await() }
         )
     }*/


    // get attributes data
    suspend fun getAttributeDetails(attributeRequest: AttributeRequest): AttributeResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getAttributeDetailsAsync(attributeRequest).await() }
        )
    }

    /*  // get Country data
      suspend fun getCountryDetails(countryDetailsRequest: CountryDetailsRequest): CountryDetailResponse? {
          return safeApiCall(
              //await the result of deferred type
              call = { apiInterface.getCountryDetailsAsync(countryDetailsRequest).await() }
          )
      }*/

    // Scan code save api call
    suspend fun saveScanCodeData(scanRequest: ScanRequest): ScanResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.saveScanCodeResponseAsync(scanRequest).await() }
        )
    }

    suspend fun validateScratchCodeRequest(validateScratchCodeRequest: ValidateScratchCodeRequest): ValidateScratchCodeResponse? {
        return safeApiCall(
            call = {
                apiInterface.getValidateScratchCodeRequest(validateScratchCodeRequest).await()
            }
        )
    }


    //QueryList for support api call
    suspend fun getQueryListData(queryListingRequest: QueryListingRequest): QueryListingResponse? {

        return safeApiCall(
            call = { apiInterface.qetQueryListingQueryResponse(queryListingRequest).await() }
        )

    }

    //    Help topic listing
    suspend fun getHelpTopic(topicListRequest: HelpTopicRetrieveRequest): GetHelpTopicResponse? {
        return safeApiCall(
            call = { apiInterface.getHelpTopicHeadersResponse(topicListRequest).await() }
        )
    }

    //    QueryChat for specific queryID
    suspend fun getChatQuery(chatQuery: QueryChatElementRequest): QueryChatElementResponse? {
        return safeApiCall(
            call = { apiInterface.getQueryChatElementResponse(chatQuery).await() }
        )
    }

    //     Transaction History Listing
    suspend fun getTansactionHistoryList(transactionHistoryRequest: TransactionHistoryRequest): TransactionHistoryResponse? {
        return safeApiCall(
            call = {
                apiInterface.getTransactionHistoryResponse(transactionHistoryRequest).await()
            }
        )
    }


    //     Location Wise Point balance Listing
    suspend fun getLocationWiseList(wishPointRequest: WishPointRequest): WishPointsResponse? {
        return safeApiCall(
            call = {
                apiInterface.getWisePointsResponse(wishPointRequest).await()
            }
        )
    }


    //     Redemption Listing
    suspend fun getRedemptionResponse(redemptionRequest: RedemptionRequest): RedemptionResponse? {
        return safeApiCall(
            call = {
                apiInterface.getRedemptionResponse(redemptionRequest).await()
            }
        )
    }

    /*WhatsNew Api Call*/
    suspend fun getPromotionList(whatsNewRequest: GetWhatsNewRequest): GetPromotionResponse? {
        return safeApiCall(
            call = {
                apiInterface.getPromotionDetailsMobileApp(whatsNewRequest).await()
            }
        )
    }

    /* BuyGift Api Call */
    suspend fun getBuyGiftList(getBuyGiftRequest: GetBuyGiftRequest): GetBuyGiftResponse? {
        return safeApiCall(
            call = {
                apiInterface.getBuyGiftMerchantDetailsForCustomerEvoucher(getBuyGiftRequest).await()
            }
        )
    }


    /* BuyGift Api Call */
    suspend fun getGiftCardList(getBuyGiftRequest: GetGiftCardRequest): GetGiftCardResponse? {
        return safeApiCall(
            call = {
                apiInterface.getGiftCardMerchantDetailsForCustomerEvoucher(getBuyGiftRequest)
                    .await()
            }
        )
    }

    /* AlbumsWithImages Api Call */
    suspend fun getAlbumsWithImageList(getAlbumWithImagesRequest: GetAlbumsWithImagesRequest): GetAlbumsWithImagesResponse? {
        return safeApiCall(
            call = {
                apiInterface.getAlbumsWithImagesMerchantDetailsForCustomerEvoucher(
                    getAlbumWithImagesRequest
                ).await()
            }
        )
    }


    /* AlbumsImages By Albums ID Api Call */
    suspend fun getAlbumsImageByIDList(getAlbumWithImagesRequest: GetAlbumsWithImagesRequest): GetAlbumsWithImagesResponse? {
        return safeApiCall(
            call = {
                apiInterface.getAlbumsImagesByAlbumsID(getAlbumWithImagesRequest).await()
            }
        )
    }

    /* Receiver ID Api Call */
    suspend fun getReceiverIdName(getReceiverIDRequest: GetReceiverIDRequest): GetReceiverIDResponse? {
        return safeApiCall(
            call = {
                apiInterface.getReceiverID(getReceiverIDRequest).await()
            }
        )
    }


    /* MyVoucher GiftCard Submit Api Call */
    suspend fun getMyVoucherGiftCardSubmit(myVouhcerGiftCardSubmitRequest: MyVoucherGiftCardSubmitRequest): MyVoucherGiftCardSubmitResponse? {
        return safeApiCall(
            call = {
                apiInterface.getMyVoucherGiftCardSubmit(myVouhcerGiftCardSubmitRequest).await()
            }
        )
    }


    /* BuyGift CaseBack value Api Call */
    suspend fun getBuyGiftCaseBackList(getbuyGiftCasebackValue: GetCashBackRequest): GetCashBackResponse? {
        return safeApiCall(
            call = {
                apiInterface.getBuyGiftCaseBackValue(getbuyGiftCasebackValue).await()
            }
        )
    }


    /* BuyGift BuyNow Api Call */
    suspend fun getBuyGiftBuyNow(getbuyGiftCardRequest: GetSaveGiftCardRequest): GetSaveGiftCardResponse? {
        return safeApiCall(
            call = {
                apiInterface.getBuyGiftBuyNow(getbuyGiftCardRequest).await()
            }
        )
    }

    /*
    suspend fun getPromotionDetail(promotionDetailsRequest: GetPromotionDetailsRequest): GetPromotionResponse? {
         return safeApiCall(
             call = {
                 apiInterface.getCustomerPromotionDetailsByPromotionID(promotionDetailsRequest).await()
             }
         )
     }
     */

    suspend fun getSaveNewTicketQuery(saveNewTicketQueryRequest: SaveNewTicketQueryRequest): SaveNewTicketQueryResponse? {
        return safeApiCall(
            call = {
                apiInterface.getSaveNewTicketQueryResponse(saveNewTicketQueryRequest).await()
            }
        )
    }

    suspend fun getProfile(myProfileRequest: RegistrationRequest): RegistrationResponse? {
        return safeApiCall(
            call = {
                apiInterface.getCustomerDetailsByDeviceIDAsync(myProfileRequest).await()
            }
        )
    }


    suspend fun getPostReply(postChatStatusRequest: PostChatStatusRequest): PostChatStatusResponse? {
        return safeApiCall(
            call = {
                apiInterface.getPostReplyHelpTopicStatus(postChatStatusRequest).await()
            }
        )
    }

    suspend fun getFeedbackResponse(feedbackRequest: FeedbackRequest): FeedbackResponse? {
        return safeApiCall(
            call = {
                apiInterface.getFeedbackresponse(feedbackRequest).await()
            }
        )
    }

    suspend fun getHistoryNotificationList(historyNotificationRequest: HistoryNotificationRequest): HistoryNotificationResponse? {
        return safeApiCall(
            call = {
                apiInterface.getHistoryNotifiation(historyNotificationRequest).await()
            }
        )
    }

    suspend fun getHistoryNotificationDetailByIdList(historyNotificationDetailsRequest: HistoryNotificationDetailsRequest): HistoryNotificationResponse? {
        return safeApiCall(
            call = {
                apiInterface.getHistoryNotifiationDetailByID(historyNotificationDetailsRequest)
                    .await()
            }
        )
    }

    suspend fun getForgotPasswordData(forgotPasswordRequest: ForgotPasswordRequest): ForgotPasswordResponse? {
        return safeApiCall(
            call = {
                apiInterface.getForgotPasswordResponse(forgotPasswordRequest).await()
            }
        )
    }

    // get Country data
    suspend fun getCountryDetails(countryDetailsRequest: CountryDetailsRequest): CountryDetailResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getCountryDetailsAsync(countryDetailsRequest).await() }
        )
    }

    suspend fun getProfessionList(attributeRequest: AttributeRequest): AttributeResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getProfessionalDetail(attributeRequest).await() }
        )
    }

    suspend fun getAgeGroupList(attributeRequest: AttributeRequest): AttributeResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getAgeGroupDetail(attributeRequest).await() }
        )
    }

    suspend fun getCityList(cityRequest: CityRequest): CityResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getCitiesBasedOnCountry(cityRequest).await() }
        )
    }

    suspend fun getProfileResponse(updateProfileRequest: UpdateProfileRequest): UpdateProfileResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getUpdateProfileRequest(updateProfileRequest).await() }
        )
    }

    /*Update Profile Image callback*/
    suspend fun getUpdateProfileImage(updateProfileImageRequest: UpdateProfileImageRequest): UpdateProfileImageResponse? {
        return safeApiCall(
            call = {
                apiInterface.getupdateProfileImage(updateProfileImageRequest).await()
            }
        )
    }


    //Request for account deletion
    suspend fun requestAccountDeletion(deleteAccountRequest: DeleteAccountRequest): DeleteAccountResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.requestAccountDeletion(deleteAccountRequest).await() }
            //convert to mutable list
        )
    }


    //Cancel account deletion
    suspend fun cancelAccountDeletion(cancelRequest: CancelRequest): CancelResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.cancelAccountDeletion(cancelRequest).await() }
            //convert to mutable list
        )
    }
}
