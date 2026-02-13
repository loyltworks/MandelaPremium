package com.loyltworks.mandelapremium.utils.dialogBox

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.LoginResponse
import com.loyltworks.mandelapremium.utils.DateFormat


object CancelInfoDialog {

    private var dialog: Dialog? = null

    interface dialogCallback {
        fun OnCancelClick()
        fun clear()
    }

    fun showDialog(context: Context?, loginResponse: LoginResponse, dialogCallback: dialogCallback) {
        if (dialog != null) {
            dialog = null
        }
        if (context == null) return
        dialog = Dialog(context, R.style.Theme_Dialog)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.layout_cancel_info)
        dialog!!.setCanceledOnTouchOutside(false)
        val window: Window? = dialog!!.getWindow()
        window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))



        val membershipId: TextView = dialog!!.findViewById(R.id.membershipId)
        val name: TextView = dialog!!.findViewById(R.id.name)
        val emailId: TextView = dialog!!.findViewById(R.id.emailId)
        val dateOfRequest: TextView = dialog!!.findViewById(R.id.dateOfRequest)
        val status: TextView = dialog!!.findViewById(R.id.status)
        val cancelBtn: AppCompatButton = dialog!!.findViewById(R.id.cancelBtn)
        val close: CardView = dialog!!.findViewById(R.id.close)
        val description:TextView = dialog!!.findViewById(R.id.description)
        val approvedDescription : TextView = dialog!!.findViewById(R.id.approvedDescription)


        membershipId.text = loginResponse.UserList?.get(0)?.membershipID.toString()
        name.text = loginResponse.UserList?.get(0)?.customerName.toString()
        emailId.text = loginResponse.UserList?.get(0)?.Email.toString()
        dateOfRequest.text = DateFormat.dateDOBUIFormat(loginResponse.UserList?.get(0)?.deletedDate.toString().split(" ")[0])
        val deleteStatus = loginResponse.UserList?.get(0)?.accountStatus.toString()

        if(deleteStatus == "0"){
            status.text = "Pending"
        }
        else if(deleteStatus == "1"){

            status.text = "Approved"
            description.text = context.getString(R.string.your_account_deletion_request_has_been_processed)
            cancelBtn.visibility = View.GONE
            approvedDescription.visibility = View.VISIBLE
        }
        else{
            status.text = "--"
        }


        cancelBtn.setOnClickListener {
            dialog!!.dismiss()
            dialogCallback.OnCancelClick()
        }

        close.setOnClickListener {
            dialog!!.dismiss()
            dialogCallback.clear()
        }

        dialog!!.show()
    }
}