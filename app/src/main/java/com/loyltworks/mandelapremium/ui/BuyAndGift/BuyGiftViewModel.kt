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
            //post the value inside live data
            _getBuyGiftListLiveData.postValue(apiRepository?.getBuyGiftList(getBuyGiftRequest))
        }
    }

    /*BuyGift Caseback Value */
    private val _getBuyGiftCaseBackValueListLiveData = MutableLiveData<GetCashBackResponse>()
    val getBuyGiftCaseBackValueListLiveData: LiveData<GetCashBackResponse> = _getBuyGiftCaseBackValueListLiveData

    fun getBuyGiftCaseBackValue(getCaseBackRequest: GetCashBackRequest) {
        scope.launch {
            _getBuyGiftCaseBackValueListLiveData.postValue(apiRepository?.getBuyGiftCaseBackList(getCaseBackRequest))
        }
    }


    /*BuyGift Buy Now */

    private val _getBuyGiftBuyNowLiveData = MutableLiveData<GetSaveGiftCardResponse>()
    val getBuyGiftBuyNowLiveData: LiveData<GetSaveGiftCardResponse> = _getBuyGiftBuyNowLiveData

    fun getBuyGiftBuyNow(getSaveGiftCardRequest: GetSaveGiftCardRequest) {
        scope.launch {
            _getBuyGiftBuyNowLiveData.postValue(apiRepository?.getBuyGiftBuyNow(getSaveGiftCardRequest))
        }
    }
}