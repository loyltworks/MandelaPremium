package com.loyltworks.mandelapremium.ui.profile.fragment

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.FragmentTab1Binding
import com.loyltworks.mandelapremium.model.*
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity.Companion.TAG
import com.loyltworks.mandelapremium.ui.profile.ProfileActivity
import com.loyltworks.mandelapremium.ui.profile.adapter.*
import com.loyltworks.mandelapremium.utils.DateFormat
import com.loyltworks.mandelapremium.utils.DatePickerFragment
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import com.oneloyalty.goodpack.utils.BlockMultipleClick
import java.util.*

class Tab1 : Fragment(), DatePickerFragment.CalenderCallBack ,View.OnClickListener, OnItemSelectedListener{

    lateinit var binding: FragmentTab1Binding
    private lateinit var viewModel: ProfileViewModel
    var spinnerArrayAdapter: ArrayAdapter<String>? = null
    var DOB = ""
    var AddressID = ""
    var CustomerID = ""
    var CustomerDetailID = ""
    var LoyaltyId = ""
    var cityID : Int = -1
    var i = 0
    var j = 0
    var k = 0
    var l = 0
    var m = 0

    private var mSelectedCountryDetail: LstCountryDetail? = null
    private var mSelectedProfessor: LstAttributesDetail? = null
    private var mSelectedAgeGroup: LstAttributesDetail? = null
    private var mSelectedCity: CityList? = null
    private var mSelectedGender: GenderSpinner? = null

    var countryList = mutableListOf<LstCountryDetail>()
    var cityList = mutableListOf<CityList>()
    val ageGroupList = mutableListOf<LstAttributesDetail>()
    val professionList = mutableListOf<LstAttributesDetail>()

    var genderList: ArrayList<GenderSpinner> = ArrayList<GenderSpinner>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding = FragmentTab1Binding.inflate(layoutInflater)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.countrySpinner.onItemSelectedListener = this;
        binding.citySpinner.onItemSelectedListener = this
        binding.professionSpinner.onItemSelectedListener = this
        binding.ageGroup.onItemSelectedListener = this
        binding.genderSpinner.onItemSelectedListener = this

        binding.updateBtn.setOnClickListener(this)

        binding.dob.setOnClickListener {
            if (BlockMultipleClick.click()) return@setOnClickListener
            val dateD = DatePickerFragment(
                requireActivity(),
                this@Tab1,
                1
            )
            dateD.show(requireActivity().fragmentManager.beginTransaction(), "DATE PICKER")
        }

        // call country details list
        viewModel.getCountryDetails(CountryDetailsRequest(ActionType = 3, IsActive = 1))
        //call professionlist
        viewModel.setProffessionalListRequest(AttributeRequest(ActionType = "5"))
        //call age group list
        viewModel.setAgeGroupListRequest(AttributeRequest(ActionType = "3"))

        val defaultstatus = GenderSpinner()
        defaultstatus.setId(-1);
        defaultstatus.setName("Select Gender");

        val genderSpinner1 = GenderSpinner()
        genderSpinner1.setId(1)
        genderSpinner1.setName("Male")

        val genderSpinner2 = GenderSpinner()
        genderSpinner2.setId(2)
        genderSpinner2.setName("Female")

        val genderSpinner3 = GenderSpinner()
        genderSpinner3.setId(3)
        genderSpinner3.setName("Don't want to disclose")

        genderList.add(defaultstatus)
        genderList.add(genderSpinner1)
        genderList.add(genderSpinner2)
        genderList.add(genderSpinner3)

