package com.loyltworks.mandelapremium.utils.dialogBox

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import com.loyltworks.mandelapremium.R
import com.oneloyalty.goodpack.utils.BlockMultipleClick

object RootCheckDialog {
    private var dialog: Dialog? = null

    interface RootCheckDialogCallBack {
        fun onOk()
    }

    fun showRootCheckDialog(
        context: Context,
        rootCheckDialogCallBack: RootCheckDialogCallBack,
    ) {

        if (dialog != null) return

        dialog = Dialog(context, R.style.Theme_Dialog)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setGravity(Gravity.CENTER)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        val window = dialog!!.window
        window!!.setGravity(Gravity.CENTER)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog?.setContentView(R.layout.root_check_dialog)
        dialog?.show()

        val ok = dialog?.findViewById<View>(R.id.back_to_dashboard) as LinearLayout

        ok.setOnClickListener {
            if(BlockMultipleClick.click())return@setOnClickListener
            rootCheckDialogCallBack.onOk()
            dialog?.dismiss()
            dialog = null
        }



    }
}