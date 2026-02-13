package com.loyltworks.mandelapremium.utils.dialogBox

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import com.loyltworks.mandelapremium.R
import com.oneloyalty.goodpack.utils.BlockMultipleClick


object CancelConfirmationDialog {

    private var dialog: Dialog? = null

    interface dialogCallback {
        fun OnYesClick()
        fun OnNoClick()
    }

    fun showDialog(context: Context?, dialogCallback: dialogCallback) {
        if (dialog != null) {
            dialog = null
        }
        if (context == null) return
        dialog = Dialog(context, R.style.Theme_Dialog)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.layout_cancel_confirm)
        dialog!!.setCanceledOnTouchOutside(false)
        val window: Window? = dialog!!.window
        window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val noBtn: AppCompatButton = dialog!!.findViewById(R.id.noBtn)
        val yesBtn: AppCompatButton = dialog!!.findViewById(R.id.yesBtn)



        noBtn.setOnClickListener {
            if(BlockMultipleClick.click())return@setOnClickListener
            dialog!!.dismiss()
            dialogCallback.OnNoClick()
        }

        yesBtn.setOnClickListener {
            if(BlockMultipleClick.click())return@setOnClickListener
            dialog!!.dismiss()
            dialogCallback.OnYesClick()
        }

        dialog!!.show()
    }
}