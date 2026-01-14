package com.loyltworks.mandelapremium.ui.dashboard


import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityDashboardBinding
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
import com.loyltworks.mandelapremium.ui.LocationActivity
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
import java.io.Serializable
import java.text.DecimalFormat


class DashboardActivity : BaseActivity(), View.OnClickListener,
    DashboardProductsAdapter.OnItemClickListener, OffersAdapter.PromotionClickListener {
  
    lateinit var binding: ActivityDashboardBinding

    private lateinit var dashBoardViewModel: DashBoardViewModel
    private lateinit var promotionViewModel: PromotionViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var _attributesDetailsList: List<LstAttributesDetail>
    private lateinit var _listPromotions: List<LstPromotionList>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this

        dashBoardViewModel = ViewModelProvider(this).get(DashBoardViewModel::class.java)
        promotionViewModel = ViewModelProvider(this).get(PromotionViewModel::class.java)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)



        binding.openDrawer.setOnClickListener(this)
        binding.openNotifications.setOnClickListener(this)
        binding.darkBackground.setOnClickListener(this)
        binding.navMyProfile.setOnClickListener(this)
        binding.naviMyearing.setOnClickListener(this)
        binding.naviRedemption.setOnClickListener(this)
        binding.naviAboutMandela.setOnClickListener(this)
        binding.naviMybenefits.setOnClickListener(this)
        binding.naviTermsAndCondition.setOnClickListener(this)
        binding.naviFaq.setOnClickListener(this)
        binding.naviHelp.setOnClickListener(this)
        binding.naviGiftCard.setOnClickListener(this)
        binding.naviRaffle.setOnClickListener(this)
        binding.naviLogout.setOnClickListener(this)
        binding.dashProfile.setOnClickListener(this)
        binding.dashHelp.setOnClickListener(this)
        binding.offerAndPromoions.setOnClickListener(this)
        binding.myGiftCard.setOnClickListener(this)
        binding.buyGift.setOnClickListener(this)
        binding.dashRedemption.setOnClickListener(this)
        binding.dashEarning.setOnClickListener(this)
        binding.dashScan.setOnClickListener(this)
        binding.ourLocation.setOnClickListener(this)

        val gridLayoutManager = GridLayoutManager(this, 3)
        binding.productsRv.layoutManager = gridLayoutManager

        getProfileDetails()
        observers()

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
    }

    fun observers() {

        profileViewModel.myProfileResponse.observe(this) {
            getDashboardDetails()
                if (it != null && !it.GetCustomerDetailsMobileAppResult?.lstCustomerJson.isNullOrEmpty()) {

                    PreferenceHelper.setStringValue(
                        this,
                        "ProfileImage",
                        BuildConfig.PROMO_IMAGE_BASE + "/UploadFiles/CustomerImage/" + it.GetCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.ProfilePicture
                    )

                    Glide.with(this).asBitmap()
                        .load(BuildConfig.PROMO_IMAGE_BASE + "/UploadFiles/CustomerImage/" + it.GetCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.ProfilePicture)
                        .error(R.drawable.default_person).placeholder(R.drawable.default_person)
                        .into((binding.dashProfile))


                    Glide.with(this).asBitmap()
                        .load(BuildConfig.PROMO_IMAGE_BASE + "/UploadFiles/CustomerImage/" + it.GetCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.ProfilePicture)
                        .error(R.drawable.default_person).placeholder(R.drawable.default_person)
                        .into((binding.naviProfileImage))

                }
        }

        dashBoardViewModel.dashboardLiveData.observe(this) {
            getDashboardDetails2()
                if (it != null && !it.lstCustomerFeedBackJson.isNullOrEmpty()) {

                    PreferenceHelper.setStringValue(this, BuildConfig.LoyaltyID, it.lstCustomerFeedBackJson[0].LoyaltyId!!)
                    PreferenceHelper.setDashboardDetails(this, it)

                    binding.userMobileNumber.text = it.lstCustomerFeedBackJson[0].CustomerMobile
                    binding.userName.text = it.lstCustomerFeedBackJson[0].FirstName
                    binding.UserName.text = it.lstCustomerFeedBackJson[0].FirstName

                } else {


                }

        }

        dashBoardViewModel.dashboardLiveData2.observe(this) {
            getOffersAndPromotions()
               if (it != null && !it.ObjCustomerDashboardList.isNullOrEmpty()) {

                    // display dashboard data
                    currencyFormat(it.ObjCustomerDashboardList[0].RedeemablePointsBalance.toString())?.let { it1 ->
                        PreferenceHelper.setStringValue(this, BuildConfig.OverAllPoints, it1)
                    }

                    binding.points.text =
                        currencyFormat(it.ObjCustomerDashboardList[0].RedeemablePointsBalance.toString())
                    binding.userPoints.text =
                        currencyFormat(it.ObjCustomerDashboardList[0].RedeemablePointsBalance.toString())

                } else {


                }

        }

        promotionViewModel.getPromotionListLiveData.observe(this) {
            getBrands()
               if (it != null && !it.LstPromotionJsonList.isNullOrEmpty()) {
                    _listPromotions = it.LstPromotionJsonList!!

                    binding.offersRV.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.offersRV.adapter = OffersAdapter(it.LstPromotionJsonList!!, this)

                } else {

                }

        }

        dashBoardViewModel.brandLogos.observe(this) {
                if (it != null && !it.lstAttributesDetails.isNullOrEmpty()) {

                    _attributesDetailsList = it.lstAttributesDetails


                    binding.productsRv.adapter = DashboardProductsAdapter(it, this)

                }
                LoadingDialogue.dismissDialog()
        }


    }

    override fun onPromotionClicked(lstPromotionJson: LstPromotionList) {
        val intent = Intent(context, OfferPromotionDetailsActivity::class.java)
        intent.putExtra("PromotionDetails", lstPromotionJson)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }


    fun openDrawer() {
        binding.sideMenu.visibility = View.VISIBLE
        binding.sideMenu.animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        binding.darkBackground.visibility = View.VISIBLE
        binding.darkBackground.animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
    }

    fun closeDrawer() {
        binding.sideMenu.visibility = View.GONE
        binding.sideMenu.animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_left)
        binding.darkBackground.visibility = View.GONE
        binding.darkBackground.animation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
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

            R.id.navMyProfile -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                startActivity(Intent(context, ProfileActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.naviHelp -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                startActivity(Intent(context, HelpActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }


            R.id.naviMyearing -> {
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

            R.id.naviRedemption -> {
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

            R.id.naviAboutMandela -> {
                if (BlockMultipleClick.click()) return

                closeDrawer()
                val intent = Intent(context, ProgramInformationActivity::class.java)
                intent.putExtra("MyActivity", "About Mandela Club")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }

            R.id.naviMybenefits -> {
                if (BlockMultipleClick.click()) return

                closeDrawer()
                val intent = Intent(context, ProgramInformationActivity::class.java)
                intent.putExtra("MyActivity", "My Benefits")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }

            R.id.naviTermsAndCondition -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                val intent = Intent(context, ProgramInformationActivity::class.java)
                intent.putExtra("MyActivity", "Terms and Conditions")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }


            R.id.naviFaq -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                val intent = Intent(context, ProgramInformationActivity::class.java)
                intent.putExtra("MyActivity", "FAQ")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.dashProfile -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                startActivity(Intent(context, ProfileActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }

            R.id.myGiftCard -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                startActivity(Intent(context, GiftCardsActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.naviGiftCard -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                startActivity(Intent(context, GiftCardsActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.naviRaffle -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                startActivity(Intent(context, RaffleActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.buyGift -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                startActivity(Intent(context, BuyGiftActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.dashHelp -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                startActivity(Intent(context, HelpActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.offerAndPromoions -> {
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


            R.id.dashRedemption -> {
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

            R.id.dashEarning -> {
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

            R.id.dashScan -> {
                if (BlockMultipleClick.click()) return
                closeDrawer()
                val intent = Intent(context, ScannerActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

            R.id.naviLogout -> {
                if (BlockMultipleClick.click()) return
                // Logout
                closeDrawer()
                PreferenceHelper.clear(applicationContext!!)
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
            }
            R.id.ourLocation -> {
                if (BlockMultipleClick.click()) return
                startActivity(Intent(context, LocationActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
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