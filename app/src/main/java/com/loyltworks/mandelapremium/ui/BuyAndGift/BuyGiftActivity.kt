package com.loyltworks.mandelapremium.ui.BuyAndGift

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityBuyGiftBinding
import com.loyltworks.mandelapremium.model.GetBuyGiftRequest
import com.loyltworks.mandelapremium.model.LstVoucherDetails
import com.loyltworks.mandelapremium.ui.BuyAndGift.adapter.BuyGiftAdapter
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue

class BuyGiftActivity : BaseActivity(), BuyGiftAdapter.OnItemClickListener {

    lateinit var binding: ActivityBuyGiftBinding
    lateinit var buyGiftViewModel: BuyGiftViewModel


    override fun callInitialServices() {

        LoadingDialogue.showDialog(this)

        buyGiftViewModel.getBuyGift(
            GetBuyGiftRequest(
                "262", PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserId.toString()
            )
        )
    }

    override fun callObservers() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyGiftBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set context
        context = this

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        //supportActionBar!!.setHomeAsUpIndicator(upArrow)

        buyGiftViewModel = ViewModelProvider(this).get(BuyGiftViewModel::class.java)



        // GiftAndBuy type observer
        buyGiftViewModel.getBuyGiftListLiveData.observe(this, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.lstVoucherDetails.isNullOrEmpty()) {
                binding.buyGiftRv.adapter = BuyGiftAdapter(it.lstVoucherDetails, this)
            }
        })

        binding.back.setOnClickListener{
            onBackPressed()
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

    override fun onItemClicked(lstVoucherDetails: LstVoucherDetails?) {
        val intent = Intent(context, BuyGiftDetailsActivity::class.java)
        intent.putExtra("BuyGiftDetails", lstVoucherDetails)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

    }


}