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
import kotlinx.android.synthetic.main.activity_buy_gift_details.amout_gift_cards
import kotlinx.android.synthetic.main.activity_buy_gift_details.back
import kotlinx.android.synthetic.main.activity_buy_gift_details.current_balance
import kotlinx.android.synthetic.main.activity_buy_gift_details.darkBackground
import kotlinx.android.synthetic.main.activity_buy_gift_details.gift_card_image
import kotlinx.android.synthetic.main.activity_buy_gift_details.gift_card_view1
import kotlinx.android.synthetic.main.activity_buy_gift_details.gift_now11
import kotlinx.android.synthetic.main.activity_buy_gift_details.gift_now_amout_point
import kotlinx.android.synthetic.main.activity_buy_gift_details.giftcard_name
import kotlinx.android.synthetic.main.activity_buy_gift_details.min_max_points
import kotlinx.android.synthetic.main.activity_buy_gift_details.no_gift_voucher
import kotlinx.android.synthetic.main.activity_buy_gift_details.offerDetails
import kotlinx.android.synthetic.main.activity_buy_gift_details.point_balance_edittext
import kotlinx.android.synthetic.main.activity_buy_gift_details.point_editext
import kotlinx.android.synthetic.main.activity_buy_gift_details.point_payOnline_card
import kotlinx.android.synthetic.main.activity_buy_gift_details.range
import kotlinx.android.synthetic.main.activity_buy_gift_details.redeemable_outlet_text
import kotlinx.android.synthetic.main.activity_buy_gift_details.step_to_reteem_voucher
import kotlinx.android.synthetic.main.activity_buy_gift_details.terms_and_condition
import kotlinx.android.synthetic.main.activity_buy_gift_details.voucher_issue_success_view
import kotlinx.android.synthetic.main.activity_buy_gift_details.yes_gift_voucher
import kotlinx.android.synthetic.main.activity_my_voucher_details.host
import kotlinx.android.synthetic.main.activity_my_voucher_details.offer_details_description_view
import kotlinx.android.synthetic.main.activity_my_voucher_details.offer_detils
import kotlinx.android.synthetic.main.activity_my_voucher_details.radioGroup
import kotlinx.android.synthetic.main.activity_my_voucher_details.redeemable_outlet
import kotlinx.android.synthetic.main.activity_my_voucher_details.redeemable_outlet_description_view
import kotlinx.android.synthetic.main.activity_my_voucher_details.step_to_redeem_the_voucher_description_view
import kotlinx.android.synthetic.main.activity_my_voucher_details.steps_to_redeem_the_voucher
import kotlinx.android.synthetic.main.activity_my_voucher_details.tc_description_view
import kotlinx.android.synthetic.main.activity_my_voucher_details.terms_and_conditions


