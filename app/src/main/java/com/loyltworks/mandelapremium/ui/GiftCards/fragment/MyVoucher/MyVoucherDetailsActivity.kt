package com.loyltworks.mandelapremium.ui.GiftCards.fragment.MyVoucher

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.GetAlbumsWithImagesRequest
import com.loyltworks.mandelapremium.model.GetReceiverIDRequest
import com.loyltworks.mandelapremium.model.GiftCardHolderInfoDetails
import com.loyltworks.mandelapremium.model.LstPromotions
import com.loyltworks.mandelapremium.model.MyVoucherGiftCardSubmitRequest
import com.loyltworks.mandelapremium.model.VGiftCardIssueDetails
import com.loyltworks.mandelapremium.model.lstMerchantinfo
import com.loyltworks.mandelapremium.ui.GiftCards.GiftCardsViewModel
import com.loyltworks.mandelapremium.ui.GiftCards.adapter.MyVoucherDetailsAdapter
import com.loyltworks.mandelapremium.ui.GiftCards.adapter.MyVoucherDetailsChildAdapter
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.AlertMessageDialog
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import com.oneloyalty.goodpack.utils.BlockMultipleClick
import kotlinx.android.synthetic.main.activity_my_voucher_details.Receiver_name
import kotlinx.android.synthetic.main.activity_my_voucher_details._gift_now_btn_relative
import kotlinx.android.synthetic.main.activity_my_voucher_details._gift_to_by
import kotlinx.android.synthetic.main.activity_my_voucher_details.back
import kotlinx.android.synthetic.main.activity_my_voucher_details.btn_first_giftNow
import kotlinx.android.synthetic.main.activity_my_voucher_details.btn_giftNow
import kotlinx.android.synthetic.main.activity_my_voucher_details.card_description_text
import kotlinx.android.synthetic.main.activity_my_voucher_details.card_number
import kotlinx.android.synthetic.main.activity_my_voucher_details.card_numer
import kotlinx.android.synthetic.main.activity_my_voucher_details.card_top_img
import kotlinx.android.synthetic.main.activity_my_voucher_details.child_voucher_gift_rv
import kotlinx.android.synthetic.main.activity_my_voucher_details.details_gift_img
import kotlinx.android.synthetic.main.activity_my_voucher_details.first_gift_now
import kotlinx.android.synthetic.main.activity_my_voucher_details.gc_redeemable_outlet
import kotlinx.android.synthetic.main.activity_my_voucher_details.gc_step_to_redeem
import kotlinx.android.synthetic.main.activity_my_voucher_details.gc_terms_condition
import kotlinx.android.synthetic.main.activity_my_voucher_details.gift_card_view
import kotlinx.android.synthetic.main.activity_my_voucher_details.gift_cards_name
import kotlinx.android.synthetic.main.activity_my_voucher_details.gifted_by
import kotlinx.android.synthetic.main.activity_my_voucher_details.gifted_date
import kotlinx.android.synthetic.main.activity_my_voucher_details.host
import kotlinx.android.synthetic.main.activity_my_voucher_details.message_for_receiver
import kotlinx.android.synthetic.main.activity_my_voucher_details.offer_details_description_view
import kotlinx.android.synthetic.main.activity_my_voucher_details.offer_detils
import kotlinx.android.synthetic.main.activity_my_voucher_details.parent_img_on_card
import kotlinx.android.synthetic.main.activity_my_voucher_details.parent_voucher_gift_rv
import kotlinx.android.synthetic.main.activity_my_voucher_details.radioGroup
import kotlinx.android.synthetic.main.activity_my_voucher_details.rb_receiver_email
import kotlinx.android.synthetic.main.activity_my_voucher_details.rb_receiver_mobile_number
import kotlinx.android.synthetic.main.activity_my_voucher_details.rd_receiver_name
import kotlinx.android.synthetic.main.activity_my_voucher_details.receiverID
import kotlinx.android.synthetic.main.activity_my_voucher_details.receiver_id
import kotlinx.android.synthetic.main.activity_my_voucher_details.receiver_name_email_number
import kotlinx.android.synthetic.main.activity_my_voucher_details.recyclerview_view
import kotlinx.android.synthetic.main.activity_my_voucher_details.redeemable_outlet
import kotlinx.android.synthetic.main.activity_my_voucher_details.redeemable_outlet_description_view
import kotlinx.android.synthetic.main.activity_my_voucher_details.reward_text
import kotlinx.android.synthetic.main.activity_my_voucher_details.sender_name
import kotlinx.android.synthetic.main.activity_my_voucher_details.sent_receiver_view
import kotlinx.android.synthetic.main.activity_my_voucher_details.step_to_redeem_the_voucher_description_view
import kotlinx.android.synthetic.main.activity_my_voucher_details.steps_to_redeem_the_voucher
import kotlinx.android.synthetic.main.activity_my_voucher_details.tc_description_view
import kotlinx.android.synthetic.main.activity_my_voucher_details.terms_and_conditions
import kotlinx.android.synthetic.main.activity_my_voucher_details.valid_till
import kotlinx.android.synthetic.main.activity_my_voucher_details.validity_date


