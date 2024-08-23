package com.loyltworks.mandelapremium.ui.help.newTicket

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.oneloyalty.goodpack.utils.BlockMultipleClick
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.GetHelpTopicRetrieveRequest
import com.loyltworks.mandelapremium.model.HelpTopicRetrieveRequest
import com.loyltworks.mandelapremium.model.ObjHelpTopicList
import com.loyltworks.mandelapremium.model.SaveNewTicketQueryRequest
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.dashboard.DashboardActivity
import com.loyltworks.mandelapremium.ui.help.HelpViewModel
import com.loyltworks.mandelapremium.ui.help.newTicket.SelectQueryBottomSheet.SelectQueryOption.selectPaymentMode
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData
import com.vmb.fileSelect.ProgressDialogue
import kotlinx.android.synthetic.main.activity_new_ticket.*
import kotlinx.android.synthetic.main.row_history_notifications.*
import kotlin.collections.ArrayList

class NewTicketActivity : BaseActivity() , View.OnClickListener{
    private lateinit var viewModel : HelpViewModel
     var __PaymentmethodList = ArrayList<ObjHelpTopicList>()

    private  var fileExtenstion:String = ""
    private var mProfileImagePath = ""
    private var helpTopicId: Int = -1

    override fun callInitialServices() {
        LoadingDialogue.showDialog(this)
        viewModel.getHelpTopicListLiveData(
            HelpTopicRetrieveRequest(
                GetHelpTopicRetrieveRequest(
                    "4", PreferenceHelper.getLoginDetails(
                        this
                    )?.UserList!![0].UserId.toString(), "true"
                )
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun callObservers() {

        viewModel.topicListLiveData.observe(this, androidx.lifecycle.Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && it.GetHelpTopicsResult.objHelpTopicList != null) {
//                val helpTypeList: MutableList<ObjHelpTopicList> = it.getHelpTopicsResult?.objHelpTopicList as MutableList<ObjHelpTopicList>
                __PaymentmethodList = it.GetHelpTopicsResult.objHelpTopicList as ArrayList<ObjHelpTopicList>
                LoadingDialogue.dismissDialog()
            }
        })

        viewModel.saveNewTicketQueryLiveData.observe(this, androidx.lifecycle.Observer  {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.ReturnMessage.isNullOrEmpty()) {
                val message: String = java.lang.String.valueOf(it.ReturnMessage)
                if (!TextUtils.isEmpty(message) && message.split("~".toRegex())
                        .toTypedArray()[0].toInt() > 0
                ) {

                    Toast.makeText(context,"Support Ticket has been submitted successfully", Toast.LENGTH_SHORT).show()
//                    snackBar(" Support Ticket has been submitted successfully", R.color.green)
//                    view?.findNavController()?.popBackStack()
                    onBackPressed()
                } else {
                snackBar(
                        "Failed to submit new ticket",
                        R.color.red
                    )
//                    onBackPressed()
//                    view?.findNavController()?.popBackStack()
                }
            } else {
               snackBar(
                    "Something went wrong, please try again later",
                    R.color.blue
                )
            }
        })



    }

    var paymentPosition = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HelpViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_ticket)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //set context
        context = this

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)

        browse_image.setOnClickListener(this)

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        //supportActionBar!!.setHomeAsUpIndicator(upArrow)

        browse_Img.setOnClickListener(this)
        submit.setOnClickListener(this)

        remove.setOnClickListener {

            browse_Img.visibility = View.GONE
            image_relative.visibility = View.GONE
            file_name.visibility = View.GONE
            remove.visibility = View.GONE
            mProfileImagePath = ""
            file_name.text = ""

        }

    }

    fun SelectQueryTopic(view: View) {

        selectPaymentMode(
            context!!,
            paymentPosition,
            __PaymentmethodList,
            object : SelectQueryBottomSheet.PaymentCallBack {
                override fun onPaymentCallback(_PaymentmethodList: ArrayList<ObjHelpTopicList>, position: Int) {
                    paymentPosition = position

                    select_query_topic.text = _PaymentmethodList[paymentPosition].HelpTopicName

                    if (position != 0)
                        select_query_topic.setTextColor(resources.getColor(R.color.white))

                    helpTopicId = _PaymentmethodList[paymentPosition].HelpTopicId!!

                }
            }
        )
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        if(BlockMultipleClick.click()) return

        when (v?.id) {
            R.id.browse_image -> this.let {

                // Browse Image or Files
                FileSelector.open(this, object : FileSelectorCallBack {
                    override fun onResponse(fileSelectorData: FileSelectorData) {
                        mProfileImagePath = fileSelectorData.responseInBase64!!
                        fileExtenstion = fileSelectorData.extension!!

                        browse_Img.visibility = View.VISIBLE
                        image_relative.visibility = View.VISIBLE
                        file_name.visibility = View.VISIBLE
                        remove.visibility = View.VISIBLE

                        browse_Img.setImageBitmap(fileSelectorData.thumbnail)
                        file_name.text = fileSelectorData.fileName

                        /* if(fileExtenstion.equals("png",true)) {
                                     browse_Img.visibility = View.VISIBLE
                                     file_name.visibility = View.GONE
                                     Glide.with(requireContext())
                                         .asBitmap()
                                         .load(Base64.decode(mProfileImagePath, Base64.DEFAULT))
                                         .into(browse_Img)
                                 }else{
                                     browse_Img.visibility = View.GONE
                                     file_name.visibility = View.VISIBLE

                                     file_name.text = fileName
                                 }*/
                    }
                })

            }
            R.id.submit -> {
                //Validate before submit query :
                if (helpTopicId==-1) {
                   snackBar("Select any topic.", R.color.red)
                    return
                } else if (TextUtils.isEmpty(query_summary.getText().toString())) {
                    query_summary.error = "Enter query summary."
                    query_summary.requestFocus()
                    return
                }  else if (TextUtils.isEmpty(query_details.getText().toString())) {
                    query_details.error = "Enter ticket details."
                    query_details.requestFocus()
                    return
                } else {

                    try {
                        LoadingDialogue.showDialog(this)
                        viewModel.saveNewTicketQuery(
                            SaveNewTicketQueryRequest(
                                ActionType = "0",
                                ActorId = PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserId.toString(),
                                CustomerName = PreferenceHelper.getLoginDetails(this)?.UserList!![0].Name.toString(),
                                Email = PreferenceHelper.getLoginDetails(this)?.UserList!![0].Email.toString(),
                                HelpTopic = select_query_topic.text.toString(),
                                HelpTopicID = helpTopicId.toString(),
                                IsQueryFromMobile = "true",
                                LoyaltyID = PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserName.toString(),
                                Mobile = PreferenceHelper.getLoginDetails(this)?.UserList!![0].Mobile.toString(),
                                QueryDetails = query_details.text.toString(),
                                QuerySummary = query_details.text.toString(),
                                SourceType = "1",
//                                FileType = fileExtenstion,
                                ImageUrl = mProfileImagePath
                            )
                        )
//
                    } catch (e: Exception) {
                        Log.d("TAG", "onClick: $e")
                    }
                }
            }
        }
    }


}