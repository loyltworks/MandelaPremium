package com.loyltworks.mandelapremium.ui.BuyAndGift

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyltworks.mandelapremium.model.*
import com.loyltworks.mandelapremium.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class BuyGiftViewModel : BaseViewModel() {

    /*BuyGift Listing */
    private val _getBuyGiftListLiveData = MutableLiveData<GetBuyGiftResponse>()
    val getBuyGiftListLiveData: LiveData<GetBuyGiftResponse> = _getBuyGiftListLiveData

    fun getBuyGift(getBuyGiftRequest: GetBuyGiftRequest) {
        scope.launch {
            //get latest news from news repo
            val buyGift_data = apiRepository.getBuyGiftList(getBuyGiftRequest)
            //post the value inside live data
            _getBuyGiftListLiveData.postValue(buyGift_data)
        }

    }

    /*BuyGift Caseback Value */
    private val _getBuyGiftCaseBackValueListLiveData = MutableLiveData<GetCashBackResponse>()
    val getBuyGiftCaseBackValueListLiveData: LiveData<GetCashBackResponse> = _getBuyGiftCaseBackValueListLiveData

    fun getBuyGiftCaseBackValue(getCaseBackRequest: GetCashBackRequest) {
        scope.launch {
            //get latest news from news repo
            val buyGiftcasebackValue_data = apiRepository.getBuyGiftCaseBackList(getCaseBackRequest)
            //post the value inside live data
            _getBuyGiftCaseBackValueListLiveData.postValue(buyGiftcasebackValue_data)
        }

    }


    /*BuyGift Buy Now */

    private val _getBuyGiftBuyNowLiveData = MutableLiveData<GetSaveGiftCardResponse>()
    val getBuyGiftBuyNowLiveData: LiveData<GetSaveGiftCardResponse> = _getBuyGiftBuyNowLiveData

    fun getBuyGiftBuyNow(getSaveGiftCardRequest: GetSaveGiftCardRequest) {
        scope.launch {
            //get latest news from news repo
            val buyGiftBuyNow_data = apiRepository.getBuyGiftBuyNow(getSaveGiftCardRequest)
            //post the value inside live data
            _getBuyGiftBuyNowLiveData.postValue(buyGiftBuyNow_data)
        }

    }


}