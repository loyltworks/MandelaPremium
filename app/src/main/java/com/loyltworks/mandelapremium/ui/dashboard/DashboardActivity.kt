package com.loyltworks.mandelapremium.ui.dashboard

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.AttributeRequest
import com.loyltworks.mandelapremium.model.CustomerDetails
import com.loyltworks.mandelapremium.model.DashboardCustomerRequest
import com.loyltworks.mandelapremium.model.DashboardRequest
import com.loyltworks.mandelapremium.model.GetWhatsNewRequest
import com.loyltworks.mandelapremium.model.LstAttributesDetail
import com.loyltworks.mandelapremium.model.LstPromotionList
import com.loyltworks.mandelapremium.model.RegistrationRequest
import com.loyltworks.mandelapremium.ui.BuyAndGift.BuyGiftActivity
import com.loyltworks.mandelapremium.ui.GiftCards.GiftCardsActivity
import com.loyltworks.mandelapremium.ui.Login.LoginActivity
import com.loyltworks.mandelapremium.ui.MyActivity.MyActivity
import com.loyltworks.mandelapremium.ui.OfferAndPromotion.OfferAndPromotionActivity
import com.loyltworks.mandelapremium.ui.OfferAndPromotion.OfferPromotionDetailsActivity
import com.loyltworks.mandelapremium.ui.OfferAndPromotion.PromotionViewModel
import com.loyltworks.mandelapremium.ui.OffersAdapter
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.help.HelpActivity
import com.loyltworks.mandelapremium.ui.notification.HistoryNotificationActivity
import com.loyltworks.mandelapremium.ui.profile.ProfileActivity
import com.loyltworks.mandelapremium.ui.profile.fragment.ProfileViewModel
import com.loyltworks.mandelapremium.ui.programinformation.ProgramInformationActivity
import com.loyltworks.mandelapremium.ui.raffle.RaffleActivity
import com.loyltworks.mandelapremium.ui.scanner.ScannerActivity
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.Vibrator
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import com.oneloyalty.goodpack.utils.BlockMultipleClick
import kotlinx.android.synthetic.main.activity_dashboard.buy_gift
import kotlinx.android.synthetic.main.activity_dashboard.darkBackground
import kotlinx.android.synthetic.main.activity_dashboard.dash_earning
import kotlinx.android.synthetic.main.activity_dashboard.dash_help
import kotlinx.android.synthetic.main.activity_dashboard.dash_profile
import kotlinx.android.synthetic.main.activity_dashboard.dash_redemption
import kotlinx.android.synthetic.main.activity_dashboard.dash_scan
import kotlinx.android.synthetic.main.activity_dashboard.my_gift_card


import kotlinx.android.synthetic.main.activity_dashboard.nav_myProfile
import kotlinx.android.synthetic.main.activity_dashboard.navi_about_mandela

import kotlinx.android.synthetic.main.activity_dashboard.navi_faq
import kotlinx.android.synthetic.main.activity_dashboard.navi_gift_card
import kotlinx.android.synthetic.main.activity_dashboard.navi_help
import kotlinx.android.synthetic.main.activity_dashboard.navi_logout

import kotlinx.android.synthetic.main.activity_dashboard.navi_mybenefits
import kotlinx.android.synthetic.main.activity_dashboard.navi_myearing
import kotlinx.android.synthetic.main.activity_dashboard.navi_profile_image

import kotlinx.android.synthetic.main.activity_dashboard.navi_raffle
import kotlinx.android.synthetic.main.activity_dashboard.navi_redemption
import kotlinx.android.synthetic.main.activity_dashboard.navi_terms_and_condition

import kotlinx.android.synthetic.main.activity_dashboard.offer_and_promoions
import kotlinx.android.synthetic.main.activity_dashboard.offersRV
import kotlinx.android.synthetic.main.activity_dashboard.openDrawer
import kotlinx.android.synthetic.main.activity_dashboard.openNotifications
import kotlinx.android.synthetic.main.activity_dashboard.points
import kotlinx.android.synthetic.main.activity_dashboard.products_rv
import kotlinx.android.synthetic.main.activity_dashboard.sideMenu
import kotlinx.android.synthetic.main.activity_dashboard.userName
import kotlinx.android.synthetic.main.activity_dashboard.user_mobile_number
import kotlinx.android.synthetic.main.activity_dashboard.user_name
import kotlinx.android.synthetic.main.activity_dashboard.user_points
import java.io.Serializable
import java.text.DecimalFormat