class MyVoucherDetailsActivity : BaseActivity(), View.OnClickListener,
    MyVoucherDetailsAdapter.OnItemClickListener,
    MyVoucherDetailsChildAdapter.OnItemChildClickListener {


     var RecevierLoayltyID = ""
    lateinit var giftCardsViewModel: GiftCardsViewModel


    lateinit var lstMerchantinfo: lstMerchantinfo
    var fragmentTab: Int = 0


    var rbExistingMember: Boolean = true
    var CustomerType = 1
    lateinit var ReceiverName: String
    lateinit var ReceiverEmail: String
    lateinit var ReceiverMobile: String
    lateinit var MessageForReceiver: String

    var error = ""


    override fun callInitialServices() {

        LoadingDialogue.showDialog(this)
        giftCardsViewModel.getAlbumWithImage(
            GetAlbumsWithImagesRequest(
                4,
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun callObservers() {


        /* Get AlbumWithImages */
        giftCardsViewModel.getAlbumWithImageListLiveData.observe(this, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.LstPromotions.isNullOrEmpty()) {
                //Parent Adapter
                parent_voucher_gift_rv.adapter = MyVoucherDetailsAdapter(it.LstPromotions, this)
            }
        })


        /* Get Albums BY Albums Images ID*/
        giftCardsViewModel.getAlbumImageByIDListLiveData.observe(this, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.LstPromotions.isNullOrEmpty()) {
                //child Adapter
                child_voucher_gift_rv.adapter = MyVoucherDetailsChildAdapter(it.LstPromotions, this)
            }
        })


        /* Get Response ReceiverID*/
        giftCardsViewModel.getReceiverIDLiveData.observe(this, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.ReturnMessage.isNullOrEmpty()) {
                if (it.ReturnMessage!!.split("~")[0].toInt() == 1) {
                    Receiver_name.text = /*it.ReturnMessage!!.split("~")[4] + " " +*/
                        it.ReturnMessage!!.split(
                            "~"
                        )[1]

                    ReceiverName = it.ReturnMessage!!.split("~")[1]
                    ReceiverEmail = it.ReturnMessage!!.split("~")[2]
                    ReceiverMobile = it.ReturnMessage!!.split("~")[3]
                    RecevierLoayltyID = it.ReturnMessage!!.split("~")[5]

                } else {
                    receiver_id.setText("")
                    Toast.makeText(this, "Receiver ID does not exist", Toast.LENGTH_SHORT).show()
                }

            }
        })


        /* Get My Voucher Gift Now Submit Response */
        giftCardsViewModel.getMyVoucherGiftNowLiveData.observe(this, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && it.ReturnValue == 1) {
                Toast.makeText(
                    this,
                    "Congratulations! Your Voucher Transferred Successfully ",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT)
                    .show()
            }
        })


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_voucher_details)
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)

        host.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        //set context
        context = this

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        //supportActionBar!!.setHomeAsUpIndicator(upArrow)

        offer_detils.setOnClickListener(this)
        steps_to_redeem_the_voucher.setOnClickListener(this)
        terms_and_conditions.setOnClickListener(this)
        redeemable_outlet.setOnClickListener(this)
        btn_giftNow.setOnClickListener(this)
        btn_first_giftNow.setOnClickListener(this)
        back.setOnClickListener(this)

        giftCardsViewModel = ViewModelProvider(this).get(GiftCardsViewModel::class.java)


        if (intent != null) {
            lstMerchantinfo = intent.getSerializableExtra("lstMerchantinfo") as lstMerchantinfo
            fragmentTab = intent.getIntExtra("GiftCard", 0)
        }


        if(fragmentTab == 1)
            _gift_to_by.text = " Gifted By"

        if(fragmentTab == 2)
            _gift_to_by.text = " Gifted To"


        // VIsibility View setup according to tab fragment
        VisibilityViewSetUp()


        gift_card_view.visibility = View.VISIBLE
        recyclerview_view.visibility = View.GONE

        if (fragmentTab == 0)
            first_gift_now.visibility = View.VISIBLE


        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val rb = findViewById<View>(checkedId) as RadioButton
