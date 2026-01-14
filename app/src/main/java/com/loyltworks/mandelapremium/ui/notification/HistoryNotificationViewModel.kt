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
            //get latest news from news repo
            val HistoryNotification_data =
                apiRepository.getHistoryNotificationList(historyNotificationRequest)
            //post the value inside live data
            _historyNotificationtLiveData.postValue(HistoryNotification_data)
        }
    }

    /*TransactionHistory Listing */
    private val _historyNotificationtDetailByIDLiveData =
        MutableLiveData<HistoryNotificationResponse>()
    val historyNotificationtDetailByIDLiveData: LiveData<HistoryNotificationResponse> =
        _historyNotificationtDetailByIDLiveData

    fun getHistoryNotificationDetailById(historyNotificationDetailsRequest: HistoryNotificationDetailsRequest) {
        scope.launch {
            //get latest news from news repo
            val HistoryNotificationDetailByID =
                apiRepository.getHistoryNotificationDetailByIdList(historyNotificationDetailsRequest)
            //post the value inside live data
            _historyNotificationtLiveData.postValue(HistoryNotificationDetailByID)
        }
    }

    /*TransactionHistory Listing */
    private val _historyNotificationtDeleteByIDLiveData =
        MutableLiveData<HistoryNotificationResponse>()
    val historyNotificationtDeleteByIDLiveData: LiveData<HistoryNotificationResponse> =
        _historyNotificationtDeleteByIDLiveData


    fun getDeleteHistoryNotificationResponse(historyNotificationDetailsRequest: HistoryNotificationDetailsRequest) {
        scope.launch {
            //get latest news from news repo
            val HistoryNotificationDetailByID =
                apiRepository.getHistoryNotificationDetailByIdList(historyNotificationDetailsRequest)
            //post the value inside live data
//            _historyNotificationtLiveData.postValue(HistoryNotificationDetailByID)
        }
    }

}