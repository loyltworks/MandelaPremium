package com.loyltworks.mandelapremium.ui.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loyltworks.mandelapremium.model.*
import com.loyltworks.mandelapremium.ui.baseClass.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    private val _loginLiveData = MutableLiveData<LoginResponse>()
    val loginLiveData: LiveData<LoginResponse> = _loginLiveData

/*    private val _customer_Data = MutableLiveData<RegistrationResponse>()
    val customer_Data: LiveData<RegistrationResponse> = _customer_Data

    private val _attributeDetails = MutableLiveData<AttributeResponse>()
    val customerType: LiveData<AttributeResponse> = _attributeDetails*/

    fun getLoginData(loginRequest: LoginRequest) {
        ///launch the coroutine scope
        scope.launch {
            //get latest news from news repo
            val login_data = apiRepository.getLoginData(loginRequest)
            //post the value inside live data
            _loginLiveData.postValue(login_data)
        }
    }


    /***  Check Customer Exists or not ***/

    private val _emailExists = MutableLiveData<EmailCheckResponse>()
    val emailExists: LiveData<EmailCheckResponse> = _emailExists

    fun getUserNameExistence(emailCheckRequest: EmailCheckRequest) {
        ///launch the coroutine scope
        scope.launch {
            //get latest news from news repo
            val login_data = apiRepository.getEmailExist(emailCheckRequest)
            //post the value inside live data
            _emailExists.postValue(login_data)
        }
    }


    /*** Forgot Password  ***/

    private val _forgotPasswordLiveData = MutableLiveData<ForgotPasswordResponse>()
    val forgotPasswordLiveData: LiveData<ForgotPasswordResponse> = _forgotPasswordLiveData

    fun getForgotPwd(forgotPasswordRequest: ForgotPasswordRequest) {
        ///launch the coroutine scope
        scope.launch {
            //get latest news from news repo
            val forgotPassword = apiRepository.getForgotPasswordData(forgotPasswordRequest)
            //post the value inside live data
            _forgotPasswordLiveData.postValue(forgotPassword)
        }
    }


/*
    fun getCustomerDataByDeviceId(request: RegistrationRequest) {
        ///launch the coroutine scope
        scope.launch {
            //get latest news from news repo
            val customer_data = apiRepository.getCustomerDetailsByDeviceID(request)
            //post the value inside live data
            _customer_Data.postValue(customer_data)
        }
    }

    fun getCustomerType(attributeRequest: AttributeRequest) {
        ///launch the coroutine scope
        scope.launch {
            //get latest news from news repo
            val attributeDetails = apiRepository.getAttributeDetails(attributeRequest)
            //post the value inside live data
            _attributeDetails.postValue(attributeDetails)
        }
    }
*/


}