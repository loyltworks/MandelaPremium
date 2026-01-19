package com.loyltworks.mandelapremium.ui.MyActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyltworks.mandelapremium.model.*
import com.loyltworks.mandelapremium.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class MyActivityViewModel : BaseViewModel() {


    /*TransactionHistory Listing */
    private val _wishPointListLiveData = MutableLiveData<WishPointsResponse>()
    val wisePointLiveData : LiveData<WishPointsResponse> = _wishPointListLiveData

    fun wishPointsLiveData(wishPointRequest: WishPointRequest) {
        scope.launch {
            _wishPointListLiveData.postValue(apiRepository?.getLocationWiseList(wishPointRequest))
        }
    }
}