class DashboardActivity : BaseActivity(), View.OnClickListener,
    DashboardProductsAdapter.OnItemClickListener, OffersAdapter.PromotionClickListener {

    private lateinit var dashBoardViewModel: DashBoardViewModel
    private lateinit var promotionViewModel: PromotionViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var _attributesDetailsList: List<LstAttributesDetail>
    private lateinit var _listPromotions: List<LstPromotionList>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        context = this

        dashBoardViewModel = ViewModelProvider(this).get(DashBoardViewModel::class.java)
        promotionViewModel = ViewModelProvider(this).get(PromotionViewModel::class.java)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)



        openDrawer.setOnClickListener(this)
        openNotifications.setOnClickListener(this)
        darkBackground.setOnClickListener(this)
        nav_myProfile.setOnClickListener(this)
        navi_myearing.setOnClickListener(this)
        navi_redemption.setOnClickListener(this)
        navi_about_mandela.setOnClickListener(this)
        navi_mybenefits.setOnClickListener(this)
        navi_terms_and_condition.setOnClickListener(this)
        navi_faq.setOnClickListener(this)
        navi_help.setOnClickListener(this)
        navi_gift_card.setOnClickListener(this)
        navi_raffle.setOnClickListener(this)
        navi_logout.setOnClickListener(this)
        dash_profile.setOnClickListener(this)
        dash_help.setOnClickListener(this)
        offer_and_promoions.setOnClickListener(this)
        my_gift_card.setOnClickListener(this)
        buy_gift.setOnClickListener(this)
        dash_redemption.setOnClickListener(this)
        dash_earning.setOnClickListener(this)
        dash_scan.setOnClickListener(this)

        val gridLayoutManager = GridLayoutManager(this, 3)
        products_rv.layoutManager = gridLayoutManager

        getProfileDetails()


        // Request camera and location permission
        ActivityCompat.requestPermissions(
            this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
        )

    }

    private fun getProfileDetails() {

        LoadingDialogue.showDialog(this)

        profileViewModel.getProfile(
            RegistrationRequest(
                ActionType = "6",
                CustomerId = PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserId.toString()
            )
        )
    }

    private fun getDashboardDetails() {
        dashBoardViewModel.getDashBoardData(
            DashboardRequest(
                CustomerDetails(CustomerId = PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserId.toString())
            )
        )
    }

    private fun getDashboardDetails2() {
        dashBoardViewModel.getDashBoardData2(
            DashboardCustomerRequest(
                ActionType = "32",
                IsActive = "true",
                ActorId = PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserId.toString()
            )
        )
    }

    private fun getOffersAndPromotions() {
        promotionViewModel.getPromotion(
            GetWhatsNewRequest(
                "259", -1, PreferenceHelper.getLoginDetails(
                    this
                )?.UserList!![0].UserId.toString()
            )
        )
    }

    private fun getBrands() {
        dashBoardViewModel.getBrandLogos(
            AttributeRequest(
                ActionType = "93",
                ActorId = PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserId.toString()
            )
        )
    }


    override fun callInitialServices() {}


    override fun callObservers() {

        profileViewModel.myProfileResponse.observe(this) {

            if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null && !it.GetCustomerDetailsMobileAppResult?.lstCustomerJson.isNullOrEmpty()) {

                    PreferenceHelper.setStringValue(
                        this,
                        "ProfileImage",
                        BuildConfig.PROMO_IMAGE_BASE + "/UploadFiles/CustomerImage/" + it.GetCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.ProfilePicture
                    )

                    Glide.with(this).asBitmap()
                        .load(BuildConfig.PROMO_IMAGE_BASE + "/UploadFiles/CustomerImage/" + it.GetCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.ProfilePicture)
                        .error(R.drawable.default_person).placeholder(R.drawable.default_person)
                        .into((dash_profile))


                    Glide.with(this).asBitmap()
                        .load(BuildConfig.PROMO_IMAGE_BASE + "/UploadFiles/CustomerImage/" + it.GetCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.ProfilePicture)
                        .error(R.drawable.default_person).placeholder(R.drawable.default_person)
                        .into((navi_profile_image))

                }
                getDashboardDetails()
            }


        }

        dashBoardViewModel.dashboardLiveData.observe(this) {
            if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null && !it.lstCustomerFeedBackJson.isNullOrEmpty()) {

                    PreferenceHelper.setStringValue(
                        this, BuildConfig.LoyaltyID, it.lstCustomerFeedBackJson[0].LoyaltyId!!
                    )
                    PreferenceHelper.setDashboardDetails(this, it)

                    user_mobile_number.text = it.lstCustomerFeedBackJson[0].CustomerMobile
                    userName.text = it.lstCustomerFeedBackJson[0].FirstName
                    user_name.text = it.lstCustomerFeedBackJson[0].FirstName

                } else {


                }
                getDashboardDetails2()
            }


        }

        dashBoardViewModel.dashboardLiveData2.observe(this) {
            if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null && !it.ObjCustomerDashboardList.isNullOrEmpty()) {

                    // display dashboard data
                    currencyFormat(it.ObjCustomerDashboardList[0].RedeemablePointsBalance.toString())?.let { it1 ->
                        PreferenceHelper.setStringValue(this, BuildConfig.OverAllPoints, it1)
                    }

                    points.text =
                        currencyFormat(it.ObjCustomerDashboardList[0].RedeemablePointsBalance.toString())
                    user_points.text =
                        currencyFormat(it.ObjCustomerDashboardList[0].RedeemablePointsBalance.toString())

                } else {


                }

                getOffersAndPromotions()
            }


        }

        promotionViewModel.getPromotionListLiveData.observe(this) {
            if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null && !it.LstPromotionJsonList.isNullOrEmpty()) {
                    _listPromotions = it.LstPromotionJsonList!!

                    offersRV.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    offersRV.adapter = OffersAdapter(it.LstPromotionJsonList!!, this)


                } else {


                }
                getBrands()
            }

        }

        dashBoardViewModel.brandLogos.observe(this) {
            if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null && !it.lstAttributesDetails.isNullOrEmpty()) {

                    _attributesDetailsList = it.lstAttributesDetails


                    products_rv.adapter = DashboardProductsAdapter(it, this)

                }
                LoadingDialogue.dismissDialog()
            }

        }


    }

    override fun onPromotionClicked(lstPromotionJson: LstPromotionList) {
        val intent = Intent(context, OfferPromotionDetailsActivity::class.java)
        intent.putExtra("PromotionDetails", lstPromotionJson)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }


    fun openDrawer() {
        sideMenu.visibility = View.VISIBLE
        sideMenu.animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        darkBackground.visibility = View.VISIBLE
        darkBackground.animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
    }

    fun closeDrawer() {
        sideMenu.visibility = View.GONE
        sideMenu.animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_left)
        darkBackground.visibility = View.GONE
        darkBackground.animation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
    }

    override fun onClick(v: View) {


        when (v.id) {

            R.id.openNotifications -> {
                startActivity(Intent(this, HistoryNotificationActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.darkBackground -> {
                Vibrator.vibrate(this@DashboardActivity)
                closeDrawer()
            }

            R.id.openDrawer -> {
                if (BlockMultipleClick.click()) return
                Vibrator.vibrate(this@DashboardActivity)
                openDrawer()
            }

            R.id.nav_myProfile -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                startActivity(Intent(context, ProfileActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.navi_help -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                startActivity(Intent(context, HelpActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }


            R.id.navi_myearing -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()

                val _attributesDetailsLis_ = ArrayList(_attributesDetailsList)

                _attributesDetailsLis_.add(
                    0, LstAttributesDetail(
                        AttributeType = "Select Product", AttributeId = -1
                    )
                )

                val b = Bundle()
                b.putInt("MyActivity", 0)
                b.putInt("fromList", 0)
                b.putSerializable("mulipleProducts", _attributesDetailsLis_ as Serializable?)

                val intent = Intent(context, MyActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }

            R.id.navi_redemption -> {
                if (BlockMultipleClick.click()) return

                closeDrawer()
                val _attributesDetailsLis_ = ArrayList(_attributesDetailsList)

                _attributesDetailsLis_.add(
                    0, LstAttributesDetail(
                        AttributeType = "Select Product", AttributeId = -1
                    )
                )

                val b = Bundle()
                b.putInt("MyActivity", 1)
                b.putInt("fromList", 0)
                b.putSerializable("mulipleProducts", _attributesDetailsLis_ as Serializable?)

                val intent = Intent(context, MyActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }

            R.id.navi_about_mandela -> {
                if (BlockMultipleClick.click()) return

                closeDrawer()
                val intent = Intent(context, ProgramInformationActivity::class.java)
                intent.putExtra("MyActivity", "About Mandela Club")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }

            R.id.navi_mybenefits -> {
                if (BlockMultipleClick.click()) return

                closeDrawer()
                val intent = Intent(context, ProgramInformationActivity::class.java)
                intent.putExtra("MyActivity", "My Benefits")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }

            R.id.navi_terms_and_condition -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                val intent = Intent(context, ProgramInformationActivity::class.java)
                intent.putExtra("MyActivity", "Terms and Conditions")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }


            R.id.navi_faq -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                val intent = Intent(context, ProgramInformationActivity::class.java)
                intent.putExtra("MyActivity", "FAQ")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.dash_profile -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                startActivity(Intent(context, ProfileActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }

            R.id.my_gift_card -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                startActivity(Intent(context, GiftCardsActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.navi_gift_card -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                startActivity(Intent(context, GiftCardsActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.navi_raffle -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                startActivity(Intent(context, RaffleActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.buy_gift -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                startActivity(Intent(context, BuyGiftActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.dash_help -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                startActivity(Intent(context, HelpActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.offer_and_promoions -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                val _attributesDetailsLis_ = ArrayList(_attributesDetailsList)

                _attributesDetailsLis_.add(
                    0, LstAttributesDetail(
                        AttributeType = "Select Product", AttributeId = -1
                    )
                )

                val b = Bundle()
                b.putSerializable("mulipleProducts", _attributesDetailsLis_ as Serializable?)

                val intent = Intent(context, OfferAndPromotionActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }


            R.id.dash_redemption -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()

                val _attributesDetailsLis_ = ArrayList(_attributesDetailsList)

                _attributesDetailsLis_.add(
                    0, LstAttributesDetail(
                        AttributeType = "Select Product", AttributeId = -1
                    )
                )

                val b = Bundle()
                b.putInt("MyActivity", 1)
                b.putInt("fromList", 0)
                b.putSerializable("mulipleProducts", _attributesDetailsLis_ as Serializable?)

                val intent = Intent(context, MyActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)


            }

            R.id.dash_earning -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                val _attributesDetailsLis_ = ArrayList(_attributesDetailsList)

                _attributesDetailsLis_.add(
                    0, LstAttributesDetail(
                        AttributeType = "Select Product", AttributeId = -1
                    )
                )

                val b = Bundle()
                b.putInt("MyActivity", 0)
                b.putInt("fromList", 0)
                b.putSerializable("mulipleProducts", _attributesDetailsLis_ as Serializable?)

                val intent = Intent(context, MyActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }

            R.id.dash_scan -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                val intent = Intent(context, ScannerActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.navi_logout -> {
                if (BlockMultipleClick.click()) return
                // Logout
                closeDrawer()
                PreferenceHelper.clear(applicationContext!!)
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
            }

        }
    }


    companion object {

        private const val TAG = "DashBoardActivity"

        const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION
        )

    }

    override fun onItemClicked(position: Int, attributesDetailsList: List<LstAttributesDetail>) {

        val _attributesDetailsLis = ArrayList(attributesDetailsList)

        _attributesDetailsLis.add(
            0, LstAttributesDetail(
                AttributeType = "Select Product", AttributeId = -1
            )
        )

        val b = Bundle()
        b.putInt("MyActivity", 0)
        b.putInt("fromList", 1)
        b.putSerializable("mulipleProducts", _attributesDetailsLis as Serializable?)
        b.putInt("SelectedPosition", position + 1)

        val intent = Intent(context, MyActivity::class.java)
        intent.putExtras(b)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }


    /** Currency formatter*/
    fun currencyFormat(amount: String?): String? {
        return try {
            val formatter = DecimalFormat("###,###,##0")
            formatter.format(amount?.toDouble())
        } catch (e: Exception) {
            e.printStackTrace()
            "0.00"
        }
    }


}