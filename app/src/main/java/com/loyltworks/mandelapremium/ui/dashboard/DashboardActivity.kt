package com.loyltworks.mandelapremium.ui.dashboard

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
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

    lateinit var promotionViewModel: PromotionViewModel
    private lateinit var profileViewModel: ProfileViewModel

    lateinit var _attributesDetailsList: List<LstAttributesDetail>
    lateinit var _listPromotions: List<LstPromotionList>


    var spanCount = 3 // 4 columns

    var spacing = 5 // 50px

    var includeEdge = false

    override fun callInitialServices() {

//            ProgressDialogue.showDialog(requireContext())

    }

    override fun onResume() {
        super.onResume()

        LoadingDialogue.showDialog(this)
        // call dashboard details
        dashBoardViewModel.getDashBoardData(
            DashboardRequest(
                CustomerDetails(
                    CustomerId = PreferenceHelper.getLoginDetails(
                        this
                    )?.UserList!![0].UserId.toString()
                )
            )
        )

        // profile detail
        profileViewModel.getProfile(
            RegistrationRequest(
                ActionType = "6",
                CustomerId = PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserId.toString()
            )
        )

    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun callObservers() {

        // get profile response
        profileViewModel.myProfileResponse.observe(this, Observer {
            if (it != null && !it.GetCustomerDetailsMobileAppResult.lstCustomerJson.isNullOrEmpty()) {

                PreferenceHelper.setStringValue(
                    this,
                    "ProfileImage",
                    BuildConfig.PROMO_IMAGE_BASE + "/UploadFiles/CustomerImage/" + it.GetCustomerDetailsMobileAppResult.lstCustomerJson[0].ProfilePicture
                )

                Glide.with(this)
                    .asBitmap()
                    .load(BuildConfig.PROMO_IMAGE_BASE + "/UploadFiles/CustomerImage/" + it.GetCustomerDetailsMobileAppResult.lstCustomerJson[0].ProfilePicture)
                    .error(R.drawable.ic_person)
                    .placeholder(R.drawable.placeholder)
                    .into((dash_profile))


                Glide.with(this)
                    .asBitmap()
                    .load(BuildConfig.PROMO_IMAGE_BASE + "/UploadFiles/CustomerImage/" + it.GetCustomerDetailsMobileAppResult.lstCustomerJson[0].ProfilePicture)
                    .error(R.drawable.ic_person)
                    .placeholder(R.drawable.placeholder)
                    .into((navi_profile_image))

            }
        })


        // customer type observer
        dashBoardViewModel.brandLogos.observe(this, Observer {
            if (it != null && !it.lstAttributesDetails.isNullOrEmpty()) {

                _attributesDetailsList = it.lstAttributesDetails
                products_rv.adapter = DashboardProductsAdapter(it, this)

            }
        })


        // dashboard observer
        dashBoardViewModel.dashboardLiveData.observe(this, Observer {
            if (it != null && !it.lstCustomerFeedBackJson.isNullOrEmpty()) {
                // save loyalty id
                PreferenceHelper.setStringValue(
                    this, BuildConfig.LoyaltyID, it.lstCustomerFeedBackJson[0].LoyaltyId!!
                )


                // set in preference helper
                PreferenceHelper.setDashboardDetails(this, it)

                // Call brand images
                dashBoardViewModel.getBrandLogos(
                    AttributeRequest(
                        ActionType = "93",
                        ActorId = PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserId.toString()
                    )
                )

                //mobileNumber.text = "Mobile " + it.lstCustomerFeedBackJson[0].CustomerMobile
                user_mobile_number.text = it.lstCustomerFeedBackJson[0].CustomerMobile
                userName.text = it.lstCustomerFeedBackJson[0].FirstName
                user_name.text = it.lstCustomerFeedBackJson[0].FirstName

                // call dashboard details 2
                dashBoardViewModel.getDashBoardData2(
                    DashboardCustomerRequest(
                        ActionType = "32",
                        IsActive = "true",
                        ActorId = PreferenceHelper.getLoginDetails(
                            this
                        )?.UserList!![0].UserId.toString()
                    )
                )

                // User status check
                val title = if (it.lstCustomerFeedBackJson?.get(0)?.CustomerStatus == 1) {
                    if (it.lstCustomerFeedBackJson?.get(0)?.VerifiedStatus == 1) {
                        if (it.lstCustomerFeedBackJson?.get(0)?.IsDormant == 0) {
                            if (it.lstCustomerFeedBackJson?.get(0)?.IsOnHold == 0) {
                                if (it.lstCustomerFeedBackJson?.get(0)?.IsBlacklisted == 0) {

                                    // Pin change if account verify successful
//                                    if (it.lstCustomerFeedBackJson?.get(0)?.PinStatus == 1)
                                    /*PasswordChangeDialogue.showChangePasswordDialog(
                                        requireContext(),
                                        object : DialogueCallBack {
                                            override fun onResponse(response: String) {
                                                dashBoardViewModel.getLoginData(
                                                    LoginRequest(
                                                        UserName = PreferenceHelper.getDashboardDetails(
                                                            requireContext()
                                                        )?.lstCustomerFeedBackJson?.get(0)?.LoyaltyId,
                                                        Password = response,
                                                        UserActionType = "UpdateChangedPassword",
                                                        UserType = "Customer"
                                                    )
                                                )
                                            }
                                        })*/

                                    // login message display
                                    "Login successful"
                                } else {
                                    "User Blacklisted"
                                }
                            } else {
                                "User On-Hold"
                            }
                        } else {
                            "User Dormant"
                        }
                    } else {
                        "User Unverified"
                    }
                } else {
                    "User InActive"
                }


                if (title != "Login successful") {
                    /* TooltipsDialogue.showDeactivateDialog(
                        requireContext(),
                        title,
                        object : DialogueCallBack {
                            override fun onResponse(response: String) {
                                // logout from dashboard
                                findNavController().navigate(R.id.nav_logoutFragment)
                            }
                        })*/
                }

            } else {
                // display snack bar
                snackBar(
                    "Something went wrong try later",
                    R.color.red
                )

            }
        })

        // dashboard observer 2
        dashBoardViewModel.dashboardLiveData2.observe(this, Observer {
            if (it != null && !it.ObjCustomerDashboardList.isNullOrEmpty()) {

                // display dashboard data
                currencyFormat(it.ObjCustomerDashboardList[0].RedeemablePointsBalance.toString())?.let { it1 ->
                    PreferenceHelper.setStringValue(
                        this,
                        BuildConfig.OverAllPoints, it1
                    )
                }

                points.text = currencyFormat(it.ObjCustomerDashboardList[0].RedeemablePointsBalance.toString())
                user_points.text = currencyFormat(it.ObjCustomerDashboardList[0].RedeemablePointsBalance.toString())

            } else {
                // display snack bar
             /*   snackBar(
                    "Something went wrong try later",
                    R.color.red
                )*/

            }
            LoadingDialogue.dismissDialog()
        })


//        carouselView.setImageClickListener {
//            if (BlockMultipleClick.click()) return@setImageClickListener
//            closeDrawer()
//            val intent = Intent(context, OfferPromotionDetailsActivity::class.java)
//            intent.putExtra("PromotionDetails", _listPromotions[it])
//            startActivity(intent)
//           overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
//        }




        // promotion Lising
        promotionViewModel.getPromotionListLiveData.observe(this, Observer {
                LoadingDialogue.dismissDialog()
                if (it != null && !it.LstPromotionJsonList.isNullOrEmpty()) {
                    _listPromotions = it.LstPromotionJsonList!!

                    offersRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    offersRV.adapter = OffersAdapter(it.LstPromotionJsonList!!,this)


                } else {




                }
            })

    }

    override fun onPromotionClicked(lstPromotionJson: LstPromotionList) {
        val intent = Intent(context, OfferPromotionDetailsActivity::class.java)
        intent.putExtra("PromotionDetails", lstPromotionJson)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

//    lateinit var notification: MenuItem


//    private var drawerLayout: DrawerLayout? = null
//    var toggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)

//        drawerLayout = findViewById(R.id.drawer_layout)

        dashBoardViewModel = ViewModelProvider(this).get(DashBoardViewModel::class.java)
        promotionViewModel = ViewModelProvider(this).get(PromotionViewModel::class.java)

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)





        //set context
        context = this

//        toolbar.post {
//            val d = ResourcesCompat.getDrawable(resources, R.drawable.ic_group_275, null)
//            toolbar.navigationIcon = d
//        }


        openDrawer.setOnClickListener(this)
        darkBackground.setOnClickListener(this)
        nav_myProfile.setOnClickListener(this)


        navi_myearing.setOnClickListener(this)
        navi_redemption.setOnClickListener(this)
        navi_about_mandela.setOnClickListener(this)
        navi_mybenefits.setOnClickListener(this)
        navi_terms_and_condition.setOnClickListener(this)
//        navi_privacy_policy.setOnClickListener(this)
        navi_faq.setOnClickListener(this)
        navi_help.setOnClickListener(this)
        navi_gift_card.setOnClickListener(this)
        navi_raffle.setOnClickListener(this)
        navi_logout.setOnClickListener(this)




        dash_profile.setOnClickListener(this)
        dash_help.setOnClickListener(this)
        offer_and_promoions.setOnClickListener(this)
        //carouselView.setOnClickListener(this)
        my_gift_card.setOnClickListener(this)
        buy_gift.setOnClickListener(this)
        dash_redemption.setOnClickListener(this)
        dash_earning.setOnClickListener(this)
        dash_scan.setOnClickListener(this)




        promotionViewModel.getPromotion(
            GetWhatsNewRequest(
                "259", -1, PreferenceHelper.getLoginDetails(
                    this
                )?.UserList!![0].UserId.toString()
            )
        )


//        toggle = ActionBarDrawerToggle(
//            this,
//            drawerLayout,
//            toolbar,
//            R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close
//        )
//        drawerLayout!!.addDrawerListener(toggle!!)


        val gridLayoutManager = GridLayoutManager(this, 3)
        products_rv.layoutManager = gridLayoutManager
        // For example 10 pixels
        // For example 10 pixels
//        val spaceInPixels = 2
//        products_rv.addItemDecoration(GridSpacingItemDecoration(spaceInPixels))


        // Request camera and location permission
        ActivityCompat.requestPermissions(
            this,
            REQUIRED_PERMISSIONS,
            REQUEST_CODE_PERMISSIONS
        )

    }

    fun openDrawer(){
        sideMenu.visibility = View.VISIBLE
        sideMenu.animation = AnimationUtils.loadAnimation(this,R.anim.slide_in_right)
        darkBackground.visibility = View.VISIBLE
        darkBackground.animation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
    }
    fun closeDrawer(){
        sideMenu.visibility = View.GONE
        sideMenu.animation = AnimationUtils.loadAnimation(this,R.anim.slide_out_left)
        darkBackground.visibility = View.GONE
        darkBackground.animation = AnimationUtils.loadAnimation(this,R.anim.fade_out)
    }

    override fun onClick(v: View) {


        when (v.id) {

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
                    0,
                    LstAttributesDetail(
                        AttributeType = "Select Product",
                        AttributeId = -1
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
                    0,
                    LstAttributesDetail(
                        AttributeType = "Select Product",
                        AttributeId = -1
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
                intent.putExtra("MyActivity", "About MandelaPremium Club")
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

           /* R.id.navi_privacy_policy -> {

                nav_aboutmandela_redemptions.visibility = View.GONE
                navi_view_programInfo_staring_border.visibility = View.INVISIBLE
                navi_programinfo_text.setTextColor(resources.getColor(R.color.textLightWhite))
                navi_programInfo_fullview.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                navi_programinfo_down_arrow.rotation = 0f

                drawerLayout!!.closeDrawer(GravityCompat.START)
                val intent = Intent(context, ProgramInformationActivity::class.java)
                intent.putExtra("MyActivity", "Privacy Policy")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }*/
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
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
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
                    0,
                    LstAttributesDetail(
                        AttributeType = "Select Product",
                        AttributeId = -1
                    )
                )

                val b = Bundle()
                b.putSerializable("mulipleProducts", _attributesDetailsLis_ as Serializable?)

                val intent = Intent(context, OfferAndPromotionActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }
            /*  R.id.carouselView -> {
                  drawerLayout!!.closeDrawer(GravityCompat.START)
                  startActivity(Intent(context, OfferAndPromotionActivity::class.java))
                  overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
              }*/
            R.id.dash_redemption -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()

                val _attributesDetailsLis_ = ArrayList(_attributesDetailsList)

                _attributesDetailsLis_.add(
                    0,
                    LstAttributesDetail(
                        AttributeType = "Select Product",
                        AttributeId = -1
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
                    0,
                    LstAttributesDetail(
                        AttributeType = "Select Product",
                        AttributeId = -1
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
//                intent.putExtra("MyActivity", 0)
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
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    }

    override fun onItemClicked(position: Int, attributesDetailsList: List<LstAttributesDetail>) {

        closeDrawer()

        var _attributesDetailsLis = ArrayList(attributesDetailsList)

        _attributesDetailsLis.add(
            0,
            LstAttributesDetail(
                AttributeType = "Select Product",
                AttributeId = -1
            )
        )

        val b = Bundle()
        b.putInt("MyActivity", 0)
        b.putInt("fromList", 1)
        b.putSerializable("mulipleProducts", _attributesDetailsLis as Serializable?)
        b.putInt("SelectedPosition", position+1)

        val intent = Intent(context, MyActivity::class.java)
        intent.putExtras(b)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }


    //    public boolean onCreateOptionsMenu(Menu menu) {
    //        MenuInflater inflater = getMenuInflater();
    //        inflater.inflate(R.menu.menu, menu);
    //        cart = menu.findItem(R.id.cart);
    //       /* if(context!=null) {
    //            if (context.getClass().getSimpleName().equals("CreateOrderActivity")) {
    //                cart.setVisible(true);
    //            }
    //            else {
    //                cart.setVisible(false);
    //            }
    //        }*/
    ////        setCartBadgeCount(String.valueOf(ProductCatalogoue_DBHelper.getInstance(context).totalNoOfRow()));
    //        return true;
    //    }

    private var click_exit = false

//    override fun onBackPressed() {
//        if (drawerLayout!!.isVisible) {
//            if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
//                drawerLayout!!.closeDrawer(GravityCompat.START)
//            } else {
//                if (click_exit) {
//                    super.onBackPressed()
//                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
//                    finish()
//                } else {
//                    click_exit = true
//                    Toast.makeText(context, "Press again to exis.", Toast.LENGTH_SHORT).show()
//                    Handler().postDelayed(
//                        { click_exit = false }, 2000
//                    )
//                }
//            }
//        } else {
//            super.onBackPressed()
//        }
//    }


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