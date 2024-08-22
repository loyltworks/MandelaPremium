package com.loyltworks.mandelapremium.ui.GiftCards

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyltworks.mandelapremium.model.*
import com.loyltworks.mandelapremium.ui.baseClass.BaseViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch

class GiftCardsViewModel : BaseViewModel() {

    /*GiftCards Listing */
    private val _getGiftCardListLiveData = MutableLiveData<GetGiftCardResponse>()
    val getGiftCardListLiveData : LiveData<GetGiftCardResponse> = _getGiftCardListLiveData

    fun getGiftCard(getGiftCardRequest: GetGiftCardRequest) {
        scope.launch {
            //get latest news from news repo
            val promotion_data = apiRepository.getGiftCardList(getGiftCardRequest)
            //post the value inside live data
            _getGiftCardListLiveData.postValue(promotion_data)
        }
    }



    /*GetAlbumsWithImages Listing */
    private val _getGetAlbumWithImageListLiveData = MutableLiveData<GetAlbumsWithImagesResponse>()
    val getAlbumWithImageListLiveData : LiveData<GetAlbumsWithImagesResponse> = _getGetAlbumWithImageListLiveData

    fun getAlbumWithImage(getAlbumWithImagesRequest: GetAlbumsWithImagesRequest) {
        scope.launch {
            //get latest news from news repo
            val albumwithImage_data = apiRepository.getAlbumsWithImageList(getAlbumWithImagesRequest)
            //post the value inside live data
            _getGetAlbumWithImageListLiveData.postValue(albumwithImage_data)
        }
    }

    /*Get Album Images by Album ID Listing */
    private val _getGetAlbumImageByIDListLiveData = MutableLiveData<GetAlbumsWithImagesResponse>()
    val getAlbumImageByIDListLiveData : LiveData<GetAlbumsWithImagesResponse> = _getGetAlbumImageByIDListLiveData

    fun getAlbumImageByID(getAlbumWithImagesRequest: GetAlbumsWithImagesRequest) {
        scope.launch {
            //get latest news from news repo
            val albumwithImagebyID_data = apiRepository.getAlbumsImageByIDList(getAlbumWithImagesRequest)
            //post the value inside live data
            _getGetAlbumImageByIDListLiveData.postValue(albumwithImagebyID_data)
        }
    }



   /*Get Receiver ID  */
    private val _getReceiverIDLiveData = MutableLiveData<GetReceiverIDResponse>()
    val getReceiverIDLiveData : LiveData<GetReceiverIDResponse> = _getReceiverIDLiveData

    fun getReceiverIdName(giftReceiverIdRequest: GetReceiverIDRequest) {
        scope.launch {
            //get latest news from news repo
            val receiverID_data = apiRepository.getReceiverIdName(giftReceiverIdRequest)
            //post the value inside live data
            _getReceiverIDLiveData.postValue(receiverID_data)
        }
    }


    /*My Voucher GiftNow Submit */
    private val _getMyVoucherGiftNowLiveData = MutableLiveData<MyVoucherGiftCardSubmitResponse>()
    val getMyVoucherGiftNowLiveData : LiveData<MyVoucherGiftCardSubmitResponse> = _getMyVoucherGiftNowLiveData

    fun getMyVoucherGiftNow(myVoucherGiftCardSubmitRequest: MyVoucherGiftCardSubmitRequest) {
        scope.launch {
            //get latest news from news repo
            val myVoucherGiftNow_data = apiRepository.getMyVoucherGiftCardSubmit(myVoucherGiftCardSubmitRequest)
            //post the value inside live data
            _getMyVoucherGiftNowLiveData.postValue(myVoucherGiftNow_data)
        }
    }



}

