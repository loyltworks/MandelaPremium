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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.OnChangedCallback
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.utils.dialogBox.ForgotPwd.mDailog
import com.oneloyalty.goodpack.utils.BlockMultipleClick

object CommonInformationDailog {

    private var dialog:Dialog?=null


    interface onClickCallback{
        fun onPositiveClick()
        fun onNegativeClick()
    }

    fun showDailog(context: Context, negativeButtons:Boolean, positiveButton:Boolean, infotext:String, callback:onClickCallback){
        if(dialog!=null) return


        dialog = Dialog(context!!, R.style.Theme_Dialog)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.common_information_dialog_layout)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCanceledOnTouchOutside(true)
        val window: Window? = dialog!!.window
        window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )


        val text: TextView = dialog!!.findViewById(R.id.infotxt)
        val positivebutton: AppCompatButton = dialog!!.findViewById(R.id.positiveButton)
        val negativeButton: AppCompatButton = dialog!!.findViewById(R.id.negativeButton)


        if(!negativeButtons){
            negativeButton.visibility=View.GONE
        }
        if(!positiveButton){
            positivebutton.visibility=View.GONE
        }

        text.text=infotext


        positivebutton.setOnClickListener {
            if(BlockMultipleClick.click())return@setOnClickListener
           dialog?.dismiss()
           dialog = null
            callback.onPositiveClick()
        }

        negativeButton.setOnClickListener {
            if(BlockMultipleClick.click())return@setOnClickListener
            dialog?.dismiss()
            dialog=null
            callback.onNegativeClick()
        }

        dialog!!.show()
    }
}