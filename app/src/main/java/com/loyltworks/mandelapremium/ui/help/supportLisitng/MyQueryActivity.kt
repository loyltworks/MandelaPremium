package com.loyltworks.mandelapremium.ui.help.supportLisitng

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.*
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.help.HelpViewModel
import com.loyltworks.mandelapremium.ui.help.newTicket.NewTicketActivity
import com.loyltworks.mandelapremium.ui.help.adapter.MyQueryAdapter
import com.loyltworks.mandelapremium.ui.help.feedback.FeedbackActivity
import com.loyltworks.mandelapremium.ui.help.help_topic_chat_status.QueryChatActivity
import com.loyltworks.mandelapremium.ui.profile.adapter.GenderAdapter
import com.loyltworks.mandelapremium.utils.AppController
import com.loyltworks.mandelapremium.utils.DatePickerBox
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.activity_my_query.*
import kotlinx.android.synthetic.main.activity_my_query.filterBtn
import kotlinx.android.synthetic.main.activity_my_query.fromDate
import kotlinx.android.synthetic.main.activity_my_query.fromDate_tv
import kotlinx.android.synthetic.main.activity_my_query.toDate
import kotlinx.android.synthetic.main.activity_my_query.toDate_tv
import kotlinx.android.synthetic.main.fragment_my_earning.*
import kotlinx.android.synthetic.main.fragment_promotion_tab1.*
import kotlinx.android.synthetic.main.fragment_tab1.*
import kotlinx.android.synthetic.main.fragment_tab2.*
import kotlinx.android.synthetic.main.row_history_notifications.*
import java.util.ArrayList

class MyQueryActivity : BaseActivity(), MyQueryAdapter.OnClickCallBack {

    var FromDate = ""
    var ToDate = ""
    var topicFilter: Int = -1
    var statusFilter: Int = -1

    var listiner = false

    var genderList: ArrayList<GenderSpinner> = ArrayList<GenderSpinner>()


    private lateinit var viewModel: HelpViewModel

