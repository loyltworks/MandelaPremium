package com.loyltworks.mandelapremium.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.oneloyalty.goodpack.utils.BlockMultipleClick
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.ObjCustomer
import com.loyltworks.mandelapremium.model.ObjCustomers
import com.loyltworks.mandelapremium.model.UpdateProfileImageRequest
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.profile.fragment.ProfileViewModel
import com.loyltworks.mandelapremium.ui.profile.fragment.Tab1
import com.loyltworks.mandelapremium.ui.profile.fragment.Tab2
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData
import com.vmb.fileSelect.FileType
import kotlinx.android.synthetic.main.activity_profile.back
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity(), View.OnClickListener {


    override fun callInitialServices() {
    }

    override fun callObservers() {
    }

    var addImage = false
    private  var fileExtenstion:String = ""
    private var mProfileImagePath = ""


    private lateinit var profileViewModel: ProfileViewModel



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)

        //set context
        context = this

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)
        //supportActionBar!!.setDisplayShowTitleEnabled(false)
//        toolbar.title = "My Profile"

        profile_image.setOnClickListener(this)

        back.setOnClickListener{
            onBackPressed()
        }

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        //supportActionBar!!.setHomeAsUpIndicator(upArrow)

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        updateProfileImage()

        user_name.text =
            "Welcome " + PreferenceHelper.getDashboardDetails(context as ProfileActivity)!!.lstCustomerFeedBackJson!![0].FirstName
        mobileNumber.text =
            "Mobile number " + PreferenceHelper.getDashboardDetails(context as ProfileActivity)!!.lstCustomerFeedBackJson!![0].CustomerMobile
        points.text =
            PreferenceHelper.getStringValue(context as ProfileActivity, BuildConfig.OverAllPoints)

        Glide.with(this@ProfileActivity)
            .asBitmap()
            .load(PreferenceHelper.getStringValue(this,"ProfileImage"))
            .error(R.drawable.default_person)
            .placeholder(R.drawable.placeholder)
            .into((profile_image))

        /** &&&&&&&&&&&&&&&&&&   Tab Items Declared with MyPageAdapter  &&&&&&&&&&&&&&&&&&&&&&  */

        setupViewPager(pager)
        tablayout!!.setupWithViewPager(pager)

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val myPagerAdapter = MyPagerAdapter(supportFragmentManager)
        myPagerAdapter.addFrag(Tab1(), "General Information")
        myPagerAdapter.addFrag(Tab2(), "Vehicle Informatioin")
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

    override fun onClick(v: View?) {
        if (BlockMultipleClick.click()) return
        when (v!!.id) {

            R.id.profile_image -> {


                // Browse Image or Files
                addImage = true
//            root.editProfileImage.isEnabled= false

                FileSelector.requiredFileTypes(FileType.IMAGES).open(
                    this@ProfileActivity,
                    object : FileSelectorCallBack {
                        @RequiresApi(Build.VERSION_CODES.M)
                        override fun onResponse(fileSelectorData: FileSelectorData) {
                            mProfileImagePath = fileSelectorData.responseInBase64!!
                            Log.d(TAG, "onProfileResponse: $mProfileImagePath")
                            if (mProfileImagePath.isNotEmpty()) {
                                LoadingDialogue.showDialog(this@ProfileActivity)

                                Glide.with(this@ProfileActivity)
                                    .asBitmap()
                                    .load(Base64.decode(mProfileImagePath, Base64.DEFAULT))
                                    .into((profile_image))

                                            profileViewModel.setProfileImageUpdate(
                                    UpdateProfileImageRequest(
                                        ActorId = PreferenceHelper.getLoginDetails(
                                            this@ProfileActivity
                                        )?.UserList!![0].UserId.toString(),
                                        ObjCustomer(
                                            DisplayImage = mProfileImagePath,
                                            LoyaltyId = PreferenceHelper.getLoginDetails(
                                                this@ProfileActivity
                                            )?.UserList!![0].UserName.toString()
                                        )
                                    ))

                            } else {
                                LoadingDialogue.dismissDialog()
                            }
                        }
                    })

            }

        }

    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun updateProfileImage() {

        /*Update Profile Image Observer*/

        profileViewModel.updateProfileImage.observe(this, androidx.lifecycle.Observer {
            if (it != null && it.ReturnMessage!!.length > 0) {
                snackBar(
                    getString(R.string.profile_image_updated),
                    R.color.primaryDark
                )
            } else {
                snackBar(
                    getString(R.string.faile_to_update_profile_image),
                    R.color.red
                )
            }
            LoadingDialogue.dismissDialog()
        })
    }



}