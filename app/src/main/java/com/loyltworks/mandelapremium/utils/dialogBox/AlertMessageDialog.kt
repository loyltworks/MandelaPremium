package com.loyltworks.mandelapremium.utils.dialogBox

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.loyltworks.mandelapremium.R

object AlertMessageDialog {

        private var dialog: Dialog?= null

        interface ForgotCallBackAlert {
                fun OK()
        }

        fun showPopUpDialog(context: Context, text: String, forgotCallBackAlert: ForgotCallBackAlert) {

                if(dialog != null) return

                dialog = Dialog(context, R.style.Theme_ZoomInZoomOut)
                dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                dialog?.window?.setGravity(Gravity.CENTER)
                dialog?.setCancelable(false)
                dialog?.setCanceledOnTouchOutside(false)
                dialog?.setContentView(R.layout.alert_message_dialog)
                dialog?.show()

                val mClose = dialog?.findViewById<View>(R.id.ok) as TextView
                val mText = dialog?.findViewById<View>(R.id.error_text) as TextView

                mText.text = text;

                mClose.setOnClickListener {
                        dialog?.dismiss()
                        dialog = null
                        forgotCallBackAlert.OK()
                }

        }

}