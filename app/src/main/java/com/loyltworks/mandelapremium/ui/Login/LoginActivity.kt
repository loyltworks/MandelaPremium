package com.loyltworks.mandelapremium.ui.Login

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.SyncStateContract
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.iid.FirebaseInstanceId
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.BuildConfig.PUSH_TOKEN
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.EmailCheckRequest
import com.loyltworks.mandelapremium.model.ForgotPasswordRequest
import com.loyltworks.mandelapremium.model.Location
import com.loyltworks.mandelapremium.model.LoginRequest
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.dashboard.DashboardActivity
import com.loyltworks.mandelapremium.utils.AppController
import com.loyltworks.mandelapremium.utils.Keyboard
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.AlertMessageDialog
import com.loyltworks.mandelapremium.utils.dialogBox.ForgotPwd
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import com.loyltworks.mandelapremium.utils.dialogBox.TCDialog
import kotlinx.android.synthetic.main.activity_buy_gift_details.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var viewModel: LoginViewModel

       var forgotPasswordType : Int = 0

    var _mDailog: Dialog? = null

    var token:String?=null


    lateinit var forgotPasswordNumber : String

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //set context
        context = this

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)



        forgotpwd_tv.setOnClickListener(this)
        login_btn.setOnClickListener(this)
        tc_checkBox.setOnClickListener(this)


        // check name number exist or not  when focus change
        userName.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (userName.text.toString().isNotEmpty()) {
                    LoadingDialogue.showDialog(this)
                    forgotPasswordType = 0
                    CheckUserNameExist(userName.text.toString())
                }
            }

        }
//        LoginObservers()


        password.setOnTouchListener(View.OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= password.right - password.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    // your action here
                    if (password.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                        password.transformationMethod = PasswordTransformationMethod.getInstance()
                        password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0)
                    } else {
                        password.transformationMethod =
                            HideReturnsTransformationMethod.getInstance()
                        password.setCompoundDrawablesWithIntrinsicBounds(
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

            R.id.forgotpwd_tv -> {
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

            R.id.login_btn -> {

                // hide keyboard
                Keyboard.hideKeyboard(this, Login_host)

                /*  Login Request */

                if (userName.text.toString().isEmpty()) {
                    userName.error = "Enter Username"
                    userName.requestFocus()
                    return
                } else if (password.text.toString().isEmpty()) {
                    password.error = "Enter Password"
                    password.requestFocus()
                    return
                } else if (!tc_checkBox.isChecked) {
                    // display snack bar
                    snackBar("Please accept terms and conditions !", R.color.blue)
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
                            UserName = userName.text.toString(),
                            Password = password.text.toString(),
                            PushID = token,
                            UserActionType = "GetPasswordDetails",
                            UserType = "Customer"
                        )
                    )
                }
            }

            R.id.tc_checkBox -> {
                TCDialog.getTCAlert(context, object : TCDialog.TCCallback {
                    override fun onAcceptAction(mDialogs: Dialog?) {
                        tc_checkBox.isChecked = true

                    }

                    override fun onDeclineAction(mDialogs: Dialog?) {
                        tc_checkBox.isChecked = false

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
                                userName.setText("")
                                userName.requestFocus()
                            }
                        })

                }else if (it.CheckUserNameExistsResult.ReturnValue == 3) {

                    AlertMessageDialog.showPopUpDialog(this,
                        "Welcome to MandelaPremium Club. our executive will contact you to activate your account.",
                        object : AlertMessageDialog.ForgotCallBackAlert {
                            override fun OK() {
                                userName.setText("")
                                userName.requestFocus()
                            }
                        })

                } else {
                    userName.setText("")
                    userName.requestFocus()
                    Toast.makeText(context, "Member doesn't exist.", Toast.LENGTH_SHORT).show()

                }
            }
        })
        
        /***   forgot password observe ***/

          viewModel.forgotPasswordLiveData.observe(this, Observer {
            if(it!=null && it.forgotPasswordMobileAppResult==true){
                _mDailog!!.dismiss()
                _mDailog = null
                userName.setText("")
                Toast.makeText(context,"We have sent the new password to your \nregistered Mobile Number!",Toast.LENGTH_SHORT).show()
                LoadingDialogue.dismissDialog()
            }else{
                userName.setText("")
                Toast.makeText(context,"Something went wrong please try again later!",Toast.LENGTH_SHORT).show()
                LoadingDialogue.dismissDialog()
            }
        })


        viewModel.loginLiveData.observe(this, {

            /**
             *  Result = 1 -> Successful
             *           -1 -> Invalid password
             *           6 -> Invalid membership Id
             *           other -> Invalid User name and password.
             */





            if (it != null) {

                val result: Int? = it.UserList?.get(0)?.Result
                var Msg:String

                Log.d("jhfkdsjf",result.toString())

                 when (result) {
                    1 -> {

                        // set Login successful
                        PreferenceHelper.setBooleanValue(
                            this,
                            BuildConfig.IsLoggedIn,
                            true
                        )
                        // save login details
                        PreferenceHelper.setLoginDetails(this, it)
                        LoadingDialogue.dismissDialog()
                        // Go to dashboard
                        val intent = Intent(this, DashboardActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                        // login message display
                        Msg =      "Login successful "

                    }
                    -1 -> {
                        Msg =  "Invalid password"
                    }
                    6 -> {
                        Msg =    "Invalid username Id"
                    }
                    else -> {
                        Msg =   "Invalid Username and password"
                    }
                }

                // display snack bar
                snackBar(Msg, R.color.blue)
            }else {
                snackBar("Something went wrong, please try again.", R.color.red)

            }

            LoadingDialogue.dismissDialog();


        })

    }


}