    override fun callInitialServices() {

        getQueryListing(
            topicFilter,
            statusFilter,
            "",
            ""
        )

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

    override fun callObservers() {

        viewModel.queryListLiveData.observe(this, androidx.lifecycle.Observer {
            if (it != null && !it.objCustomerAllQueryJsonList.isNullOrEmpty()) {
                LoadingDialogue.dismissDialog()
                myQuery_rv?.adapter = MyQueryAdapter(it, this)
                myQuery_rv?.visibility = View.VISIBLE
                query_error_hint?.visibility = View.GONE

            } else {
                LoadingDialogue.dismissDialog()
                myQuery_rv?.visibility = View.GONE
                query_error_hint?.visibility = View.VISIBLE
            }
        })


        viewModel.topicListLiveData.observe(this, androidx.lifecycle.Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && it.GetHelpTopicsResult.objHelpTopicList != null) {

                val objHelptopicList = ArrayList(it.GetHelpTopicsResult.objHelpTopicList)

                objHelptopicList.add(
                    0,
                    ObjHelpTopicList(
                        "",
                        false,
                        0,
                        -1,
                        "Select topic",
                        false,
                        -1,
                        "Select topic",
                        0
                    )
                )

                select_topic.adapter =
                    CustomSpinnersAdapter2(this, objHelptopicList)

            }
        })

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HelpViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_query)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //set context
        context = this

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)

        query_filterDisplay.visibility = View.GONE

        getQueryListing(
            topicFilter,
            statusFilter,
            "",
            ""
        )

        add_query.setOnClickListener {
            startActivity(Intent(this@MyQueryActivity, NewTicketActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }


        filter_display.setOnClickListener {
            if (query_filterDisplay.visibility == View.VISIBLE) {
                query_filterDisplay.animation =
                    AnimationUtils.loadAnimation(context, R.anim.slide_out_up)
                query_filterDisplay.visibility = View.GONE
            } else if (query_filterDisplay.visibility == View.GONE) {
                query_filterDisplay.animation =
                    AnimationUtils.loadAnimation(context, R.anim.slide_in_down)
                query_filterDisplay.visibility = View.VISIBLE
            }
        }

//        myQuery_rv.adapter = MyQueryAdapter(null, this)


        fromDate.setOnClickListener {
            DatePickerBox.date(this) {
                fromDate_tv.text = it
                FromDate = it
                try {
                    DatePickerBox.dateCompare(this, FromDate, ToDate) {
                        if (!it) {
                            fromDate_tv.text = "DD/MM/YYYY"
                            FromDate = ""
                        }
                    }
                } catch (e: Exception) {
                }
            }
        }


        toDate.setOnClickListener {
            DatePickerBox.date(this) {
                toDate_tv.text = it
                ToDate = it
                DatePickerBox.dateCompare(this, FromDate, ToDate) {
                    if (!it) {
                        toDate_tv.text = "DD/MM/YYYY"
                        ToDate = ""
                    }
                }
            }
        }


        filterBtn.setOnClickListener { v ->
            if (listiner) {
                listiner = false
                getQueryListing(
                    topicFilter, statusFilter,
                    AppController.dateAPIFormats(FromDate).toString(),
                    AppController.dateAPIFormats(ToDate).toString()
                )
            } else if (!TextUtils.isEmpty(FromDate) && !TextUtils.isEmpty(ToDate)) {
                FromDate = fromDate_tv.text.toString()
                ToDate = toDate_tv.text.toString()
                getQueryListing(
                    topicFilter,
                    statusFilter,
                    AppController.dateAPIFormats(FromDate).toString(),
                    AppController.dateAPIFormats(ToDate).toString()
                )
            } else if (topicFilter != -1 || statusFilter != -1) {
                 listiner = true
                getQueryListing(topicFilter, statusFilter,
                    AppController.dateAPIFormats(FromDate).toString(),
                    AppController.dateAPIFormats(ToDate).toString()
                )
            } else
                Toast.makeText(this, "Please select date range", Toast.LENGTH_SHORT).show()

        }


        SpinnerSetUp()


    }

    private fun SpinnerSetUp() {


        val defaultstatus = GenderSpinner()
        defaultstatus.id = -1;
        defaultstatus.name = "Select status";

        val genderSpinner1 = GenderSpinner()
        genderSpinner1.id = 1
        genderSpinner1.name = "Pending"

        val genderSpinner2 = GenderSpinner()
        genderSpinner2.id = 2
        genderSpinner2.name = "Re-Open"

        val genderSpinner3 = GenderSpinner()
        genderSpinner3.id = 3
        genderSpinner3.name = "Resolved"

        val genderSpinner4 = GenderSpinner()
        genderSpinner4.id = 4
        genderSpinner4.name = "Closed"

        val genderSpinner5 = GenderSpinner()
        genderSpinner5.id = 5
        genderSpinner5.name = "Resolved"

        genderList.add(defaultstatus)
        genderList.add(genderSpinner1)
        genderList.add(genderSpinner2)
        genderList.add(genderSpinner3)
        genderList.add(genderSpinner4)
        genderList.add(genderSpinner5)

        select_status.adapter = GenderAdapter(this, android.R.layout.simple_spinner_item, genderList)


        select_topic.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                topicFilter =
                    (parent?.getItemAtPosition(position) as ObjHelpTopicList).HelpTopicId!!

                Log.d("fjhsdhfjds", topicFilter.toString())

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }


        select_status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                statusFilter = (parent?.getItemAtPosition(position) as GenderSpinner).id!!

                Log.d("fjhsdhfjds", statusFilter.toString())

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

    }


    fun getQueryListing(topicFilter: Int, statusFilter: Int, fromDate: String, toDate: String) {

        viewModel.getQueryListLiveData(
            QueryListingRequest(
                "1",
                PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserId.toString(),
                fromDate,
                toDate,
                statusFilter.toString(),
                topicFilter.toString()

            )
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

    override fun onQueryListItemClickResponse(
        itemView: View,
        position: Int,
        productList: List<ObjCustomerAllQueryList?>?
    ) {
        startActivity(
            Intent(
                this@MyQueryActivity,
                QueryChatActivity::class.java
            ).putExtra("SUPPORT_DATA", productList!![position])
        )
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

    }

    override fun onClickFeedback(
        feedbackType: Int,
        position: Int,
        productList: List<ObjCustomerAllQueryList?>?
    ) {
        startActivity(
            Intent(this@MyQueryActivity, FeedbackActivity::class.java).putExtra(
                "SUPPORT_DATA",
                productList!![position]
            )
                .putExtra("FeedbackType", feedbackType)
        )
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

}