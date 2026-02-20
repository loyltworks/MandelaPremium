package com.loyltworks.mandelapremium.ui.profile

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityProfileBinding
import com.loyltworks.mandelapremium.model.ObjCustomerJson
import com.loyltworks.mandelapremium.model.UpdateProfileImageRequest
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.profile.fragment.ProfileViewModel
import com.loyltworks.mandelapremium.ui.profile.fragment.Tab1
import com.loyltworks.mandelapremium.ui.profile.fragment.Tab2
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import com.oneloyalty.goodpack.utils.BlockMultipleClick
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData
import com.vmb.fileSelect.FileType

class ProfileActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding: ActivityProfileBinding

    override fun callInitialServices() {
    }

    override fun callObservers() {
    }

    var addImage = false
    private var fileExtenstion: String = ""
    private var mProfileImagePath = ""


    private lateinit var profileViewModel: ProfileViewModel


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //set context
        context = this

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)
        //supportActionBar!!.setDisplayShowTitleEnabled(false)
//        toolbar.title = "My Profile"

        binding.profileImage.setOnClickListener(this)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        //supportActionBar!!.setHomeAsUpIndicator(upArrow)

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        updateProfileImage()

        binding.userName.text =
            "Welcome " + PreferenceHelper.getDashboardDetails(context as ProfileActivity)!!.lstCustomerFeedBackJson!![0].FirstName
        binding.mobileNumber.text =
            "Mobile number " + PreferenceHelper.getDashboardDetails(context as ProfileActivity)!!.lstCustomerFeedBackJson!![0].CustomerMobile
        binding.points.text =
            PreferenceHelper.getStringValue(context as ProfileActivity, BuildConfig.OverAllPoints)

        Glide.with(this@ProfileActivity).asBitmap()
            .load(PreferenceHelper.getStringValue(this, "ProfileImage"))
            .error(R.drawable.default_person).placeholder(R.drawable.default_person)
            .into((binding.profileImage))

        /** &&&&&&&&&&&&&&&&&&   Tab Items Declared with MyPageAdapter  &&&&&&&&&&&&&&&&&&&&&&  */

        setupViewPager(binding.pager)
        binding.tablayout!!.setupWithViewPager(binding.pager)

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

            R.id.profileImage -> {


                // Browse Image or Files
                addImage = true
//            root.editProfileImage.isEnabled= false

                FileSelector.requiredFileTypes(FileType.IMAGES)
                    .open(this@ProfileActivity, object : FileSelectorCallBack {

                        override fun onResponse(fileSelectorData: FileSelectorData) {
                            mProfileImagePath = fileSelectorData.responseInBase64!!
                            Log.d(TAG, "onProfileResponse: $mProfileImagePath")
                            if (mProfileImagePath.isNotEmpty()) {
                                LoadingDialogue.showDialog(this@ProfileActivity)

                                Glide.with(this@ProfileActivity).asBitmap()
                                    .load(Base64.decode(mProfileImagePath, Base64.DEFAULT))
                                    .into((binding.profileImage))

                                profileViewModel.setProfileImageUpdate(
                                    UpdateProfileImageRequest(
                                        actorId = PreferenceHelper.getLoginDetails(this@ProfileActivity)?.UserList!![0].UserId.toString(),
                                        ObjCustomerJson(
                                            displayImage = mProfileImagePath,
                                            loyaltyId = PreferenceHelper.getLoginDetails(this@ProfileActivity)?.UserList!![0].UserName.toString()
                                        )
                                    )
                                )

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
            if (it != null && it.returnMessage!!.length > 0) {
                Toast.makeText(this,getString(R.string.profile_image_updated),Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this,getString(R.string.faile_to_update_profile_image),Toast.LENGTH_SHORT).show()
            }
            LoadingDialogue.dismissDialog()
        })
    }


}