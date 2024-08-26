package com.loyltworks.mandelapremium.ui.help.help_topic_chat_status

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.oneloyalty.goodpack.utils.BlockMultipleClick
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.ObjCustomerAllQueryList
import com.loyltworks.mandelapremium.model.PostChatStatusRequest
import com.loyltworks.mandelapremium.model.QueryChatElementRequest
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.help.HelpViewModel
import com.loyltworks.mandelapremium.ui.help.adapter.QueryChatAdapter
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData
import kotlinx.android.synthetic.main.activity_my_query.*
import kotlinx.android.synthetic.main.activity_query_chat.*
import kotlinx.android.synthetic.main.activity_query_chat.back


class QueryChatActivity  : BaseActivity(), View.OnClickListener, QueryChatAdapter.ChatImageDisplay  {

    private var mProfileImagePath = ""
    var ticketId: Int = -1
    var customerTicketId: Int = -1
    var actionType: Int = 0
    var QueryStatus: String? = null
    var helpTopic: String? = null
    var helpTopicID: String? = null
    private val PICKFILE_REQUEST_CODE: Int = 24
    private var outputFileUri: Uri? = null

    private var fileExtenstion: String = ""

    private lateinit var viewModel: HelpViewModel

    override fun callInitialServices() {
        LoadingDialogue.showDialog(this)
        viewModel.getQueryChat(
            QueryChatElementRequest(
                "171", PreferenceHelper.getLoginDetails(
                    this
                )?.UserList!![0].UserId.toString(), customerTicketId.toString()
            )
        )

    }

    override fun callObservers() {

        viewModel.queryChatLiveData.observe(this, Observer {
            query_chat_recycler.adapter = QueryChatAdapter(it, this)
            imageAdd.isEnabled = true
            send_query_btn.isEnabled = true
            query_details_fld.isEnabled = true
            query_details_fld.text.clear()
            mProfileImagePath = ""
            query_chat_recycler.scrollToPosition(it.objQueryResponseJsonList!!.size - 1)

            LoadingDialogue.dismissDialog()
        })

        viewModel.postChatStatusResponseLiveData.observe(this, Observer {
            LoadingDialogue.dismissDialog()
            mProfileImagePath = ""
            if (it != null && !it.ReturnMessage.isNullOrEmpty() && it.ReturnMessage!!.split("~")[0] == "1") {
                loadChats()
            } else {
                LoadingDialogue.dismissDialog()
            }
        })

    }

    private fun loadChats() {
        LoadingDialogue.showDialog(this)
        viewModel.getQueryChat(
            QueryChatElementRequest(
                "171", PreferenceHelper.getLoginDetails(
                    this
                )?.UserList!![0].UserId.toString(), customerTicketId.toString()
            )
        )
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HelpViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query_chat)
        //set context
        context = this

        val intent = this.intent
        val bundle = intent.extras
        if (bundle != null) {
            val dataholder: ObjCustomerAllQueryList? = bundle.getSerializable("SUPPORT_DATA") as ObjCustomerAllQueryList?

            if (dataholder != null) {
//                ticketId = dataholder.TicketStatus?.toInt()!!
                customerTicketId = dataholder.CustomerTicketID!!
                QueryStatus = dataholder.TicketStatus!!
                helpTopic = dataholder.HelpTopic!!
                helpTopicID = dataholder.HelpTopicID.toString()!!
                query_summary.text = "Ticket Details : " + dataholder.QuerySummary!!


                if (dataholder.TicketStatus == "Closed") {
                    edittext_block_linear.visibility = View.GONE
                    chatlist_closet_text.visibility = View.VISIBLE
                } else {
                    edittext_block_linear.visibility = View.VISIBLE
                    chatlist_closet_text.visibility = View.GONE

                }


            }
        }



        imageAdd.setOnClickListener(this)
        send_query_btn.setOnClickListener(this)
        back.setOnClickListener(this)

        closeImage.setOnClickListener {
            ChatImageOpen.visibility = View.GONE
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onBackPressed() {
        if (ChatImageOpen.visibility == View.VISIBLE) {
            ChatImageOpen.visibility = View.GONE
        }else {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        }

    }


    override fun onClickChatImage(Url: String?) {
        ChatImageOpen.visibility = View.VISIBLE
//        mChatImageOpen.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        Glide.with(this)
            .load(Url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(chatImges)
    }

    override fun onClick(v: View?) {
        if(BlockMultipleClick.click()) return

        when (v?.id) {

            R.id.back ->{
                onBackPressed()
            }

            R.id.imageAdd ->
                // Browse Image or File
                FileSelector.open(this, object : FileSelectorCallBack {
                    override fun onResponse(fileSelectorData: FileSelectorData) {
                        mProfileImagePath = fileSelectorData.responseInBase64!!
                        fileExtenstion = fileSelectorData.extension!!
                        // post the image
                        getPostReplyTextImage()
                    }
                })

            R.id.send_query_btn ->
                getPostReplyTextImage()

        }
    }

    private fun getPostReplyTextImage() {
        if (!TextUtils.isEmpty(query_details_fld.text) || !mProfileImagePath.isNullOrEmpty()) {
            LoadingDialogue.showDialog(this)

            when (QueryStatus) {
                "Resolved",
                "Reopen" -> ticketId = 2
                "Resolved-Follow up" -> ticketId = 5
                "Closed" -> ticketId = 4
                "Pending" -> ticketId = 1
            }


            val postChatStatusRequest = PostChatStatusRequest()

            postChatStatusRequest.ActionType = "4"

            if (!mProfileImagePath.isNullOrEmpty()) {
                postChatStatusRequest.ImageUrl = mProfileImagePath
//                postChatStatusRequest.FileType = fileExtenstion
                postChatStatusRequest.IsQueryFromMobile = "true"
            } else {
                postChatStatusRequest.IsQueryFromMobile = "false"
            }
            postChatStatusRequest.ActorId =
                PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserId.toString()

            if (!TextUtils.isEmpty(query_details_fld.text))
                postChatStatusRequest.QueryDetails = query_details_fld.text.toString()

            postChatStatusRequest.HelpTopicID = helpTopicID
            postChatStatusRequest.CustomerTicketID = customerTicketId.toString()
            postChatStatusRequest.QueryStatus = ticketId.toString()
            postChatStatusRequest.HelpTopic = helpTopic

            viewModel.getPostReply(postChatStatusRequest, this)

        }
    }


}
