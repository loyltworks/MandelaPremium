package com.loyltworks.mandelapremium.ui.MyActivity

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityMyBinding
import com.loyltworks.mandelapremium.ui.MyActivity.fragment.MyEarningFragment
import com.loyltworks.mandelapremium.ui.MyActivity.fragment.MyRedemptionFragment
import com.loyltworks.mandelapremium.ui.OfferAndPromotion.OfferAndPromotionsFragment
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.profile.MyPagerAdapter


class MyActivity : BaseActivity() {
    
    lateinit var binding: ActivityMyBinding

    override fun callInitialServices() {
    }

    override fun callObservers() {}


    override var context: Context? = null

    private lateinit var viewModel: MyActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this
        viewModel = ViewModelProvider(this).get(MyActivityViewModel::class.java)

        setupViewPager(binding.pager)
        binding.tablayout!!.setupWithViewPager(binding.pager)

        binding.pager.currentItem = intent.extras!!.getInt("MyActivity")

        if(binding.pager.currentItem == 0){
            binding.toolbarTitle.text = "My Earnings"
        }else{
            binding.toolbarTitle.text = "My Redemptions"
        }

        binding.back.setOnClickListener{
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