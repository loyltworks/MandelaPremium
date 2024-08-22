package com.loyltworks.mandelapremium.ui.GiftCards.fragment.GiftReceived

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.oneloyalty.goodpack.utils.BlockMultipleClick
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import kotlinx.android.synthetic.main.activity_my_voucher_details.*


class GiftReceivedDetailsActivity : BaseActivity(), View.OnClickListener {


    override fun callInitialServices() {
    }

    override fun callObservers() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift_received_details)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //set context
        context = this

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)

        offer_detils.setOnClickListener(this)
        steps_to_redeem_the_voucher.setOnClickListener(this)
        terms_and_conditions.setOnClickListener(this)
        redeemable_outlet.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if (BlockMultipleClick.click()) return

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