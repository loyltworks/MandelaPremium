package com.loyltworks.mandelapremium.ui.help.newTicket

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.oneloyalty.goodpack.utils.BlockMultipleClick
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.CityList
import com.loyltworks.mandelapremium.model.CityRequest
import com.loyltworks.mandelapremium.model.GenderSpinner
import com.loyltworks.mandelapremium.model.GetHelpTopicRetrieveRequest
import com.loyltworks.mandelapremium.model.HelpTopicRetrieveRequest
import com.loyltworks.mandelapremium.model.LstAttributesDetail
import com.loyltworks.mandelapremium.model.LstCountryDetail
import com.loyltworks.mandelapremium.model.ObjHelpTopicList
import com.loyltworks.mandelapremium.model.SaveNewTicketQueryRequest
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.dashboard.DashboardActivity
import com.loyltworks.mandelapremium.ui.help.HelpViewModel
import com.loyltworks.mandelapremium.ui.help.adapter.HelptopicsAdapter
import com.loyltworks.mandelapremium.ui.help.newTicket.SelectQueryBottomSheet.SelectQueryOption.selectPaymentMode
import com.loyltworks.mandelapremium.ui.profile.adapter.CountryAdapter
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData
import com.vmb.fileSelect.ProgressDialogue
import kotlinx.android.synthetic.main.activity_new_ticket.*
import kotlinx.android.synthetic.main.fragment_tab1.country_spinner
import kotlinx.android.synthetic.main.row_history_notifications.*
import kotlin.collections.ArrayList

class NewTicketActivity : BaseActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    private lateinit var viewModel: HelpViewModel
    var __PaymentmethodList = ArrayList<ObjHelpTopicList>()

    private var fileExtenstion: String = ""
    private var mProfileImagePath = ""
    private var helpTopicId: Int = -1

    private var mSelectedHelpTopic: ObjHelpTopicList? = null
    var helpTopicList = mutableListOf<ObjHelpTopicList>()

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

                helpTopicList.clear()
                helpTopicList.addAll(it.GetHelpTopicsResult.objHelpTopicList)
                helpTopicList.add(
                    0,
                    ObjHelpTopicList(HelpTopicId = -1, HelpTopicName = "Select topic")
                )
                help_topic_spinner.adapter = HelptopicsAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    helpTopicList as ArrayList<ObjHelpTopicList>
                )

            } else {
                helpTopicList.clear()
                helpTopicList.add(
                    0,
                    ObjHelpTopicList(HelpTopicId = -1, HelpTopicName = "Select topic")
                )
                help_topic_spinner.adapter = HelptopicsAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    helpTopicList as ArrayList<ObjHelpTopicList>
                )
            }
        })

        viewModel.saveNewTicketQueryLiveData.observe(this, androidx.lifecycle.Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.ReturnMessage.isNullOrEmpty()) {
                val message: String = java.lang.String.valueOf(it.ReturnMessage)
                if (!TextUtils.isEmpty(message) && message.split("~".toRegex())
                        .toTypedArray()[0].toInt() > 0
                ) {

                    Toast.makeText(
                        context,
                        "Support Ticket has been submitted successfully",
                        Toast.LENGTH_SHORT
                    ).show()
//                    snackBar(" Support Ticket has been submitted successfully", R.color.green)
//                    view?.findNavController()?.popBackStack()
                    onBackPressed()
                } else {
                    Toast.makeText(this,"Failed to submit new ticket",Toast.LENGTH_SHORT).show()

//                    onBackPressed()
//                    view?.findNavController()?.popBackStack()
                }
            } else {
                Toast.makeText(this,"Something went wrong, please try again later",Toast.LENGTH_SHORT).show()

            }
        })


    }

    var paymentPosition = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HelpViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_ticket)


        //set context
        context = this


        help_topic_spinner.onItemSelectedListener = this

        browse_image.setOnClickListener(this)
        submit.setOnClickListener(this)
        back.setOnClickListener(this)


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
        if (BlockMultipleClick.click()) return

        when (v?.id) {

            R.id.back -> {
                onBackPressed()
            }

            R.id.browse_image -> this.let {

                // Browse Image or Files
                FileSelector.open(this, object : FileSelectorCallBack {
                    override fun onResponse(fileSelectorData: FileSelectorData) {
                        mProfileImagePath = fileSelectorData.responseInBase64!!
                        fileExtenstion = fileSelectorData.extension!!

                        browse_Img.visibility = View.VISIBLE

                        browse_Img.setImageBitmap(fileSelectorData.thumbnail)


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
                if (mSelectedHelpTopic?.HelpTopicId == -1) {
                    Toast.makeText(this,"Select any topic.",Toast.LENGTH_SHORT).show()
                    return
                } else if (TextUtils.isEmpty(query_details.text.toString())) {
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
                                HelpTopic = mSelectedHelpTopic?.HelpTopicName,
                                HelpTopicID = mSelectedHelpTopic?.HelpTopicId.toString(),
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent!!.id) {

            R.id.help_topic_spinner -> {
                mSelectedHelpTopic = parent.getItemAtPosition(position) as ObjHelpTopicList
            }


        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}