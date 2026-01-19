package com.loyltworks.mandelapremium.ui.MyActivity.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyltworks.mandelapremium.model.*
import com.loyltworks.mandelapremium.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class EarningRedemptionViewModel : BaseViewModel() {


    /*TransactionHistory Listing */
    private val _transactionHistoryListLiveData = MutableLiveData<TransactionHistoryResponse>()
    val transactionHistoryListLiveData: LiveData<TransactionHistoryResponse> =
        _transactionHistoryListLiveData

    /*Redemption Listing */
    private val _redemptionLiveData = MutableLiveData<RedemptionResponse>()
    val redemptionLiveData: LiveData<RedemptionResponse> = _redemptionLiveData

    private val _attributeDetails = MutableLiveData<AttributeResponse>()
    val filterData: LiveData<AttributeResponse> = _attributeDetails

    fun transactionHistoryLiveData(transactionHistoryRequest: TransactionHistoryRequest) {
        scope.launch {
            _transactionHistoryListLiveData.postValue(
                apiRepository?.getTansactionHistoryList(
                    transactionHistoryRequest
                )
            )
        }
    }

    fun redemptionLiveData(redemptionRequest: RedemptionRequest) {
        scope.launch {
            _redemptionLiveData.postValue(apiRepository?.getRedemptionResponse(redemptionRequest))
        }
    }

}