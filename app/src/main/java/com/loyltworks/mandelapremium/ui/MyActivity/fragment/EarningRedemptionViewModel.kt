package com.loyltworks.mandelapremium.ui.MyActivity.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyltworks.mandelapremium.model.*
import com.loyltworks.mandelapremium.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class EarningRedemptionViewModel : BaseViewModel() {


    /*TransactionHistory Listing */
    private val _transactionHistoryListLiveData = MutableLiveData<TransactionHistoryResponse>()
    val transactionHistoryListLiveData : LiveData<TransactionHistoryResponse> = _transactionHistoryListLiveData

     /*Redemption Listing */
    private val _redemptionLiveData = MutableLiveData<RedemptionResponse>()
    val redemptionLiveData : LiveData<RedemptionResponse> = _redemptionLiveData

    private val _attributeDetails = MutableLiveData<AttributeResponse>()
    val filterData: LiveData<AttributeResponse> = _attributeDetails

    fun transactionHistoryLiveData(transactionHistoryRequest: TransactionHistoryRequest) {
        scope.launch {
            //get latest news from news repo
            val transactionHistory_data = apiRepository.getTansactionHistoryList(transactionHistoryRequest)
            //post the value inside live data
            _transactionHistoryListLiveData.postValue(transactionHistory_data)
        }
    }

    fun redemptionLiveData(redemptionRequest: RedemptionRequest) {
            scope.launch {
                //get latest news from news repo
                val transactionHistory_data = apiRepository.getRedemptionResponse(redemptionRequest)
                //post the value inside live data
                _redemptionLiveData.postValue(transactionHistory_data)
            }
        }


}