//            Toast.makeText(applicationContext, rb.text, Toast.LENGTH_SHORT).show()

            if (rb.text == "Gift a Existing Member") {
                CustomerType = 1
                rbExistingMember = true
                ReceiverName = ""
                ReceiverEmail = ""
                ReceiverMobile = ""
                rd_receiver_name.setText("")
                rb_receiver_mobile_number.setText("")
                rb_receiver_email.setText("")

                receiverID.visibility = View.VISIBLE
                receiver_name_email_number.visibility = View.GONE
            } else {
                CustomerType = 0
                rbExistingMember = false
                ReceiverName = ""
                ReceiverEmail = ""
                ReceiverMobile = ""
                receiver_id.setText("")
                receiverID.visibility = View.GONE
                receiver_name_email_number.visibility = View.VISIBLE

            }


        })


        setUpUi()

    }

    private fun VisibilityViewSetUp() {

        when (fragmentTab) {
            0 -> {

                receiverID.visibility = View.VISIBLE
                btn_giftNow.visibility = View.VISIBLE
                _gift_now_btn_relative.visibility = View.VISIBLE
                recyclerview_view.visibility = View.VISIBLE
                gift_card_view.visibility = View.VISIBLE
                first_gift_now.visibility = View.VISIBLE
                sent_receiver_view.visibility = View.GONE

            }

            1 -> {

                receiverID.visibility = View.GONE
                _gift_now_btn_relative.visibility = View.GONE
                recyclerview_view.visibility = View.GONE
                btn_giftNow.visibility = View.GONE
                first_gift_now.visibility = View.GONE
                gift_card_view.visibility = View.VISIBLE
                sent_receiver_view.visibility = View.VISIBLE

            }
            2 -> {
                receiverID.visibility = View.GONE
                _gift_now_btn_relative.visibility = View.GONE
                recyclerview_view.visibility = View.GONE
                btn_giftNow.visibility = View.GONE
                first_gift_now.visibility = View.GONE
                gift_card_view.visibility = View.VISIBLE
                sent_receiver_view.visibility = View.VISIBLE

            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setUpUi() {


        //gift to an Non member VIew

        rd_receiver_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Receiver_name.text = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })



        //  receiver ID check exist or not
        receiver_id.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {

                if (receiver_id.text.toString().isEmpty()) {
                    Toast.makeText(this, "Please Enter Receiver ID", Toast.LENGTH_LONG).show()
                    return@OnFocusChangeListener
                }

                giftCardsViewModel.getReceiverIdName(
                    GetReceiverIDRequest(
                        ActionType = "43",
                        ActorId = PreferenceHelper.getLoginDetails(
                            this
                        )?.UserList!![0].UserId.toString(),
                        receiver_id.text.toString()
                    )
                )


            }
        }


        //message field enter text show in card description
        message_for_receiver.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                card_description_text.text = s.toString()
                MessageForReceiver = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        Glide.with(this).asBitmap().error(R.drawable.dummy_image)
            .placeholder(R.drawable.dummy_image).load(
                BuildConfig.GIFTCARD_IMAGE_BASE + lstMerchantinfo.Imageurl!!.replace(
                    "~",
                    ""
                )
            ).into(details_gift_img)


        Glide.with(this).asBitmap().error(R.drawable.dummy_image)
            .placeholder(R.drawable.dummy_image).load(
                BuildConfig.GIFTCARD_IMAGE_BASE + lstMerchantinfo.Imageurl!!.replace(
                    "~",
                    ""
                )
            ).into(parent_img_on_card)


        gift_cards_name.text = lstMerchantinfo.MerchantName!!.split("~")[1]
        valid_till.text = lstMerchantinfo.MerchantName!!.split("~")[6]
        card_numer.text = lstMerchantinfo.MerchantName!!.split("~")[0]
        reward_text.text = lstMerchantinfo.MerchantName!!.split("~")[3]+ " Rewards"

        if (lstMerchantinfo.ReceiverName.isNullOrEmpty()) {
            sender_name.text = lstMerchantinfo.GiftedBy

        }else {
            sender_name.text = lstMerchantinfo.ReceiverName

        }

        card_number.text = lstMerchantinfo.MerchantName!!.split("~")[0]
        validity_date.text = lstMerchantinfo.MerchantName!!.split("~")[6]

        if (fragmentTab == 2) {
            gifted_by.text = lstMerchantinfo.ReceiverName
        }else {
            gifted_by.text = lstMerchantinfo.GiftedBy
        }

        gifted_date.text = lstMerchantinfo.GiftedDate

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            gc_offer_detils.text = Html.fromHtml(lstMerchantinfo.Message, Html.FROM_HTML_MODE_COMPACT)

            if (lstMerchantinfo.Instruction != null)
                gc_step_to_redeem.text =
                    Html.fromHtml(lstMerchantinfo.Instruction, Html.FROM_HTML_MODE_COMPACT)

            if (lstMerchantinfo.TermsAndConditions != null)
                gc_terms_condition.text =
                    Html.fromHtml(lstMerchantinfo.TermsAndConditions, Html.FROM_HTML_MODE_COMPACT)

            if (lstMerchantinfo.Locations != null)
                gc_redeemable_outlet.text =
                    Html.fromHtml(lstMerchantinfo.Locations, Html.FROM_HTML_MODE_COMPACT)

        } else {
//            gc_offer_detils.text = Html.fromHtml(lstMerchantinfo.Message)

            if (lstMerchantinfo.Instruction != null)
                gc_step_to_redeem.text = Html.fromHtml(lstMerchantinfo.Instruction)

            if (lstMerchantinfo.TermsAndConditions != null)
                gc_terms_condition.text = Html.fromHtml(lstMerchantinfo.TermsAndConditions)

            if (lstMerchantinfo.Locations != null)
                gc_redeemable_outlet.text = Html.fromHtml(lstMerchantinfo.Locations)
        }


    }


    override fun onItemChildClicked(lstPromotions: LstPromotions) {

        Glide.with(this).asBitmap().error(R.drawable.dummy_image)
            .placeholder(R.drawable.dummy_image).load(
                BuildConfig.GIFTCARD_IMAGE_BASE + lstPromotions.ImagePath!!.replace(
                    "~",
                    ""
                )
            ).into(card_top_img)

    }

    override fun onItemClicked(lstPromotions: LstPromotions) {

        LoadingDialogue.showDialog(this)
        giftCardsViewModel.getAlbumImageByID(
            GetAlbumsWithImagesRequest(
                4,
            )
        )

    }


    override fun onClick(v: View?) {


        when (v!!.id) {

            R.id.back -> {
                onBackPressed()
            }
            R.id.offer_detils -> {

                if (offer_details_description_view.visibility == View.VISIBLE) {
                    offer_details_description_view.visibility = View.GONE
                    return
                }

                offer_details_description_view.visibility = View.VISIBLE
                step_to_redeem_the_voucher_description_view.visibility = View.GONE
                tc_description_view.visibility = View.GONE
                redeemable_outlet_description_view.visibility = View.GONE
            }

            R.id.steps_to_redeem_the_voucher -> {

                if (step_to_redeem_the_voucher_description_view.visibility == View.VISIBLE) {
                    step_to_redeem_the_voucher_description_view.visibility = View.GONE
                    return
                }

                offer_details_description_view.visibility = View.GONE
                step_to_redeem_the_voucher_description_view.visibility = View.VISIBLE
                tc_description_view.visibility = View.GONE
                redeemable_outlet_description_view.visibility = View.GONE
            }

            R.id.terms_and_conditions -> {

                if (tc_description_view.visibility == View.VISIBLE) {
                    tc_description_view.visibility = View.GONE
                    return
                }

                offer_details_description_view.visibility = View.GONE
                step_to_redeem_the_voucher_description_view.visibility = View.GONE
                tc_description_view.visibility = View.VISIBLE
                redeemable_outlet_description_view.visibility = View.GONE
            }
            R.id.redeemable_outlet -> {

                if (redeemable_outlet_description_view.visibility == View.VISIBLE) {
                    redeemable_outlet_description_view.visibility = View.GONE
                    return
                }

                offer_details_description_view.visibility = View.GONE
                step_to_redeem_the_voucher_description_view.visibility = View.GONE
                tc_description_view.visibility = View.GONE
                redeemable_outlet_description_view.visibility = View.VISIBLE
            }

            R.id.btn_first_giftNow -> {
                gift_card_view.visibility = View.GONE
                recyclerview_view.visibility = View.VISIBLE
                first_gift_now.visibility = View.GONE
            }

            R.id.btn_giftNow -> {

                SubmitGiftNow()

            }


        }

    }


    private fun SubmitGiftNow() {

        if (Validation()) {

            if(CustomerType != 1) {
                RecevierLoayltyID = PreferenceHelper.getStringValue(this, BuildConfig.LoyaltyID)
            }

            LoadingDialogue.showDialog(this)

            giftCardsViewModel.getMyVoucherGiftNow(
                MyVoucherGiftCardSubmitRequest(
                    "",
                    GiftCardNomineeDetails = "",
                    LstGiftCardIssueDetails = "",
                    ActorId = PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserId.toString()
                        .toInt(),
                    ActionType = 32,

                    (
                            VGiftCardIssueDetails(
                                GiftCardIssueId = lstMerchantinfo.MerchantID!!.toInt(),
                                LoyaltyId =RecevierLoayltyID,
                                CardNumber = lstMerchantinfo.MerchantName!!.split("~")[0],
                                TopUpAmount = lstMerchantinfo.MerchantName!!.split("~")[3],
                                IssuedEncashBalance = false,
                                Is_Active = false,
                                CustomerType = CustomerType.toString(),
                                Password = "", // send empty password backend will be added
                                LocationID = PreferenceHelper.getDashboardDetails(this)!!.lstCustomerFeedBackJson!![0].LocationId!!,
                            )),
                    GiftCardHolderInfoDetails(
                        Mobile = ReceiverMobile,
                        Email = ReceiverEmail,
                        PersonalMessage = MessageForReceiver,
                        SenderName = sender_name.text.toString(),
                        RecipentName = ReceiverName,
                        PinType = "",
                        OwnerEmail = PreferenceHelper.getDashboardDetails(this)!!.lstCustomerFeedBackJson!![0].CustomerEmail!!,
                        EvoucherName = lstMerchantinfo.MerchantName!!.split("~")[1],
                        EvoucherNumber = lstMerchantinfo.MerchantName!!.split("~")[0],
                        CardOwnerMobile = PreferenceHelper.getDashboardDetails(this)!!.lstCustomerFeedBackJson!![0].CustomerMobile!!
                    )

                )
            )


        } else {

            AlertMessageDialog.showPopUpDialog(
                this,
                error,
                object : AlertMessageDialog.ForgotCallBackAlert {
                    override fun OK() {
                    }
                })
        }

    }

    private fun Validation(): Boolean {

        var valid = true
        error = ""

        if (!rbExistingMember) {
            ReceiverName = rd_receiver_name.text.toString()
            ReceiverEmail = rb_receiver_email.text.toString()
            ReceiverMobile = rb_receiver_mobile_number.text.toString()
        }

        if (rbExistingMember && receiver_id.text.isEmpty()) {
            error += "Please Enter Receiver ID\n"
            valid = false
        }

        if (!rbExistingMember && ReceiverName.isEmpty()) {
            error += "Please Enter Receiver Name\n"
            valid = false
        }


        if (!rbExistingMember && !TextUtils.isEmpty(ReceiverEmail)) {
            if (!(ReceiverEmail.contains("@") && ReceiverEmail.contains(".") && ReceiverEmail.length > 6)) {
                valid = false
                error += "Enter valid receiver email Id\n"
            } else rb_receiver_email.error = null
        }

        if (!rbExistingMember && ReceiverEmail.isEmpty()) {
            error += "Please Enter Receiver Email\n"
            valid = false
        }

        if (!rbExistingMember && ReceiverMobile.isEmpty()) {
            error += "Please Enter Receiver Mobile\n"
            valid = false
        }

        if (!rbExistingMember && ReceiverMobile.length < 9) {
            error += "Please Enter Valid Mobile\n"
            valid = false
        }

        if (message_for_receiver.text.isEmpty()) {
            error += "Please Enter Message for the Receiver"
            valid = false
        }


        return valid

    }

    override fun onBackPressed() {

        if (recyclerview_view.visibility == View.VISIBLE) {
            recyclerview_view.visibility = View.GONE
            gift_card_view.visibility = View.VISIBLE
            first_gift_now.visibility = View.VISIBLE


        } else {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)

        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}