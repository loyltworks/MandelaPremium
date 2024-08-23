package com.loyltworks.mandelapremium.ui.BuyAndGift

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.GetBuyGiftRequest
import com.loyltworks.mandelapremium.model.GetWhatsNewRequest
import com.loyltworks.mandelapremium.model.LstPromotionList
import com.loyltworks.mandelapremium.model.LstVoucherDetails
import com.loyltworks.mandelapremium.ui.BuyAndGift.adapter.BuyGiftAdapter
import com.loyltworks.mandelapremium.ui.GiftCards.adapter.MyVoucherAdapter
import com.loyltworks.mandelapremium.ui.GiftCards.fragment.MyVoucher.MyVoucherDetailsActivity
import com.loyltworks.mandelapremium.ui.OfferAndPromotion.OfferPromotionDetailsActivity
import com.loyltworks.mandelapremium.ui.OfferAndPromotion.PromotionViewModel
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.dashboard.DashboardProductsAdapter
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.activity_buy_gift.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_dashboard.*
import kotlinx.android.synthetic.main.fragment_my_voucher.*
import kotlinx.android.synthetic.main.fragment_my_voucher.my_voucher_rv

class BuyGiftActivity : BaseActivity(), BuyGiftAdapter.OnItemClickListener {

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
        setContentView(R.layout.activity_buy_gift)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

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
                buy_gift_rv.adapter = BuyGiftAdapter(it.lstVoucherDetails, this)
            }
        })



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