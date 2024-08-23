package com.loyltworks.mandelapremium.ui.help.feedback

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.FeedbackRequest
import com.loyltworks.mandelapremium.model.ObjCustomerAllQueryList
import com.loyltworks.mandelapremium.model.PostChatStatusRequest
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.help.HelpViewModel
import com.loyltworks.mandelapremium.utils.AppController
import com.loyltworks.mandelapremium.utils.DatePickerBox
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.activity_feedback.*
import kotlinx.android.synthetic.main.activity_my_query.*
import kotlinx.android.synthetic.main.activity_my_query.fromDate
import kotlinx.android.synthetic.main.activity_my_query.fromDate_tv
import kotlinx.android.synthetic.main.activity_my_query.toDate
import kotlinx.android.synthetic.main.activity_my_query.toDate_tv
import kotlinx.android.synthetic.main.fragment_my_earning.*
import kotlinx.android.synthetic.main.fragment_my_earning.view.*
import kotlin.math.roundToInt

class FeedbackActivity : BaseActivity() {

    override fun callInitialServices() {
    }

    override fun callObservers() {


        viewModel.postFeedbackResponseLiveData.observe(this, Observer {
                LoadingDialogue.dismissDialog()
            if (it.ReturnMessage.equals("1")) {
                success_message.visibility = View.VISIBLE
            }else{
                Toast.makeText(this, "Something went wrong, please try again later.", Toast.LENGTH_SHORT).show()
            }
        }
        )
    }

    var customerTicketId: Int = -1
    var mRating = "0"



    private lateinit var viewModel: HelpViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        success_message.visibility = View.GONE

        viewModel = ViewModelProvider(this).get(HelpViewModel::class.java)


        //set context
        context = this

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)

        //@SuppressLint("UseCompatLoadingForDrawables") val upArrow =
        //    resources.getDrawable(R.drawable.ic_back_arrow)
        //supportActionBar!!.setHomeAsUpIndicator(upArrow)

        val intent = this.intent
        val bundle = intent.extras
        if (bundle != null) {
            val dataholder: ObjCustomerAllQueryList? = bundle.getSerializable("SUPPORT_DATA") as ObjCustomerAllQueryList?
            val FeedbackType = bundle.getInt("FeedbackType", 1)

            if (dataholder!!.Rating != null && dataholder.Rating != "0") {
                feedback_text.setText(dataholder.Comments)
                feedback_rating_bar.rating = dataholder.Rating.toString().toFloat()
                mRating =   dataholder.Rating.toString()
            }



            if (FeedbackType == 1) {
                feedback_description.text =
                    "We would love to hear your thoughts, \nSuggestions on our query response!"
                feeback_description_bottom_title.text = "Please share your experience with us!"
            } else if (FeedbackType == 0) {
                feedback_description.text = "We would like your feedback to improve our service."
                feeback_description_bottom_title.text =
                    "what is your opinion of the response provided?"
            } else {
                feedback_description.text = "We would like your feedback to improve our service."
                feeback_description_bottom_title.text =
                    "what is your opinion of the response provided?"
            }

            if (dataholder != null) {
                customerTicketId = dataholder.CustomerTicketID!!
            }

            feedback_rating_bar.onRatingBarChangeListener =
                OnRatingBarChangeListener { ratingBar: RatingBar?, rating: Float, fromUser: Boolean ->
                    val rat = rating.roundToInt()
                    mRating = rat.toString()
                }
        }



        success_message.setOnClickListener {
            onBackPressed()
        }

        SumbitBtn()

    }

    private fun SumbitBtn() {

        feedback_submit.setOnClickListener {

            if (feedback_text.text.isNullOrEmpty()) {
                feedback_text.error = "Enter feedback"
                feedback_text.requestFocus()
            }else if (mRating == "0") {
                Toast.makeText(context,"Please give us rating", Toast.LENGTH_SHORT).show()
            }else {
                LoadingDialogue.showDialog(this)
                viewModel.getFeedback(
                    FeedbackRequest(
                        ActorId = PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserId.toString(),
                        ActionType = "1",
                        Comments = feedback_text.text.toString(),
                        CustomerTicketId = customerTicketId.toString(),
                        Rating = mRating
                    )
                )
            }


        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }


}