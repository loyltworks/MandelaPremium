package com.loyltworks.mandelapremium.ui.Login

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityLoginBinding
import com.loyltworks.mandelapremium.model.CancelRequest
import com.loyltworks.mandelapremium.model.EmailCheckRequest
import com.loyltworks.mandelapremium.model.ForgotPasswordRequest
import com.loyltworks.mandelapremium.model.Location
import com.loyltworks.mandelapremium.model.LoginRequest
import com.loyltworks.mandelapremium.model.LoginResponse
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.dashboard.DashboardActivity
import com.loyltworks.mandelapremium.utils.Keyboard
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.AlertMessageDialog
import com.loyltworks.mandelapremium.utils.dialogBox.CancelConfirmationDialog
import com.loyltworks.mandelapremium.utils.dialogBox.CancelInfoDialog
import com.loyltworks.mandelapremium.utils.dialogBox.CancelSuccessDialog
import com.loyltworks.mandelapremium.utils.dialogBox.ForgotPwd
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import com.loyltworks.mandelapremium.utils.dialogBox.TCDialog
import com.loyltworks.mandelapremium.utils.fetchData.ShaKeyManager


class LoginActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

       var forgotPasswordType : Int = 0

    var _mDailog: Dialog? = null

    var token:String?=null

    private lateinit var loginResponse: LoginResponse


    lateinit var forgotPasswordNumber : String

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //set context
        context = this

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        ShaKeyManager.fetchShaKeys(
            onSuccess = {

            },
            onFailure = {
                Toast.makeText(this,getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
            }
        )

        binding.forgotpwdTv.setOnClickListener(this)
        binding.loginBtn.setOnClickListener(this)
        binding.tcCheckBox.setOnClickListener(this)


        // check name number exist or not  when focus change
        binding.userName.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.userName.text.toString().isNotEmpty()) {
                    LoadingDialogue.showDialog(this)
                    forgotPasswordType = 0
                    CheckUserNameExist(binding.userName.text.toString())
                }
            }

        }
