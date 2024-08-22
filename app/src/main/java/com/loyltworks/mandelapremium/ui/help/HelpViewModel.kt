package com.loyltworks.mandelapremium.ui.help

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyltworks.mandelapremium.model.*
import com.loyltworks.mandelapremium.ui.baseClass.BaseViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch

class HelpViewModel : BaseViewModel() {

    /*Query Listing */
    private val _queryListLiveData = MutableLiveData<QueryListingResponse>()
    val queryListLiveData: LiveData<QueryListingResponse> = _queryListLiveData

    fun getQueryListLiveData(queryList: QueryListingRequest) {
        scope.launch { _queryListLiveData.postValue(apiRepository.getQueryListData(queryList)) }
    }

    /*Help topic listing*/
    private val _topicListData = MutableLiveData<GetHelpTopicResponse>()
    val topicListLiveData: LiveData<GetHelpTopicResponse> = _topicListData

    fun getHelpTopicListLiveData(topicList: HelpTopicRetrieveRequest) {
        scope.launch {
            val helpTopic_data = apiRepository.getHelpTopic(topicList)
            _topicListData.postValue(helpTopic_data)
        }
    }

    /*Chat reply*/
    private val _queryChatLiveData = MutableLiveData<QueryChatElementResponse>()
    val queryChatLiveData: LiveData<QueryChatElementResponse> = _queryChatLiveData

    fun getQueryChat(chatQuery: QueryChatElementRequest) {
        scope.launch { _queryChatLiveData.postValue(apiRepository.getChatQuery(chatQuery)) }
    }

    /*Save new ticket request-response*/
    private val _saveNewTicketQueryLiveData = MutableLiveData<SaveNewTicketQueryResponse>()
    val saveNewTicketQueryLiveData: LiveData<SaveNewTicketQueryResponse> =
        _saveNewTicketQueryLiveData

    fun saveNewTicketQuery(saveNewTicketQueryRequest: SaveNewTicketQueryRequest) {

        scope.launch {
            _saveNewTicketQueryLiveData.postValue(
                apiRepository.getSaveNewTicketQuery(
                    saveNewTicketQueryRequest
                )
            )
        }
    }

    /*Post reply help topic*/

    private val _postChatStatusResponseLiveData = MutableLiveData<PostChatStatusResponse>()
    val postChatStatusResponseLiveData: LiveData<PostChatStatusResponse> =
        _postChatStatusResponseLiveData

    fun getPostReply(postChatStatusRequest: PostChatStatusRequest, context: Context) {

        scope.launch {
            _postChatStatusResponseLiveData.postValue(
                apiRepository.getPostReply(
                    postChatStatusRequest
                )
            )
        }
    }


    /* Feedback Submit*/

    private val _feedbackResponseLiveData = MutableLiveData<FeedbackResponse>()
    val postFeedbackResponseLiveData: LiveData<FeedbackResponse> = _feedbackResponseLiveData

    fun getFeedback(feedbacRequest: FeedbackRequest) {

        scope.launch {
            _feedbackResponseLiveData.postValue(
                apiRepository.getFeedbackResponse(
                    feedbacRequest
                )
            )
        }
    }

}

