package com.loyltworks.mandelapremium.ui.BuyAndGift

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityBuyGiftDetailsBinding
import com.loyltworks.mandelapremium.model.GetCashBackRequest
import com.loyltworks.mandelapremium.model.GetGiftCardRequest
import com.loyltworks.mandelapremium.model.GetSaveGiftCardRequest
import com.loyltworks.mandelapremium.model.GiftCardIssueDetails
import com.loyltworks.mandelapremium.model.LstVoucherDetails
import com.loyltworks.mandelapremium.ui.GiftCards.GiftCardsViewModel
import com.loyltworks.mandelapremium.ui.GiftCards.fragment.MyVoucher.MyVoucherDetailsActivity
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.AlertMessageDialog
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue


class BuyGiftDetailsActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding: ActivityBuyGiftDetailsBinding

    lateinit var buyGiftViewModel: BuyGiftViewModel

    lateinit var lstVoucherDetails: LstVoucherDetails

    lateinit var giftCardsViewModel: GiftCardsViewModel


    var AmountConvertPoints: Int = 0
    var merchantID: Int = 0

    override fun callInitialServices() {
    }

    override fun callObservers() {


        // GiftAndBuy type observer
        buyGiftViewModel.getBuyGiftCaseBackValueListLiveData.observe(this, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null) {
                binding.giftNowAmoutPoint.visibility = View.VISIBLE
                AmountConvertPoints = it.CashBack_Value!!.toInt()
                binding.pointBalanceEdittext.setText(it.CashBack_Value.toString())
            } else {
//                Toast.makeText(context, "Something went wrong, please try again.", Toast.LENGTH_SHORT).show()
            }
        })


        //BuyNowGift Card
        buyGiftViewModel.getBuyGiftBuyNowLiveData.observe(this, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null) {

                if (it.ReturnMessage!!.split(":")[0].toInt() > 0) {
                    merchantID = it.ReturnMessage!!.split(":")[1].toInt()
                    binding.voucherIssueSuccessView.visibility = View.VISIBLE
                    binding.voucherIssueSuccessView.animation = AnimationUtils.loadAnimation(this,R.anim.slide_up)
                    binding.darkBackground.visibility = View.VISIBLE
                    binding.darkBackground.animation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
                } else {

                    Toast.makeText(context, "Voucher issued Failed", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(context, "Something went wrong, please try again.", Toast.LENGTH_SHORT).show()
            }
        })


        // for this observe call when user click on Voucher issue successfully click on YES
        giftCardsViewModel.getGiftCardListLiveData.observe(this, Observer {

            binding.voucherIssueSuccessView.visibility = View.GONE
            binding.voucherIssueSuccessView.animation = AnimationUtils.loadAnimation(this,R.anim.slide_down)
            binding.darkBackground.visibility = View.GONE
            binding.darkBackground.animation = AnimationUtils.loadAnimation(this,R.anim.fade_out)

            if (it != null && !it.lstMerchantinfo.isNullOrEmpty()) {

                for (element in it.lstMerchantinfo) {
                    if (element.MerchantID == merchantID) {
                        LoadingDialogue.dismissDialog()
                        val intent = Intent(this, MyVoucherDetailsActivity::class.java)
                        intent.putExtra("lstMerchantinfo", element)
                        intent.putExtra("GiftCard", 0)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                        finish()

                    }
                }


            } else {
                LoadingDialogue.dismissDialog()

                Toast.makeText(this, "Something went wrong, Please try again", Toast.LENGTH_SHORT)
                    .show()
            }

        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyGiftDetailsBinding.inflate(layoutInflater)
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

        if (intent != null)
            lstVoucherDetails = intent.getSerializableExtra("BuyGiftDetails") as LstVoucherDetails


        binding.voucherIssueSuccessView.visibility = View.GONE
        binding.darkBackground.visibility = View.GONE



        SetUpUi()



        binding.offerDetils.setOnClickListener(this)
        binding.stepsToRedeemTheVoucher.setOnClickListener(this)
        binding.termsAndConditions.setOnClickListener(this)
        binding.redeemableOutlet.setOnClickListener(this)
        binding.giftNow11.setOnClickListener(this)
        binding.noGiftVoucher.setOnClickListener(this)
        binding.yesGiftVoucher.setOnClickListener(this)
        binding.giftNowAmoutPoint.setOnClickListener(this)
        binding.back.setOnClickListener(this)

        buyGiftViewModel = ViewModelProvider(this).get(BuyGiftViewModel::class.java)
        giftCardsViewModel = ViewModelProvider(this).get(GiftCardsViewModel::class.java)




        binding.pointPayOnlineCard.visibility = View.GONE
        binding.giftCardView1.visibility = View.VISIBLE



        binding.amoutGiftCards.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.pointBalanceEdittext.setText("")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        binding.radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val rb = findViewById<View>(checkedId) as RadioButton
//            Toast.makeText(applicationContext, rb.text, Toast.LENGTH_SHORT).show()

            if (rb.text == "Pay by Points") {
                binding.pointEditext.visibility = View.VISIBLE
                binding.giftNowAmoutPoint.visibility = View.VISIBLE
            } else {
                binding.pointEditext.visibility = View.GONE
                binding.giftNowAmoutPoint.visibility = View.GONE
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun SetUpUi() {

        binding.giftcardName.text = lstVoucherDetails.CardName

        if (lstVoucherDetails.FixedValue != null && lstVoucherDetails.FixedValue != 0) {
            binding.range.visibility = View.GONE
            binding.minMaxPoints.visibility = View.GONE
            binding.amoutGiftCards.setText(lstVoucherDetails.FixedValue.toString())
            binding.amoutGiftCards.isEnabled = false

        }else {
            binding.range.visibility = View.VISIBLE
            binding.minMaxPoints.visibility = View.VISIBLE
            binding.amoutGiftCards.isEnabled = true
            binding.minMaxPoints.text = lstVoucherDetails.MinValue.toString() + " - " + lstVoucherDetails.MaxValue.toString()
        }

     /*   if (lstVoucherDetails.CardCatName.equals("Fixed")) {

            binding.range.text = "Value"
            binding.minMaxPoints.text = lstVoucherDetails.FixedValue.toString()

        } else {
            binding.minMaxPoints.text =
                lstVoucherDetails.MinValue.toString() + " - " + lstVoucherDetails.MaxValue.toString()
        }
*/


        binding.currentBalance.text = PreferenceHelper.getStringValue(this, BuildConfig.OverAllPoints)

        Glide.with(this).asBitmap().error(R.drawable.dummy_image)
            .placeholder(R.drawable.dummy_image).load(
                BuildConfig.GIFTCARD_IMAGE_BASE + lstVoucherDetails.ImageUrl!!.replace(
                    "~",
                    ""
                )
            ).into(binding.giftCardImage)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.offerDetails.text =
                Html.fromHtml(lstVoucherDetails.Message, Html.FROM_HTML_MODE_COMPACT);
            binding.stepToReteemVoucher.text =
                Html.fromHtml(lstVoucherDetails.Instruction, Html.FROM_HTML_MODE_COMPACT);
            binding.termsAndCondition.text =
                Html.fromHtml(lstVoucherDetails.TermsAndConditions, Html.FROM_HTML_MODE_COMPACT);
            binding.redeemableOutletText.text =
                Html.fromHtml(lstVoucherDetails.LocationName, Html.FROM_HTML_MODE_COMPACT);
        } else {
            binding.offerDetails.text = Html.fromHtml(lstVoucherDetails.Message);
            binding.stepToReteemVoucher.text = Html.fromHtml(lstVoucherDetails.Instruction);
            binding.termsAndCondition.text = Html.fromHtml(lstVoucherDetails.TermsAndConditions);
            binding.redeemableOutletText.text = Html.fromHtml(lstVoucherDetails.LocationName);
        }


        // Request call when focus change amount for getting point
        binding.amoutGiftCards.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {

                val minRange = lstVoucherDetails.MinValue
                val maxRange = lstVoucherDetails.MaxValue

                if (lstVoucherDetails.CardCatName.equals("Fixed")) {

                    if (lstVoucherDetails.FixedValue!!.toInt() != binding.amoutGiftCards.text.toString().toInt()) {
                        binding.pointBalanceEdittext.setText("")
                        Toast.makeText(this, "Amount binding.range should be given binding.range", Toast.LENGTH_LONG).show()
                        return@OnFocusChangeListener
                    }

                }else {
                    if (!binding.amoutGiftCards.text.toString().isNullOrEmpty() && minRange != null && maxRange != null) {
                        if (minRange > binding.amoutGiftCards.text.toString().toInt() || maxRange < binding.amoutGiftCards.text.toString().toInt()) {
                            binding.pointBalanceEdittext.setText("")
                            Toast.makeText(this, "Amount binding.range should be given binding.range", Toast.LENGTH_LONG).show()
                            return@OnFocusChangeListener
                        }
                    }
                }



                LoadingDialogue.showDialog(this)
                buyGiftViewModel.getBuyGiftCaseBackValue(
                    GetCashBackRequest(
                        binding.amoutGiftCards.text.toString(),
                        BuildConfig.MerchantID,
                        BuildConfig.MerchantName
                    )
                )

            }
        }

    }


    override fun onClick(v: View?) {

        when (v!!.id) {
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
            R.id.giftNow11 -> {

                binding.pointPayOnlineCard.visibility = View.VISIBLE
                binding.giftNow11.visibility = View.GONE
                binding.giftCardView1.visibility = View.GONE

            }
            R.id.giftNowAmoutPoint -> {

                var rewardPoint = PreferenceHelper.getStringValue(this, BuildConfig.OverAllPoints)
                    .replace(",", "")

                if (binding.amoutGiftCards.text.toString().isEmpty()) {
                    Toast.makeText(this, "Please enter amount", Toast.LENGTH_LONG).show()
                } else if (binding.pointBalanceEdittext.text.toString().isNullOrEmpty()) {
                    Toast.makeText(this, "Please add points value", Toast.LENGTH_LONG).show()
                } else if (AmountConvertPoints > rewardPoint.toInt()) {
                    Toast.makeText(
                        this,
                        "You don't have sufficient balance to buy this gift card",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    LoadingDialogue.showDialog(this)
                    buyGiftViewModel.getBuyGiftBuyNow(
                        GetSaveGiftCardRequest(
                            (
                                    GiftCardIssueDetails(
                                        GiftCardTypeId = lstVoucherDetails.CardTypeId,
                                        GiftCardIssueId = 0,
                                        LoyaltyId = PreferenceHelper.getStringValue(
                                            this,
                                            BuildConfig.LoyaltyID
                                        ),
//                                        CardNumber = ,
                                        TopUpAmount = binding.amoutGiftCards.text.toString().toInt(),
                                        IssuedEncashBalance = true,
                                        Is_Active = false,
                                        IsUsingLoyaltyPoints = false,
                                        MerchantName = BuildConfig.MerchantName,
//                                        MobileNo = lstVoucherDetails.,
                                        GiftCardCatName = lstVoucherDetails.CardName,
                                    )),
                            ActorId = PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserId.toString()
                                .toInt(),
                            ActionType = 0
                        )
                    )

                }

//                binding.voucherIssueSuccessView.visibility = View.VISIBLE

            }


            R.id.noGiftVoucher -> {

                binding.voucherIssueSuccessView.visibility = View.GONE
                binding.voucherIssueSuccessView.animation = AnimationUtils.loadAnimation(this,R.anim.slide_down)
                binding.darkBackground.visibility = View.GONE
                binding.darkBackground.animation = AnimationUtils.loadAnimation(this,R.anim.fade_out)

                AlertMessageDialog.showPopUpDialog(this,
                    "This E-gift card added to your account.\n" +
                            "For more info check in the Gift Card section. ",
                    object : AlertMessageDialog.ForgotCallBackAlert {
                        override fun OK() {
                            binding.pointPayOnlineCard.visibility = View.GONE
                            onBackPressed()

                        }
                    })


            }
            R.id.yesGiftVoucher -> {

                /*   startActivity(Intent(context, MyVoucherDetailsActivity::class.java))
                   overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
   */
                LoadingDialogue.showDialog(this)
                giftCardsViewModel.getGiftCard(
                    GetGiftCardRequest(
                        "259", PreferenceHelper.getLoginDetails(
                            this
                        )?.UserList!![0].UserId.toString()
                    )
                )


            }

            R.id.back -> {
                onBackPressed()
            }


        }


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onBackPressed() {

        if (binding.pointPayOnlineCard.visibility == View.VISIBLE) {
            binding.amoutGiftCards.setText("")
            binding.pointBalanceEdittext.setText("")

            binding.pointPayOnlineCard.visibility = View.GONE
            binding.giftNow11.visibility = View.VISIBLE
            binding.giftCardView1.visibility = View.VISIBLE

        } else/* if (binding.voucherIssueSuccessView.visibility != View.VISIBLE) */ {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        }

    }


}