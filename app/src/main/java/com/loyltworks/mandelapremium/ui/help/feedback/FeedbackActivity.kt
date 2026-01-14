package com.loyltworks.mandelapremium.ui.help.feedback

import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityFeedbackBinding
import com.loyltworks.mandelapremium.model.FeedbackRequest
import com.loyltworks.mandelapremium.model.ObjCustomerAllQueryList
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.help.HelpViewModel
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import kotlin.math.roundToInt

class FeedbackActivity : BaseActivity() {

    lateinit var binding: ActivityFeedbackBinding
    private lateinit var viewModel: HelpViewModel

    override fun callInitialServices() {
    }

    override fun callObservers() {
        
        viewModel.postFeedbackResponseLiveData.observe(this, Observer {
                LoadingDialogue.dismissDialog()
            if (it.ReturnMessage.equals("1")) {
                binding.successMessage.visibility = View.VISIBLE
            }else{
                Toast.makeText(this, "Something went wrong, please try again later.", Toast.LENGTH_SHORT).show()
            }
        }
        )
    }

    var customerTicketId: Int = -1
    var mRating = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        binding.successMessage.visibility = View.GONE

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
                binding.feedbackText.setText(dataholder.Comments)
                binding.feedbackRatingBar.rating = dataholder.Rating.toString().toFloat()
                mRating =   dataholder.Rating.toString()
            }



            if (FeedbackType == 1) {
                binding.feedbackDescription.text =
                    "We would love to hear your thoughts, \nSuggestions on our query response!"
                binding.feebackDescriptionBottomTitle.text = "Please share your experience with us!"
            } else if (FeedbackType == 0) {
                binding.feedbackDescription.text = "We would like your feedback to improve our service."
                binding.feebackDescriptionBottomTitle.text =
                    "what is your opinion of the response provided?"
            } else {
                binding.feedbackDescription.text = "We would like your feedback to improve our service."
                binding.feebackDescriptionBottomTitle.text =
                    "what is your opinion of the response provided?"
            }

            if (dataholder != null) {
                customerTicketId = dataholder.CustomerTicketID!!
            }

            binding.feedbackRatingBar.onRatingBarChangeListener =
                OnRatingBarChangeListener { ratingBar: RatingBar?, rating: Float, fromUser: Boolean ->
                    val rat = rating.roundToInt()
                    mRating = rat.toString()
                }
        }



        binding.successMessage.setOnClickListener {
            onBackPressed()
        }

        SumbitBtn()

    }

    private fun SumbitBtn() {

        binding.feedbackSubmit.setOnClickListener {

            if (binding.feedbackText.text.isNullOrEmpty()) {
                binding.feedbackText.error = "Enter feedback"
                binding.feedbackText.requestFocus()
            }else if (mRating == "0") {
                Toast.makeText(context,"Please give us rating", Toast.LENGTH_SHORT).show()
            }else {
                LoadingDialogue.showDialog(this)
                viewModel.getFeedback(
                    FeedbackRequest(
                        ActorId = PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserId.toString(),
                        ActionType = "1",
                        Comments = binding.feedbackText.text.toString(),
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