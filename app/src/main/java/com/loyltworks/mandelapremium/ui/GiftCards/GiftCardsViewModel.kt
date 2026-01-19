package com.loyltworks.mandelapremium.ui.GiftCards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyltworks.mandelapremium.model.GetAlbumsWithImagesRequest
import com.loyltworks.mandelapremium.model.GetAlbumsWithImagesResponse
import com.loyltworks.mandelapremium.model.GetGiftCardRequest
import com.loyltworks.mandelapremium.model.GetGiftCardResponse
import com.loyltworks.mandelapremium.model.GetReceiverIDRequest
import com.loyltworks.mandelapremium.model.GetReceiverIDResponse
import com.loyltworks.mandelapremium.model.MyVoucherGiftCardSubmitRequest
import com.loyltworks.mandelapremium.model.MyVoucherGiftCardSubmitResponse
import com.loyltworks.mandelapremium.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class GiftCardsViewModel : BaseViewModel() {

    /*GiftCards Listing */
    private val _getGiftCardListLiveData = MutableLiveData<GetGiftCardResponse>()
    val getGiftCardListLiveData : LiveData<GetGiftCardResponse> = _getGiftCardListLiveData

    fun getGiftCard(getGiftCardRequest: GetGiftCardRequest) {
        scope.launch {
            _getGiftCardListLiveData.postValue(apiRepository?.getGiftCardList(getGiftCardRequest))
        }
    }

    /*GetAlbumsWithImages Listing */
    private val _getGetAlbumWithImageListLiveData = MutableLiveData<GetAlbumsWithImagesResponse>()
    val getAlbumWithImageListLiveData : LiveData<GetAlbumsWithImagesResponse> = _getGetAlbumWithImageListLiveData

    fun getAlbumWithImage(getAlbumWithImagesRequest: GetAlbumsWithImagesRequest) {
        scope.launch {
            _getGetAlbumWithImageListLiveData.postValue(apiRepository?.getAlbumsWithImageList(getAlbumWithImagesRequest))
        }
    }

    /*Get Album Images by Album ID Listing */
    private val _getGetAlbumImageByIDListLiveData = MutableLiveData<GetAlbumsWithImagesResponse>()
    val getAlbumImageByIDListLiveData : LiveData<GetAlbumsWithImagesResponse> = _getGetAlbumImageByIDListLiveData

    fun getAlbumImageByID(getAlbumWithImagesRequest: GetAlbumsWithImagesRequest) {
        scope.launch {
            _getGetAlbumImageByIDListLiveData.postValue(apiRepository?.getAlbumsImageByIDList(getAlbumWithImagesRequest))
        }
    }

   /*Get Receiver ID  */
    private val _getReceiverIDLiveData = MutableLiveData<GetReceiverIDResponse>()
    val getReceiverIDLiveData : LiveData<GetReceiverIDResponse> = _getReceiverIDLiveData

    fun getReceiverIdName(giftReceiverIdRequest: GetReceiverIDRequest) {
        scope.launch {
            _getReceiverIDLiveData.postValue(apiRepository?.getReceiverIdName(giftReceiverIdRequest))
        }
    }


    /*My Voucher GiftNow Submit */
    private val _getMyVoucherGiftNowLiveData = MutableLiveData<MyVoucherGiftCardSubmitResponse>()
    val getMyVoucherGiftNowLiveData : LiveData<MyVoucherGiftCardSubmitResponse> = _getMyVoucherGiftNowLiveData

    fun getMyVoucherGiftNow(myVoucherGiftCardSubmitRequest: MyVoucherGiftCardSubmitRequest) {
        scope.launch {
            _getMyVoucherGiftNowLiveData.postValue(apiRepository?.getMyVoucherGiftCardSubmit(myVoucherGiftCardSubmitRequest))
        }
    }
}