class BuyGiftDetailsActivity : BaseActivity(), View.OnClickListener {


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
                gift_now_amout_point.visibility = View.VISIBLE
                AmountConvertPoints = it.CashBack_Value!!.toInt()
                point_balance_edittext.setText(it.CashBack_Value.toString())
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
                    voucher_issue_success_view.visibility = View.VISIBLE
                    voucher_issue_success_view.animation = AnimationUtils.loadAnimation(this,R.anim.slide_up)
                    darkBackground.visibility = View.VISIBLE
                    darkBackground.animation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
                } else {

                    Toast.makeText(context, "Voucher issued Failed", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(context, "Something went wrong, please try again.", Toast.LENGTH_SHORT).show()
            }
        })


        // for this observe call when user click on Voucher issue successfully click on YES
        giftCardsViewModel.getGiftCardListLiveData.observe(this, Observer {

            voucher_issue_success_view.visibility = View.GONE
            voucher_issue_success_view.animation = AnimationUtils.loadAnimation(this,R.anim.slide_down)
            darkBackground.visibility = View.GONE
            darkBackground.animation = AnimationUtils.loadAnimation(this,R.anim.fade_out)

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
        setContentView(R.layout.activity_buy_gift_details)
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

        if (intent != null)
            lstVoucherDetails = intent.getSerializableExtra("BuyGiftDetails") as LstVoucherDetails


        voucher_issue_success_view.visibility = View.GONE
        darkBackground.visibility = View.GONE



        SetUpUi()



        offer_detils.setOnClickListener(this)
        steps_to_redeem_the_voucher.setOnClickListener(this)
        terms_and_conditions.setOnClickListener(this)
        redeemable_outlet.setOnClickListener(this)
        gift_now11.setOnClickListener(this)
        no_gift_voucher.setOnClickListener(this)
        yes_gift_voucher.setOnClickListener(this)
        gift_now_amout_point.setOnClickListener(this)
        back.setOnClickListener(this)

        buyGiftViewModel = ViewModelProvider(this).get(BuyGiftViewModel::class.java)
        giftCardsViewModel = ViewModelProvider(this).get(GiftCardsViewModel::class.java)




        point_payOnline_card.visibility = View.GONE
        gift_card_view1.visibility = View.VISIBLE



        amout_gift_cards.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                point_balance_edittext.setText("")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val rb = findViewById<View>(checkedId) as RadioButton
//            Toast.makeText(applicationContext, rb.text, Toast.LENGTH_SHORT).show()

            if (rb.text == "Pay by Points") {
                point_editext.visibility = View.VISIBLE
                gift_now_amout_point.visibility = View.VISIBLE
            } else {
                point_editext.visibility = View.GONE
                gift_now_amout_point.visibility = View.GONE
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun SetUpUi() {

        giftcard_name.text = lstVoucherDetails.CardName

        if (lstVoucherDetails.FixedValue != null && lstVoucherDetails.FixedValue != 0) {
            range.visibility = View.GONE
            min_max_points.visibility = View.GONE
            amout_gift_cards.setText(lstVoucherDetails.FixedValue.toString())
            amout_gift_cards.isEnabled = false

        }else {
            range.visibility = View.VISIBLE
            min_max_points.visibility = View.VISIBLE
            amout_gift_cards.isEnabled = true
            min_max_points.text = lstVoucherDetails.MinValue.toString() + " - " + lstVoucherDetails.MaxValue.toString()
        }

     /*   if (lstVoucherDetails.CardCatName.equals("Fixed")) {

            range.text = "Value"
            min_max_points.text = lstVoucherDetails.FixedValue.toString()

        } else {
            min_max_points.text =
                lstVoucherDetails.MinValue.toString() + " - " + lstVoucherDetails.MaxValue.toString()
        }
*/


        current_balance.text = PreferenceHelper.getStringValue(this, BuildConfig.OverAllPoints)

        Glide.with(this).asBitmap().error(R.drawable.dummy_image)
            .placeholder(R.drawable.dummy_image).load(
                BuildConfig.GIFTCARD_IMAGE_BASE + lstVoucherDetails.ImageUrl!!.replace(
                    "~",
                    ""
                )
            ).into(gift_card_image)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            offerDetails.text =
                Html.fromHtml(lstVoucherDetails.Message, Html.FROM_HTML_MODE_COMPACT);
            step_to_reteem_voucher.text =
                Html.fromHtml(lstVoucherDetails.Instruction, Html.FROM_HTML_MODE_COMPACT);
            terms_and_condition.text =
                Html.fromHtml(lstVoucherDetails.TermsAndConditions, Html.FROM_HTML_MODE_COMPACT);
            redeemable_outlet_text.text =
                Html.fromHtml(lstVoucherDetails.LocationName, Html.FROM_HTML_MODE_COMPACT);
        } else {
            offerDetails.text = Html.fromHtml(lstVoucherDetails.Message);
            step_to_reteem_voucher.text = Html.fromHtml(lstVoucherDetails.Instruction);
            terms_and_condition.text = Html.fromHtml(lstVoucherDetails.TermsAndConditions);
            redeemable_outlet_text.text = Html.fromHtml(lstVoucherDetails.LocationName);
        }


        // Request call when focus change amount for getting point
        amout_gift_cards.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {

                val minRange = lstVoucherDetails.MinValue
                val maxRange = lstVoucherDetails.MaxValue

                if (lstVoucherDetails.CardCatName.equals("Fixed")) {

                    if (lstVoucherDetails.FixedValue!!.toInt() != amout_gift_cards.text.toString().toInt()) {
                        point_balance_edittext.setText("")
                        Toast.makeText(this, "Amount range should equal the card value", Toast.LENGTH_LONG).show()
                        return@OnFocusChangeListener
                    }

                }else {
                    if (!amout_gift_cards.text.toString().isNullOrEmpty() && minRange != null && maxRange != null) {
                        if (minRange > amout_gift_cards.text.toString().toInt() || maxRange < amout_gift_cards.text.toString().toInt()) {
                            point_balance_edittext.setText("")
                            Toast.makeText(this, "Amount range should equal the card value", Toast.LENGTH_LONG).show()
                            return@OnFocusChangeListener
                        }
                    }
                }



                LoadingDialogue.showDialog(this)
                buyGiftViewModel.getBuyGiftCaseBackValue(
                    GetCashBackRequest(
                        amout_gift_cards.text.toString(),
                        BuildConfig.MerchantID,
                        BuildConfig.MerchantName
                    )
                )

            }
        }

    }


    override fun onClick(v: View?) {

        when (v!!.id) {
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
            R.id.gift_now11 -> {

                point_payOnline_card.visibility = View.VISIBLE
                gift_now11.visibility = View.GONE
                gift_card_view1.visibility = View.GONE

            }
            R.id.gift_now_amout_point -> {

                var rewardPoint = PreferenceHelper.getStringValue(this, BuildConfig.OverAllPoints)
                    .replace(",", "")

                if (amout_gift_cards.text.toString().isEmpty()) {
                    Toast.makeText(this, "Please enter amount", Toast.LENGTH_LONG).show()
                } else if (point_balance_edittext.text.toString().isNullOrEmpty()) {
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
                                        TopUpAmount = amout_gift_cards.text.toString().toInt(),
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

//                voucher_issue_success_view.visibility = View.VISIBLE

            }


            R.id.no_gift_voucher -> {

                voucher_issue_success_view.visibility = View.GONE
                voucher_issue_success_view.animation = AnimationUtils.loadAnimation(this,R.anim.slide_down)
                darkBackground.visibility = View.GONE
                darkBackground.animation = AnimationUtils.loadAnimation(this,R.anim.fade_out)

                AlertMessageDialog.showPopUpDialog(this,
                    "This E-gift card added to your account.\n" +
                            "For more info check in the Gift Card section. ",
                    object : AlertMessageDialog.ForgotCallBackAlert {
                        override fun OK() {
                            point_payOnline_card.visibility = View.GONE
                            onBackPressed()

                        }
                    })


            }
            R.id.yes_gift_voucher -> {

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

        if (point_payOnline_card.visibility == View.VISIBLE) {
            amout_gift_cards.setText("")
            point_balance_edittext.setText("")

            point_payOnline_card.visibility = View.GONE
            gift_now11.visibility = View.VISIBLE
            gift_card_view1.visibility = View.VISIBLE

        } else/* if (voucher_issue_success_view.visibility != View.VISIBLE) */ {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        }

    }


}