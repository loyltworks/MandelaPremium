package com.loyltworks.mandelapremium.ui.MyActivity

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.ui.MyActivity.fragment.MyEarningFragment
import com.loyltworks.mandelapremium.ui.MyActivity.fragment.MyRedemptionFragment
import com.loyltworks.mandelapremium.ui.OfferAndPromotion.OfferAndPromotionsFragment
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.profile.MyPagerAdapter
import kotlinx.android.synthetic.main.activity_my.back
import kotlinx.android.synthetic.main.activity_my.toolbarTitle
import kotlinx.android.synthetic.main.activity_profile.pager
import kotlinx.android.synthetic.main.activity_profile.tablayout


class MyActivity : BaseActivity() {


    override fun callInitialServices() {
    }

    override fun callObservers() {}


    override var context: Context? = null

    private lateinit var viewModel: MyActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        context = this
        viewModel = ViewModelProvider(this).get(MyActivityViewModel::class.java)

        setupViewPager(pager)
        tablayout!!.setupWithViewPager(pager)

        pager.currentItem = intent.extras!!.getInt("MyActivity")

        if(pager.currentItem == 0){
            toolbarTitle.text = "My Earnings"
        }else{
            toolbarTitle.text = "My Redemptions"
        }

        back.setOnClickListener{
            onBackPressed()
        }


    }

    private fun setupViewPager(viewPager: ViewPager) {
        val myPagerAdapter = MyPagerAdapter(supportFragmentManager)
        myPagerAdapter.addFrag(MyEarningFragment(), "My Earnings")
        myPagerAdapter.addFrag(MyRedemptionFragment(), "My Redemptions")
        myPagerAdapter.addFrag(OfferAndPromotionsFragment(), "Promotions")
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