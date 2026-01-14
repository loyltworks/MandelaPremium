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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityMyVoucherDetailsBinding
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


class MyVoucherDetailsActivity : BaseActivity(), View.OnClickListener,
    MyVoucherDetailsAdapter.OnItemClickListener,
    MyVoucherDetailsChildAdapter.OnItemChildClickListener {

    lateinit var binding: ActivityMyVoucherDetailsBinding

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
                binding.parentVoucherGiftRv.adapter = MyVoucherDetailsAdapter(it.LstPromotions, this)
            }
        })


        /* Get Albums BY Albums Images ID*/
        giftCardsViewModel.getAlbumImageByIDListLiveData.observe(this, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.LstPromotions.isNullOrEmpty()) {
                //child Adapter
                binding.childVoucherGiftRv.adapter = MyVoucherDetailsChildAdapter(it.LstPromotions, this)
            }
        })


        /* Get Response ReceiverID*/
        giftCardsViewModel.getReceiverIDLiveData.observe(this, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.ReturnMessage.isNullOrEmpty()) {
                if (it.ReturnMessage!!.split("~")[0].toInt() == 1) {
                    binding.ReceiverName.text = /*it.ReturnMessage!!.split("~")[4] + " " +*/
                        it.ReturnMessage!!.split(
                            "~"
                        )[1]

                    ReceiverName = it.ReturnMessage!!.split("~")[1]
                    ReceiverEmail = it.ReturnMessage!!.split("~")[2]
                    ReceiverMobile = it.ReturnMessage!!.split("~")[3]
                    RecevierLoayltyID = it.ReturnMessage!!.split("~")[5]

                } else {
                    binding.receiverId.setText("")
                    Toast.makeText(this, "Receiver Id Not Exist", Toast.LENGTH_SHORT).show()
                }

            }
        })


        /* Get My Voucher Gift Now Submit Response */
        giftCardsViewModel.getMyVoucherGiftNowLiveData.observe(this, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && it.ReturnValue == 1) {
                Toast.makeText(
                    this,
                    "Congratulations! Your Voucher Transferred Successfully. ",
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
        binding = ActivityMyVoucherDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)

        binding.host.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        //set context
        context = this

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        //supportActionBar!!.setHomeAsUpIndicator(upArrow)

        binding.offerDetils.setOnClickListener(this)
        binding.stepsToRedeemTheVoucher.setOnClickListener(this)
        binding.termsAndConditions.setOnClickListener(this)
        binding.redeemableOutlet.setOnClickListener(this)
        binding.btnGiftNow.setOnClickListener(this)
        binding.btnFirstGiftNow.setOnClickListener(this)
        binding.back.setOnClickListener(this)

        giftCardsViewModel = ViewModelProvider(this).get(GiftCardsViewModel::class.java)


        if (intent != null) {
            lstMerchantinfo = intent.getSerializableExtra("lstMerchantinfo") as lstMerchantinfo
            fragmentTab = intent.getIntExtra("GiftCard", 0)
        }


        if(fragmentTab == 1)
            binding.giftToBy.text = " Gifted By"

        if(fragmentTab == 2)
            binding.giftToBy.text = " Gifted To"


        // VIsibility View setup according to tab fragment
        VisibilityViewSetUp()


        binding.giftCardView.visibility = View.VISIBLE
        binding.recyclerviewView.visibility = View.GONE

        if (fragmentTab == 0)
            binding.firstGiftNow.visibility = View.VISIBLE


        binding.radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val rb = findViewById<View>(checkedId) as RadioButton
//            Toast.makeText(applicationContext, rb.text, Toast.LENGTH_SHORT).show()

            if (rb.text == "Gift a Existing Member") {
                CustomerType = 1
                rbExistingMember = true
                ReceiverName = ""
                ReceiverEmail = ""
                ReceiverMobile = ""
                binding.rdReceiverName.setText("")
                binding.rbReceiverMobileNumber.setText("")
                binding.rbReceiverEmail.setText("")

                binding.receiverID.visibility = View.VISIBLE
                binding.receiverNameEmailNumber.visibility = View.GONE
            } else {
                CustomerType = 0
                rbExistingMember = false
                ReceiverName = ""
                ReceiverEmail = ""
                ReceiverMobile = ""
                binding.receiverId.setText("")
                binding.receiverID.visibility = View.GONE
                binding.receiverNameEmailNumber.visibility = View.VISIBLE

            }


        })


        setUpUi()

    }

    private fun VisibilityViewSetUp() {

        when (fragmentTab) {
            0 -> {

                binding.receiverID.visibility = View.VISIBLE
                binding.btnGiftNow.visibility = View.VISIBLE
                binding.giftNowBtnRelative.visibility = View.VISIBLE
                binding.recyclerviewView.visibility = View.VISIBLE
                binding.giftCardView.visibility = View.VISIBLE
                binding.firstGiftNow.visibility = View.VISIBLE
                binding.sentReceiverView.visibility = View.GONE

            }

            1 -> {

                binding.receiverID.visibility = View.GONE
                binding.giftNowBtnRelative.visibility = View.GONE
                binding.recyclerviewView.visibility = View.GONE
                binding.btnGiftNow.visibility = View.GONE
                binding.firstGiftNow.visibility = View.GONE
                binding.giftCardView.visibility = View.VISIBLE
                binding.sentReceiverView.visibility = View.VISIBLE

            }
            2 -> {
                binding.receiverID.visibility = View.GONE
                binding.giftNowBtnRelative.visibility = View.GONE
                binding.recyclerviewView.visibility = View.GONE
                binding.btnGiftNow.visibility = View.GONE
                binding.firstGiftNow.visibility = View.GONE
                binding.giftCardView.visibility = View.VISIBLE
                binding.sentReceiverView.visibility = View.VISIBLE

            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setUpUi() {


        //gift to an Non member VIew

        binding.rdReceiverName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.ReceiverName.text = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })



        //  receiver ID check exist or not
        binding.receiverId.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {

                if (binding.receiverId.text.toString().isEmpty()) {
                    Toast.makeText(this, "Please Enter Receiver ID", Toast.LENGTH_LONG).show()
                    return@OnFocusChangeListener
                }

                giftCardsViewModel.getReceiverIdName(
                    GetReceiverIDRequest(
                        ActionType = "43",
                        ActorId = PreferenceHelper.getLoginDetails(
                            this
                        )?.UserList!![0].UserId.toString(),
                        binding.receiverId.text.toString()
                    )
                )


            }
        }


        //message field enter text show in card description
        binding.messageForReceiver.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.cardDescriptionText.text = s.toString()
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
            ).into(binding.detailsGiftImg)


        Glide.with(this).asBitmap().error(R.drawable.dummy_image)
            .placeholder(R.drawable.dummy_image).load(
                BuildConfig.GIFTCARD_IMAGE_BASE + lstMerchantinfo.Imageurl!!.replace(
                    "~",
                    ""
                )
            ).into(binding.parentImgOnCard)


        binding.giftCardsName.text = lstMerchantinfo.MerchantName!!.split("~")[1]
        binding.validTill.text = lstMerchantinfo.MerchantName!!.split("~")[6]
        binding.cardNumer.text = lstMerchantinfo.MerchantName!!.split("~")[0]
        binding.rewardText.text = lstMerchantinfo.MerchantName!!.split("~")[3]+ " Rewards"

        if (lstMerchantinfo.ReceiverName.isNullOrEmpty()) {
            binding.senderName.text = lstMerchantinfo.GiftedBy

        }else {
            binding.senderName.text = lstMerchantinfo.ReceiverName

        }

        binding.cardNumber.text = lstMerchantinfo.MerchantName!!.split("~")[0]
        binding.validityDate.text = lstMerchantinfo.MerchantName!!.split("~")[6]

        if (fragmentTab == 2) {
            binding.giftedBy.text = lstMerchantinfo.ReceiverName
        }else {
            binding.giftedBy.text = lstMerchantinfo.GiftedBy
        }

        binding.giftedDate.text = lstMerchantinfo.GiftedDate

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            gc_offer_detils.text = Html.fromHtml(lstMerchantinfo.Message, Html.FROM_HTML_MODE_COMPACT)

            if (lstMerchantinfo.Instruction != null)
                binding.gcStepToRedeem.text =
                    Html.fromHtml(lstMerchantinfo.Instruction, Html.FROM_HTML_MODE_COMPACT)

            if (lstMerchantinfo.TermsAndConditions != null)
                binding.gcTermsCondition.text =
                    Html.fromHtml(lstMerchantinfo.TermsAndConditions, Html.FROM_HTML_MODE_COMPACT)

            if (lstMerchantinfo.Locations != null)
                binding.gcRedeemableOutlet.text =
                    Html.fromHtml(lstMerchantinfo.Locations, Html.FROM_HTML_MODE_COMPACT)

        } else {
//            gc_offer_detils.text = Html.fromHtml(lstMerchantinfo.Message)

            if (lstMerchantinfo.Instruction != null)
                binding.gcStepToRedeem.text = Html.fromHtml(lstMerchantinfo.Instruction)

            if (lstMerchantinfo.TermsAndConditions != null)
                binding.gcTermsCondition.text = Html.fromHtml(lstMerchantinfo.TermsAndConditions)

            if (lstMerchantinfo.Locations != null)
                binding.gcRedeemableOutlet.text = Html.fromHtml(lstMerchantinfo.Locations)
        }


    }


    override fun onItemChildClicked(lstPromotions: LstPromotions) {

        Glide.with(this).asBitmap().error(R.drawable.dummy_image)
            .placeholder(R.drawable.dummy_image).load(
                BuildConfig.GIFTCARD_IMAGE_BASE + lstPromotions.ImagePath!!.replace(
                    "~",
                    ""
                )
            ).into(binding.cardTopImg)

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
            R.id.offerDetils -> {

                if (binding.offerDetailsDescriptionView.visibility == View.VISIBLE) {
                    binding.offerDetailsDescriptionView.visibility = View.GONE
                    return
                }

                binding.offerDetailsDescriptionView.visibility = View.VISIBLE
                binding.stepToRedeemTheVoucherDescriptionView.visibility = View.GONE
                binding.tcDescriptionView.visibility = View.GONE
                binding.redeemableOutletDescriptionView.visibility = View.GONE
            }

            R.id.stepsToRedeemTheVoucher -> {

                if (binding.stepToRedeemTheVoucherDescriptionView.visibility == View.VISIBLE) {
                    binding.stepToRedeemTheVoucherDescriptionView.visibility = View.GONE
                    return
                }

                binding.offerDetailsDescriptionView.visibility = View.GONE
                binding.stepToRedeemTheVoucherDescriptionView.visibility = View.VISIBLE
                binding.tcDescriptionView.visibility = View.GONE
                binding.redeemableOutletDescriptionView.visibility = View.GONE
            }

            R.id.termsAndConditions -> {

                if (binding.tcDescriptionView.visibility == View.VISIBLE) {
                    binding.tcDescriptionView.visibility = View.GONE
                    return
                }

                binding.offerDetailsDescriptionView.visibility = View.GONE
                binding.stepToRedeemTheVoucherDescriptionView.visibility = View.GONE
                binding.tcDescriptionView.visibility = View.VISIBLE
                binding.redeemableOutletDescriptionView.visibility = View.GONE
            }
            R.id.redeemableOutlet -> {

                if (binding.redeemableOutletDescriptionView.visibility == View.VISIBLE) {
                    binding.redeemableOutletDescriptionView.visibility = View.GONE
                    return
                }

                binding.offerDetailsDescriptionView.visibility = View.GONE
                binding.stepToRedeemTheVoucherDescriptionView.visibility = View.GONE
                binding.tcDescriptionView.visibility = View.GONE
                binding.redeemableOutletDescriptionView.visibility = View.VISIBLE
            }

            R.id.btnFirstGiftNow -> {
                binding.giftCardView.visibility = View.GONE
                binding.recyclerviewView.visibility = View.VISIBLE
                binding.firstGiftNow.visibility = View.GONE
            }

            R.id.btnGiftNow -> {

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
                        SenderName = binding.senderName.text.toString(),
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
            ReceiverName = binding.rdReceiverName.text.toString()
            ReceiverEmail = binding.rbReceiverEmail.text.toString()
            ReceiverMobile = binding.rbReceiverMobileNumber.text.toString()
        }

        if (rbExistingMember && binding.receiverId.text.isEmpty()) {
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
            } else binding.rbReceiverEmail.error = null
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

        if (binding.messageForReceiver.text.isEmpty()) {
            error += "Please Enter Message for Receiver"
            valid = false
        }


        return valid

    }

    override fun onBackPressed() {

        if (binding.recyclerviewView.visibility == View.VISIBLE) {
            binding.recyclerviewView.visibility = View.GONE
            binding.giftCardView.visibility = View.VISIBLE
            binding.firstGiftNow.visibility = View.VISIBLE


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