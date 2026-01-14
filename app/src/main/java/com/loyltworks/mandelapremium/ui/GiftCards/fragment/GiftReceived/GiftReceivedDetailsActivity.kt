package com.loyltworks.mandelapremium.ui.GiftCards.fragment.GiftReceived

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityGiftReceivedDetailsBinding
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.oneloyalty.goodpack.utils.BlockMultipleClick


class GiftReceivedDetailsActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding: ActivityGiftReceivedDetailsBinding

    override fun callInitialServices() {
    }

    override fun callObservers() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGiftReceivedDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

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

    }

    override fun onClick(v: View?) {
        if (BlockMultipleClick.click()) return

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