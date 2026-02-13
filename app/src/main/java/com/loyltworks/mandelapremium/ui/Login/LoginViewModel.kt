package com.loyltworks.mandelapremium.ui.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyltworks.mandelapremium.model.CancelRequest
import com.loyltworks.mandelapremium.model.CancelResponse
import com.loyltworks.mandelapremium.model.EmailCheckRequest
import com.loyltworks.mandelapremium.model.EmailCheckResponse
import com.loyltworks.mandelapremium.model.ForgotPasswordRequest
import com.loyltworks.mandelapremium.model.ForgotPasswordResponse
import com.loyltworks.mandelapremium.model.LoginRequest
import com.loyltworks.mandelapremium.model.LoginResponse
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
            _loginLiveData.postValue(apiRepository?.getLoginData(loginRequest))
        }
    }


    /***  Check Customer Exists or not ***/

    private val _emailExists = MutableLiveData<EmailCheckResponse>()
    val emailExists: LiveData<EmailCheckResponse> = _emailExists

    fun getUserNameExistence(emailCheckRequest: EmailCheckRequest) {
        ///launch the coroutine scope
        scope.launch {
            _emailExists.postValue(apiRepository?.getEmailExist(emailCheckRequest))
        }
    }


    /*** Forgot Password  ***/

    private val _forgotPasswordLiveData = MutableLiveData<ForgotPasswordResponse>()
    val forgotPasswordLiveData: LiveData<ForgotPasswordResponse> = _forgotPasswordLiveData

    fun getForgotPwd(forgotPasswordRequest: ForgotPasswordRequest) {
        ///launch the coroutine scope
        scope.launch {
            _forgotPasswordLiveData.postValue(apiRepository?.getForgotPasswordData(forgotPasswordRequest))
        }
    }


/*
    fun getCustomerDataByDeviceId(request: RegistrationRequest) {
        ///launch the coroutine scope
        scope.launch {
               _customer_Data.postValue(apiRepository?.getCustomerDetailsByDeviceID(request))
        }
    }

    fun getCustomerType(attributeRequest: AttributeRequest) {
        ///launch the coroutine scope
        scope.launch {
                  _attributeDetails.postValue(apiRepository?.getAttributeDetails(attributeRequest))
        }
    }
*/


    /*** Cancel account deletion request ***/
    private val _cancelDeleteLiveData = MutableLiveData<CancelResponse>()
    val cancelDeleteLiveData: LiveData<CancelResponse> = _cancelDeleteLiveData

    fun cancelAccountDeletion(cancelRequest: CancelRequest) {
        ///launch the coroutine scope
        scope.launch {
            //post the value inside live data
            _cancelDeleteLiveData.postValue(apiRepository?.cancelAccountDeletion(cancelRequest))
        }
    }
}