//        LoginObservers()


        binding.password.setOnTouchListener(View.OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.password.right - binding.password.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    // your action here
                    if (binding.password.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                        binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
                        binding.password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0)
                    } else {
                        binding.password.transformationMethod =
                            HideReturnsTransformationMethod.getInstance()
                        binding.password.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_visibility_off,
                            0
                        )
                    }
                    return@OnTouchListener true
                }
            }
            false
        })


    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.forgotpwdTv -> {
                ForgotPwd.forgotPasswordDailog(
                    false,
                    context,
                    object : ForgotPwd.ForgotCallBackAlert {
                        override fun submit(status: Boolean, text: String?, mDailog: Dialog?) {
                            forgotPasswordNumber = text!!
                            _mDailog = mDailog
                            forgotPasswordType = 1
                            LoadingDialogue.showDialog(this@LoginActivity)
                            CheckUserNameExist(text)
                        }

                    })
            }

            R.id.loginBtn -> {

                // hide keyboard
                Keyboard.hideKeyboard(this, binding.LoginHost)

                /*  Login Request */

                if (binding.userName.text.toString().isEmpty()) {
                    binding.userName.error = "Enter Username"
                    binding.userName.requestFocus()
                    return
                } else if (binding.password.text.toString().isEmpty()) {
                    binding.password.error = "Enter Password"
                    binding.password.requestFocus()
                    return
                } else if (!binding.tcCheckBox.isChecked) {
                    // display snack bar
                    Toast.makeText(this,"Please accept terms and conditions !",Toast.LENGTH_SHORT).show()
                    return
                } else {
                    LoadingDialogue.showDialog(this)

                    //Push token :
                    token = PreferenceHelper.getStringValue(this, BuildConfig.PUSH_TOKEN)
                    Log.d(TAG, "token : $token")

                    //if (token!!.isEmpty()) token = FirebaseInstanceId.getInstance().token
                    print("onCreate: TOKEN $token")

                    //token = FirebaseInstanceId.getInstance().token


                    // login api call
                    viewModel.getLoginData(
                        LoginRequest(
                            Browser = "Android",
                            LoggedDeviceName = "Android",
                            UserName = binding.userName.text.toString(),
                            Password = binding.password.text.toString(),
                            PushID = token,
                            UserActionType = "GetPasswordDetails",
                            UserType = "Customer"
                        )
                    )
                }
            }

            R.id.tcCheckBox -> {
                TCDialog.getTCAlert(context, object : TCDialog.TCCallback {
                    override fun onAcceptAction(mDialogs: Dialog?) {
                        binding.tcCheckBox.isChecked = true

                    }

                    override fun onDeclineAction(mDialogs: Dialog?) {
                        binding.tcCheckBox.isChecked = false

                    }

                })

            }


        }
    }


    private fun CheckUserNameExist(username: String) {
        viewModel.getUserNameExistence(
            EmailCheckRequest(
                ActionType = "170", (
                        Location(
                            UserName = username
                        ))
            )
        )
    }


    override fun callInitialServices() {
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun callObservers() {

        /*** Check email Exists or not ***/
        viewModel.emailExists.observe(this, Observer {
            LoadingDialogue.dismissDialog()

            if (it != null && it.CheckUserNameExistsResult.ReturnValue != null) {
                if (it.CheckUserNameExistsResult.ReturnValue > 0) {

                    if (forgotPasswordType ==  1) {
                        LoadingDialogue.showDialog(this)
                        viewModel.getForgotPwd(ForgotPasswordRequest(forgotPasswordNumber))
                    }

                }else if (it.CheckUserNameExistsResult.ReturnValue == 2) {

                    AlertMessageDialog.showPopUpDialog(this,
                        "Welcome to MandelaPremium Club. your account has been deactivated.",
                        object : AlertMessageDialog.ForgotCallBackAlert {
                            override fun OK() {
                                binding.userName.setText("")
                                binding.userName.requestFocus()
                            }
                        })

                }else if (it.CheckUserNameExistsResult.ReturnValue == 3) {

                    AlertMessageDialog.showPopUpDialog(this,
                        "Welcome to MandelaPremium Club. our executive will contact you to activate your account.",
                        object : AlertMessageDialog.ForgotCallBackAlert {
                            override fun OK() {
                                binding.userName.setText("")
                                binding.userName.requestFocus()
                            }
                        })

                } else {
                    binding.userName.setText("")
                    binding.userName.requestFocus()
                    Toast.makeText(context, "Member doesn't exist.", Toast.LENGTH_SHORT).show()

                }
            }
        })
        
        /***   forgot binding.password observe ***/

          viewModel.forgotPasswordLiveData.observe(this, Observer {
            if(it!=null && it.forgotPasswordMobileAppResult==true){
                _mDailog!!.dismiss()
                _mDailog = null
                binding.userName.setText("")
                Toast.makeText(context,"We have sent the new binding.password to your \nregistered Mobile Number!",Toast.LENGTH_SHORT).show()
                LoadingDialogue.dismissDialog()
            }else{
                binding.userName.setText("")
                Toast.makeText(context,"Something went wrong please try again later!",Toast.LENGTH_SHORT).show()
                LoadingDialogue.dismissDialog()
            }
        })


        viewModel.loginLiveData.observe(this) {

            /**
             *  Result = 1 -> Successful
             *           -1 -> Invalid binding.password
             *           6 -> Invalid membership Id
             *           other -> Invalid User name and binding.password.
             */

            if(this.lifecycle.currentState== Lifecycle.State.RESUMED){
                if (it != null) {
                    loginResponse = it
                    val result: Int? = it.UserList?.get(0)?.Result
                    var Msg=""

                    Log.d("jhfkdsjf", result.toString())


                    when (result) {
                        1 -> {
                            checkAccountDeletionStatus()
                        }

                        -1 -> {
                            Msg = "Invalid binding.password"
                        }

                        6 -> {
                            Msg = "Invalid username Id"
                        }

                        else -> {
                            Msg = "Invalid Username and binding.password"
                        }
                    }

                    // display snack bar
                    Toast.makeText(this,Msg,Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,"Something went wrong, please try again.",Toast.LENGTH_SHORT).show()

                }
            }




        }


        viewModel.cancelDeleteLiveData.observe(this){
            if (this.lifecycle.currentState == Lifecycle.State.RESUMED){

                LoadingDialogue.dismissDialog()

                if(it !=null && it.returnMessage.contentEquals("1")){
                    showCancelSuccessPopup()
                }else{
                    Toast.makeText(this, getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    private fun checkAccountDeletionStatus() {
        if(loginResponse.UserList!![0].isDelete == 0){
            verifiedStatus(loginResponse)
        }else if(loginResponse.UserList!![0].accountStatus == "0" || loginResponse.UserList!![0].accountStatus == "1"){
            showCancelDeleteRequestPopup()
        }else if(loginResponse.UserList!![0].accountStatus == "2"){
           snackBar("Username Does not exist!", R.color.red)
        }
    }


    private fun showCancelDeleteRequestPopup() {
        CancelInfoDialog.showDialog(this,loginResponse,object : CancelInfoDialog.dialogCallback{
            override fun OnCancelClick() {
                showCancelConfirmPopup()
            }
            override fun clear() {
                binding.userName.setText("")
                binding.password.setText("")
            }
        })



    }


    private fun showCancelConfirmPopup() {
        CancelConfirmationDialog.showDialog(this,object : CancelConfirmationDialog.dialogCallback{
            override fun OnYesClick() {
              cancelDeletionRequest()
            }

            override fun OnNoClick() {
                showCancelDeleteRequestPopup()
            }
        })
    }


    private fun showCancelSuccessPopup() {
        CancelSuccessDialog.showDialog(this,object : CancelSuccessDialog.dialogCallback{
            override fun OnBackClick() {
                binding.userName.setText("")
                binding.password.setText("")
            }
        })
    }

    private fun verifiedStatus(loginResponse: LoginResponse) {
        // set Login successful
        PreferenceHelper.setBooleanValue(
            this,
            BuildConfig.IsLoggedIn,
            true
        )
        // save login details
        PreferenceHelper.setLoginDetails(this, loginResponse)
        LoadingDialogue.dismissDialog()
        // Go to dashboard
        val intent = Intent(this, DashboardActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }



    private fun cancelDeletionRequest() {
        LoadingDialogue.showDialog(this)
        viewModel.cancelAccountDeletion(
            CancelRequest(
            actionType = 6,
            userid = loginResponse.UserList?.get(0)?.UserId.toString().toInt(),
                emailOrMobile = loginResponse.UserList?.get(0)?.Email.toString(),
            firstName = loginResponse.UserList?.get(0)?.customerName.toString(),
            userName = loginResponse.UserList?.get(0)?.UserName.toString()
        )
        )
    }


}