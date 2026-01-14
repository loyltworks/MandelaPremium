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

object ScanDialogCallback {

        private var dialog: Dialog?= null
        fun showPopUpDialog(context: Context, text: String,text1 :String, dialogueCallBack: DialogueCallBack) {

                if(dialog != null) return

                dialog = Dialog(context, R.style.Theme_NoInternetDialog)
                dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                dialog?.window?.setGravity(Gravity.BOTTOM)
                dialog?.setCancelable(false)
                dialog?.setCanceledOnTouchOutside(false)
                dialog?.setContentView(R.layout.scan_success_below_popup_layout)
                dialog?.show()

                val mClose = dialog?.findViewById<View>(R.id.code) as TextView
                val mText = dialog?.findViewById<View>(R.id.code_status) as TextView
                val scan = dialog?.findViewById<View>(R.id.scan) as TextView
                val back = dialog?.findViewById<View>(R.id.backBtn) as TextView

                mText.text = text;
                mClose.text = text1;

                scan.setOnClickListener {
                        dialogueCallBack.onScanAgain()
                        dialog?.dismiss()
                        dialog = null
                }

                back.setOnClickListener {
                        dialogueCallBack.onBack()
                        dialog?.dismiss()
                        dialog = null
                }

        }

}