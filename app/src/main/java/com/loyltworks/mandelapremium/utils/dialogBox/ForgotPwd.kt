package com.loyltworks.mandelapremium.utils.dialogBox

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.loyltworks.mandelapremium.R

object ForgotPwd {

    var mDailog: Dialog? = null


    interface ForgotCallBackAlert {
        fun submit(status: Boolean, text: String?, mDailog: Dialog?)
    }

    fun forgotPasswordDailog(
        isVisibleCancel: Boolean?,
        context: Context?,
        forgotCallBackAlert: ForgotCallBackAlert
    ) {

        if (mDailog == null) {
            mDailog = Dialog(context!!,R.style.Theme_Dialog)
            mDailog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mDailog!!.setContentView(R.layout.custom_forgot_password_alert)
            mDailog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDailog!!.setCanceledOnTouchOutside(true)
            val window: Window? = mDailog!!.window
            window!!.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val textDialog: EditText = mDailog!!.findViewById<EditText>(R.id.fp_email)
        val fp_forgot_pw_btn: TextView = mDailog!!.findViewById<TextView>(R.id.fp_forgot_pw_btn)
        fp_forgot_pw_btn.setOnClickListener { v: View? ->
            val textDialogs = textDialog.text.toString()
            if (textDialogs.isEmpty()) {
                Toast.makeText(context, "Enter Mobile Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (textDialogs.length < 4) {
                Toast.makeText(context, "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            forgotCallBackAlert.submit(true, textDialogs, mDailog)
        }
        mDailog!!.show()
    }

}