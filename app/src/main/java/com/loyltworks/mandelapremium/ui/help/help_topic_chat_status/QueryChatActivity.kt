package com.loyltworks.mandelapremium.ui.help.help_topic_chat_status

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityQueryChatBinding
import com.loyltworks.mandelapremium.model.ObjCustomerAllQueryList
import com.loyltworks.mandelapremium.model.PostChatStatusRequest
import com.loyltworks.mandelapremium.model.QueryChatElementRequest
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.help.HelpViewModel
import com.loyltworks.mandelapremium.ui.help.adapter.QueryChatAdapter
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import com.oneloyalty.goodpack.utils.BlockMultipleClick
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData


class QueryChatActivity  : BaseActivity(), View.OnClickListener, QueryChatAdapter.ChatImageDisplay  {
  
    lateinit var binding: ActivityQueryChatBinding

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
            binding.queryChatRecycler.adapter = QueryChatAdapter(it, this)
            binding.imageAdd.isEnabled = true
            binding.sendQueryBtn.isEnabled = true
            binding.queryDetailsFld.isEnabled = true
            binding.queryDetailsFld.text.clear()
            mProfileImagePath = ""
            binding.queryChatRecycler.scrollToPosition(it.objQueryResponseJsonList!!.size - 1)

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
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HelpViewModel::class.java)
        binding = ActivityQueryChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                binding.querySummary.text = "Ticket Details : " + dataholder.QuerySummary!!


                if (dataholder.TicketStatus == "Closed") {
                    binding.edittextBlockLinear.visibility = View.GONE
                    binding.chatlistClosetText.visibility = View.VISIBLE
                } else {
                    binding.edittextBlockLinear.visibility = View.VISIBLE
                    binding.chatlistClosetText.visibility = View.GONE

                }


            }
        }



        binding.imageAdd.setOnClickListener(this)
        binding.sendQueryBtn.setOnClickListener(this)
        binding.back.setOnClickListener(this)

        binding.closeImage.setOnClickListener {
            binding.ChatImageOpen.visibility = View.GONE
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onBackPressed() {
        if (binding.ChatImageOpen.visibility == View.VISIBLE) {
            binding.ChatImageOpen.visibility = View.GONE
        }else {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        }

    }


    override fun onClickChatImage(Url: String?) {
        binding.ChatImageOpen.visibility = View.VISIBLE
//        mChatImageOpen.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        Glide.with(this)
            .load(Url)
            .placeholder(R.drawable.dummy_image)
            .error(R.drawable.dummy_image)
            .into(binding.chatImges)
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

            R.id.sendQueryBtn ->
                getPostReplyTextImage()

        }
    }

    private fun getPostReplyTextImage() {
        if (!TextUtils.isEmpty(binding.queryDetailsFld.text) || !mProfileImagePath.isNullOrEmpty()) {
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

            if (!TextUtils.isEmpty(binding.queryDetailsFld.text))
                postChatStatusRequest.QueryDetails = binding.queryDetailsFld.text.toString()

            postChatStatusRequest.HelpTopicID = helpTopicID
            postChatStatusRequest.CustomerTicketID = customerTicketId.toString()
            postChatStatusRequest.QueryStatus = ticketId.toString()
            postChatStatusRequest.HelpTopic = helpTopic

            viewModel.getPostReply(postChatStatusRequest, this)

        }
    }


}
