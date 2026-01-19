package com.loyltworks.mandelapremium.ui.OfferAndPromotion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyltworks.mandelapremium.model.GetPromotionResponse
import com.loyltworks.mandelapremium.model.GetWhatsNewRequest
import com.loyltworks.mandelapremium.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class PromotionViewModel : BaseViewModel() {

    /*Promotion Listing */
    private val _getPromotionListLiveData = MutableLiveData<GetPromotionResponse>()
    val getPromotionListLiveData : LiveData<GetPromotionResponse> = _getPromotionListLiveData

    fun getPromotion(getWhatsNewRequest: GetWhatsNewRequest) {
        scope.launch {
            _getPromotionListLiveData.postValue(apiRepository?.getPromotionList(getWhatsNewRequest))
        }
    }

}