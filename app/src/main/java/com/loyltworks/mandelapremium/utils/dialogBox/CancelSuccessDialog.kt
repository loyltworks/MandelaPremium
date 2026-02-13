package com.loyltworks.mandelapremium.utils.dialogBox

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import com.loyltworks.mandelapremium.R


object CancelSuccessDialog {

    private var dialog: Dialog? = null

    interface dialogCallback {
        fun OnBackClick()
    }

    fun showDialog(context: Context?, dialogCallback: dialogCallback) {
        if (dialog != null) {
            dialog = null
        }
        if (context == null) return
        dialog = Dialog(context, R.style.Theme_Dialog)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.layout_cancel_success)
        dialog!!.setCanceledOnTouchOutside(false)
        val window: Window? = dialog!!.window
        window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val backBtn: AppCompatButton = dialog!!.findViewById(R.id.backBtn)


        backBtn.setOnClickListener {
            dialog!!.dismiss()
            dialogCallback.OnBackClick()
        }


        dialog!!.show()
    }
}