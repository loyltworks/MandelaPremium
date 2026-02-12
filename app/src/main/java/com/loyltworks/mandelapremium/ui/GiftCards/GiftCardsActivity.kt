package com.loyltworks.mandelapremium.ui.GiftCards

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityGiftCardsBinding
import com.loyltworks.mandelapremium.model.GetGiftCardRequest
import com.loyltworks.mandelapremium.ui.GiftCards.fragment.GiftReceived.GiftReceivedFragment
import com.loyltworks.mandelapremium.ui.GiftCards.fragment.GiftSent.GiftSentFragment
import com.loyltworks.mandelapremium.ui.GiftCards.fragment.MyVoucher.MyVoucherFragment
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.profile.MyPagerAdapter
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue

class GiftCardsActivity : BaseActivity() {

    lateinit var binding: ActivityGiftCardsBinding

    lateinit var giftCardsViewModel: GiftCardsViewModel


    override fun callInitialServices() {


        LoadingDialogue.showDialog(this)
        giftCardsViewModel.getGiftCard(
            GetGiftCardRequest(
                "259", PreferenceHelper.getLoginDetails(
                    this
                )?.UserList!![0].UserId.toString()
            )
        )
    }

    override fun callObservers() {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGiftCardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)

        //set context
        context = this

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        //supportActionBar!!.setHomeAsUpIndicator(upArrow)


        giftCardsViewModel = ViewModelProvider(this).get(GiftCardsViewModel::class.java)

        /** &&&&&&&&&&&&&&&&&&   Tab Items Declared with MyPageAdapter  &&&&&&&&&&&&&&&&&&&&&&  */

        setupViewPager(binding.pager)
        binding.tablayout.setupWithViewPager(binding.pager)

        binding.back.setOnClickListener{
            onBackPressed()
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {

        val myPagerAdapter = MyPagerAdapter(supportFragmentManager)
        myPagerAdapter.addFrag(MyVoucherFragment(), "My Vouchers")
        myPagerAdapter.addFrag(GiftReceivedFragment(), "Gift Received")
        myPagerAdapter.addFrag(GiftSentFragment(), "Gift Sent")
        viewPager.adapter = myPagerAdapter
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