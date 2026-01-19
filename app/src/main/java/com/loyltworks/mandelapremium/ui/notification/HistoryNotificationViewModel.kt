package com.loyltworks.mandelapremium.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyltworks.mandelapremium.model.HistoryNotificationDetailsRequest
import com.loyltworks.mandelapremium.model.HistoryNotificationRequest
import com.loyltworks.mandelapremium.model.HistoryNotificationResponse
import com.loyltworks.mandelapremium.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class HistoryNotificationViewModel : BaseViewModel() {

    /*TransactionHistory Listing */
    private val _historyNotificationtLiveData = MutableLiveData<HistoryNotificationResponse>()
    val historyNotificationtLiveData: LiveData<HistoryNotificationResponse> =
        _historyNotificationtLiveData

    fun getNotificationHistoryResponse(historyNotificationRequest: HistoryNotificationRequest) {
        scope.launch {
            _historyNotificationtLiveData.postValue(apiRepository?.getHistoryNotificationList(historyNotificationRequest))
        }
    }

    /*TransactionHistory Listing */
    private val _historyNotificationtDetailByIDLiveData =
        MutableLiveData<HistoryNotificationResponse>()
    val historyNotificationtDetailByIDLiveData: LiveData<HistoryNotificationResponse> =
        _historyNotificationtDetailByIDLiveData

    fun getHistoryNotificationDetailById(historyNotificationDetailsRequest: HistoryNotificationDetailsRequest) {
        scope.launch {
            _historyNotificationtLiveData.postValue(apiRepository?.getHistoryNotificationDetailByIdList(historyNotificationDetailsRequest))
        }
    }

    /*TransactionHistory Listing */
    private val _historyNotificationtDeleteByIDLiveData =
        MutableLiveData<HistoryNotificationResponse>()
    val historyNotificationtDeleteByIDLiveData: LiveData<HistoryNotificationResponse> =
        _historyNotificationtDeleteByIDLiveData


    fun getDeleteHistoryNotificationResponse(historyNotificationDetailsRequest: HistoryNotificationDetailsRequest) {
        scope.launch {
            _historyNotificationtLiveData.postValue(apiRepository?.getHistoryNotificationDetailByIdList(historyNotificationDetailsRequest))
        }
    }

}