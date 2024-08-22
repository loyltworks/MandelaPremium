package com.loyltworks.mandelapremium.ui.MyActivity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.LstAttributesDetail
import com.loyltworks.mandelapremium.model.WishPointRequest
import com.loyltworks.mandelapremium.ui.MyActivity.fragment.MyEarningFragment
import com.loyltworks.mandelapremium.ui.MyActivity.fragment.MyRedemptionFragment
import com.loyltworks.mandelapremium.ui.OfferAndPromotion.OfferAndPromotionsFragment
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.help.HelpViewModel
import com.loyltworks.mandelapremium.ui.profile.MyPagerAdapter
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.activity_my.*
import kotlinx.android.synthetic.main.activity_profile.pager
import kotlinx.android.synthetic.main.activity_profile.tablayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class MyActivity : BaseActivity() {


    override fun callInitialServices() {
    }

    override fun callObservers() {

      /*  viewModel.wisePointLiveData.observe(this, {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.ObjCustomerDashboardList.isNullOrEmpty()){
                product_point.text = it.ObjCustomerDashboardList[0].BehaviourWisePoints.toString()
            }
        })*/

    }

    fun GetWishePoint(locationAttributeID:Int) {

        val wishPointRequest = WishPointRequest()
        wishPointRequest.ActorId = PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserId.toString()
        wishPointRequest.ActionType = "1"
        wishPointRequest.LocationId = locationAttributeID.toString()
        viewModel.wishPointsLiveData(wishPointRequest)

    }


      var FromList = 0

    override var context: Context? = null

    lateinit var lstAttributesDetail: LstAttributesDetail

    private lateinit var viewModel : MyActivityViewModel

     var filterID = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //set context
        context = this

        viewModel = ViewModelProvider(this).get(MyActivityViewModel::class.java)


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar.title = ""

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)

        val b = intent.extras

        val TabValue = b!!.getInt("MyActivity")
        FromList = b.getInt("fromList")
        val Position = b.getInt("SelectedPosition")

        val lstAttributesDetail = b.getSerializable("mulipleProducts") as ArrayList<LstAttributesDetail?>?
        val selectedPosition =  intent.getIntExtra("SelectedPosition",0)
        filterID = lstAttributesDetail?.get(selectedPosition)?.AttributeId!!.toInt()

        Log.d("jfskdjf", FromList.toString())

//        GetWishePoint(lstAttributesDetail?.get(selectedPosition)?.AttributeId!!.toInt())

        /** &&&&&&&&&&&&&&&&&&   Tab Items Declared with MyPageAdapter  &&&&&&&&&&&&&&&&&&&&&&  */

        setupViewPager(pager)
        tablayout!!.setupWithViewPager(pager)

        pager.currentItem = TabValue

        filter_my_activity.setOnClickListener {
            ChooseFragmentToShowingFilterView()
        }
    }


    fun ChooseFragmentToShowingFilterView() {

        /*
            val promotionTab1Fragment: PromotionTab1Fragment? = fragmentManager?.findFragmentByTag("PromotionTab1Fragment") as PromotionTab1Fragment?
                if (promotionTab1Fragment != null && promotionTab1Fragment.isVisible) {
                         val fragment: PromotionTab1Fragment = pager.adapter!!.instantiateItem(pager, pager.currentItem) as PromotionTab1Fragment
                         fragment.FilterDislplay()}
                     */


        val tabPosition = tablayout.selectedTabPosition

        when (tabPosition) {
            0 -> {
                val fragment: MyEarningFragment = pager.adapter!!.instantiateItem(
                    pager,
                    pager.currentItem
                ) as MyEarningFragment
                fragment.FilterDislplay()
            }
            1 -> {
                val fragment: MyRedemptionFragment = pager.adapter!!.instantiateItem(
                    pager,
                    pager.currentItem
                ) as MyRedemptionFragment
                fragment.FilterDislplay()
            }
            2 -> {
                val fragment: OfferAndPromotionsFragment = pager.adapter!!.instantiateItem(
                    pager,
                    pager.currentItem
                ) as OfferAndPromotionsFragment
             //   fragment.ChooseFragmentToShowingFilterView()
            }
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