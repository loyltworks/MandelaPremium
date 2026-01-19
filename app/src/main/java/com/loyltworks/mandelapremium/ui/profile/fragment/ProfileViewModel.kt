package com.loyltworks.mandelapremium.ui.profile.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyltworks.mandelapremium.model.*
import com.loyltworks.mandelapremium.ui.baseClass.BaseViewModel
import com.vmb.fileSelect.FileSelector
import kotlinx.coroutines.launch

class ProfileViewModel : BaseViewModel() {

    /*MyProfile request*/
    private val _myProfileResponse = MutableLiveData<RegistrationResponse>()
    val myProfileResponse: LiveData<RegistrationResponse> = _myProfileResponse

    fun getProfile(myProfileRequest: RegistrationRequest) {
        ///launch the coroutine scope
        scope.launch {
            _myProfileResponse.postValue(apiRepository?.getProfile(myProfileRequest))
        }
    }


    private val _countyDetails = MutableLiveData<CountryDetailResponse>()
    val countyDetails: LiveData<CountryDetailResponse> = _countyDetails

    fun getCountryDetails(countryDetailsRequest: CountryDetailsRequest) {
        scope.launch {
            _countyDetails.postValue(apiRepository?.getCountryDetails(countryDetailsRequest))
        }
    }


    /*MyProfession request*/

    private val _myProfession = MutableLiveData<AttributeResponse>()
    val myProfession: LiveData<AttributeResponse> = _myProfession

    fun setProffessionalListRequest(attributeRequest: AttributeRequest) {
        scope.launch {
            _myProfession.postValue(apiRepository?.getProfessionList(attributeRequest))
        }
    }


    /*MyProfile request*/
    private val _getAgeGroupList = MutableLiveData<AttributeResponse>()
    val getAgeGroupList: LiveData<AttributeResponse> = _getAgeGroupList

    fun setAgeGroupListRequest(attributeRequest: AttributeRequest) {
        scope.launch {
            _getAgeGroupList.postValue(apiRepository?.getAgeGroupList(attributeRequest))
        }
    }

    /*CityList request*/
    private val _getCityList = MutableLiveData<CityResponse>()
    val getCityList: LiveData<CityResponse> = _getCityList

    fun setCityListRequest(cityRequest: CityRequest) {
        scope.launch {
            _getCityList.postValue(apiRepository?.getCityList(cityRequest))
        }
    }

    /*update profile*/
    private val _updateProfileResponse = MutableLiveData<UpdateProfileResponse>()
        val updateProfileResponse: LiveData<UpdateProfileResponse> = _updateProfileResponse

    fun setUpdateProfileRequest(updateProfileRequest: UpdateProfileRequest) {
        scope.launch {
            _updateProfileResponse.postValue(apiRepository?.getProfileResponse(updateProfileRequest))
        }
    }



    /*Update ProfileImage callback*/

    private val _updateProfileImageLiveData = MutableLiveData<UpdateProfileImageResponse>()
    val updateProfileImage : LiveData<UpdateProfileImageResponse> = _updateProfileImageLiveData

    fun setProfileImageUpdate(updateProfileImageRequest: UpdateProfileImageRequest) {
        FileSelector.scope.launch {
            _updateProfileImageLiveData.postValue(apiRepository?.getUpdateProfileImage(updateProfileImageRequest))
        }
    }
}