        binding.genderSpinner.setAdapter(
            GenderAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                genderList
            )
        )

        LoadingDialogue.showDialog(requireContext())
        // profile detail
        viewModel.getProfile(RegistrationRequest(ActionType = "6", CustomerId = PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].UserId.toString()))

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel.myProfession.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.lstAttributesDetails.isNullOrEmpty()) {

                professionList.addAll(it.lstAttributesDetails)
                professionList.add(
                    0, LstAttributesDetail(
                        AttributeValue = "Select Profession",
                        AttributeId = -1
                    )
                )
                binding.professionSpinner.setAdapter(
                    ProfessionAttributeAdapter(
                        requireContext(), android.R.layout.simple_spinner_item,
                        professionList as ArrayList<LstAttributesDetail>
                    )
                )
            }
        })

        viewModel.getAgeGroupList.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.lstAttributesDetails.isNullOrEmpty()) {

                ageGroupList.addAll(it.lstAttributesDetails)
                ageGroupList.add(
                    0, LstAttributesDetail(
                        AttributeValue = "Select Age Group",
                        AttributeId = -1
                    )
                )

                binding.ageGroup.setAdapter(
                    AttributeAdapter(
                        requireContext(), android.R.layout.simple_spinner_item,
                        ageGroupList as ArrayList<LstAttributesDetail>
                    )
                )
            }
        })

        viewModel.countyDetails.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.lstCountryDetails.isNullOrEmpty()) {

                countryList.addAll(it.lstCountryDetails!!)
                countryList.add(0, LstCountryDetail(CountryName = "Select Country", CountryId = -1))
                binding.countrySpinner.setAdapter(
                    CountryAdapter(
                        requireContext(), android.R.layout.simple_spinner_item,
                        countryList as ArrayList<LstCountryDetail>
                    )
                )
            }
        })

        viewModel.getCityList.observe(viewLifecycleOwner, Observer {
            if(it!=null && !it.CityList.isNullOrEmpty()){
                cityList.addAll(it.CityList!!)
                cityList.add(0, CityList(CityName = "Select City", CityId = -1))
                binding.citySpinner.setAdapter(
                    CityAdapter(
                        requireContext(), android.R.layout.simple_spinner_item,
                        cityList as ArrayList<CityList>
                    )
                )

                cityList.forEach { cityDetail ->
                    if (cityDetail.CityId == cityID) {
                        binding.citySpinner.setSelection(m)
                        m = 0
                        return@forEach
                    }
                    m++
                }
            }
        })

        viewModel.myProfileResponse.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.getCustomerDetailsMobileAppResult?.lstCustomerJson.isNullOrEmpty()) {
                binding.address1.text.append(it.getCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.address1.toString())
                binding.address2.text.append(it.getCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.address2.toString())
                if (it.getCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.jDOB != null) {
                    binding.dob.text = /*DateFormat.dateUIFormat(*/
                        it.getCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.jDOB.toString()
                            .split(" ")[0]
//                    )
                } else {
                    binding.dob.text = "Select Birthday"
                }

                Log.d(TAG, "onActivityCreated: ${it.getCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.jDOB.toString()}")
                AddressID =
                    it.getCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.addressId.toString()
                CustomerID =
                    it.getCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.customerId.toString()
                CustomerDetailID =
                    it.getCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.customerDetailId.toString()
                LoyaltyId =
                    it.getCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.loyaltyId.toString()
                val countryID = it.getCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.countryId
                cityID = it.getCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.cityId!!
                val professionID =
                    it.getCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.professionId
                val ageGroupID = it.getCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.ageGroupId
                val genderName = it.getCustomerDetailsMobileAppResult?.lstCustomerJson?.get(0)?.gender

//                Log.d(TAG, "onActivityCreated: $cityID")
//                viewModel.setCityListRequest(CityRequest(StateId = countryID.toString()))

                countryList.forEach { countryDetail ->
                    if (countryDetail.CountryId!! == countryID) {
                        binding.countrySpinner.setSelection(i)
                        i = 0
                        return@forEach
                    }
                    i++
                }

                professionList.forEach { lstAttributesDetail ->
                    if (lstAttributesDetail.AttributeId!! == professionID) {
                        binding.professionSpinner.setSelection(j)
                        j = 0
                        return@forEach
                    }
                    j++
                }

                ageGroupList.forEach { lstAttributesDetail ->
                    if (lstAttributesDetail.AttributeId!! == ageGroupID) {
                        binding.ageGroup.setSelection(k)
                        k = 0
                        return@forEach
                    }
                    k++
                }

                genderList.forEach { genderSpinner ->
                    if (genderSpinner.name!! == genderName) {
                        binding.genderSpinner.setSelection(l)
                        l = 0
                        return@forEach
                    }
                    l++
                }
            }
        })


        viewModel.updateProfileResponse.observe(viewLifecycleOwner, Observer {
            if(it!=null && it.ReturnMessage!!.toInt()>0){
                Toast.makeText(requireContext(),"Profile updated successfully",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),"Profile failed to updated",Toast.LENGTH_SHORT).show()
            }
            LoadingDialogue.dismissDialog()
        })

        LoadingDialogue.dismissDialog()

    }

    override fun OnCalenderClickResult(
        selectedDate: String?,
        serverFormat: String?,
        actionType: Int?
    ) {
        DOB = serverFormat.toString()
        binding.dob.text = selectedDate
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when(parent!!.id){

            R.id.countrySpinner -> {
                mSelectedCountryDetail = parent.getItemAtPosition(position) as LstCountryDetail
                if (mSelectedCountryDetail!!.CountryId!! >0) {
                    viewModel.setCityListRequest(CityRequest(StateId = mSelectedCountryDetail!!.CountryId.toString()))
                }
            }

            R.id.citySpinner -> mSelectedCity = parent.getItemAtPosition(position) as CityList

            R.id.ageGroup -> mSelectedAgeGroup = parent.getItemAtPosition(position) as LstAttributesDetail

            R.id.professionSpinner -> mSelectedProfessor = parent.getItemAtPosition(position) as LstAttributesDetail

            R.id.genderSpinner -> mSelectedGender = parent.getItemAtPosition(position) as GenderSpinner

        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.updateBtn -> {
                if (TextUtils.isEmpty(binding.address1.text.toString())) {
                    binding.address1.error = "Enter Address1"
                } else if (TextUtils.isEmpty(binding.address2.text.toString())){
                    binding.address2.error = "Enter Address2"
                } else if(mSelectedCountryDetail==null || mSelectedCountryDetail!!.CountryId==-1){
                    (activity as BaseActivity).snackBar("Select Country", R.color.red)
                }else if(mSelectedCity==null || mSelectedCity!!.CityId==-1){
                    (activity as BaseActivity).snackBar("Select City", R.color.red)
                }else if(mSelectedProfessor == null || mSelectedProfessor!!.AttributeId==-1){
                    (activity as BaseActivity).snackBar("Select Profession", R.color.red)
                }else if(mSelectedAgeGroup == null || mSelectedAgeGroup!!.AttributeId==-1){
                    (activity as BaseActivity).snackBar("Select Age Group", R.color.red)
                }else if(TextUtils.isEmpty(binding.dob.text.toString())){
                    binding.dob.error = "Select DOB"
                }else if(mSelectedGender == null || mSelectedGender!!.id==-1){
                    (activity as BaseActivity).snackBar("Select Gender", R.color.red)
                } else{
                    LoadingDialogue.showDialog(requireContext())
                    viewModel.setUpdateProfileRequest(UpdateProfileRequest(ActionType = "8",
                        ActorId = PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].UserId.toString(),
                        IsMobileRequest = "1",
                        ObjCustomers(Address1 = binding.address1.text.toString(),
                            Address2 = binding.address2.text.toString(),
                            AddressId = AddressID,
                            CityId = mSelectedCity!!.CityId.toString(),
                            CountryId = mSelectedCountryDetail!!.CountryId.toString(),
//                            StateId = mSelectedCountryDetail!!.CountryId.toString(),
                            CustomerId = CustomerID,
                            JDOB = DateFormat.dateFormat(binding.dob.text.toString()),
                            Email = PreferenceHelper.getDashboardDetails(context as ProfileActivity)!!.lstCustomerFeedBackJson!![0].CustomerEmail,
                            Mobile = PreferenceHelper.getDashboardDetails(context as ProfileActivity)!!.lstCustomerFeedBackJson!![0].CustomerMobile,
                            FirstName = PreferenceHelper.getDashboardDetails(context as ProfileActivity)!!.lstCustomerFeedBackJson!![0].FirstName,
                            LastName = PreferenceHelper.getDashboardDetails(context as ProfileActivity)!!.lstCustomerFeedBackJson!![0].LastName),
                            ObjCustomerDetail(Gender = mSelectedGender!!.name, LoyaltyId = LoyaltyId, CustomerDetailId=CustomerDetailID,
                            AgeGroupId= mSelectedAgeGroup!!.AttributeId.toString(),ProfessionId= mSelectedProfessor!!.AttributeId.toString())))
                }
            }
        }
    }

}