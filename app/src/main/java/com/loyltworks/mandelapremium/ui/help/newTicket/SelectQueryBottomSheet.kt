package com.loyltworks.mandelapremium.ui.help.newTicket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.ObjHelpTopicList
import com.loyltworks.mandelapremium.ui.help.adapter.SelectQueryBottomSheetAdapter

class SelectQueryBottomSheet{


    interface PaymentCallBack {
        fun onPaymentCallback(_PaymentmethodList: ArrayList<ObjHelpTopicList>, position: Int)
    }


    object SelectQueryOption {

         fun selectPaymentMode(
             context: Context,
             PaymentPosition: Int,
             _PaymentmethodList: ArrayList<ObjHelpTopicList>,
             paymentCallBack: PaymentCallBack
         ) {
            val layoutInflater = LayoutInflater.from(context)
            val dialog = BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme)
            val sheetView: View = layoutInflater.inflate(R.layout.query_topic_bottom_sheet, null)
            dialog.setContentView(sheetView)
            val recyclerView = dialog.findViewById<RecyclerView>(R.id.payment__dialog_recyclerview)
             val closeBottomsheet = dialog.findViewById<ImageView>(R.id.close_bottomsheet)

             val mTextview = dialog.findViewById<TextView>(R.id.select_continue)
            //recyclerView!!.setHasFixedSize(true)
            // use a linear layout manager
            recyclerView!!.isNestedScrollingEnabled = false
            recyclerView.setRecycledViewPool(RecycledViewPool())
            recyclerView.itemAnimator = DefaultItemAnimator()

             dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

             dialog.behavior.addBottomSheetCallback(object : BottomSheetCallback() {
                 override fun onStateChanged(bottomSheet: View, newState: Int) {
                     if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                         dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                     }
                 }

                 override fun onSlide(bottomSheet: View, slideOffset: Float) {}
             })

             closeBottomsheet!!.setOnClickListener { dialog.dismiss() }


             dialog.show()




             recyclerView.adapter = SelectQueryBottomSheetAdapter(
                 context,
                 PaymentPosition,
                 _PaymentmethodList,
                 object : SelectQueryBottomSheetAdapter.PaymentClickCallback {
                     override fun onPaymentCallback(
                         position: Int,
                         _PaymentmethodList: ArrayList<ObjHelpTopicList>
                     ) {

                         mTextview!!.setTextColor(context.resources.getColor(R.color.textYello))
                         mTextview.setBackgroundColor(context.resources.getColor(R.color.colorPrimary))

                         mTextview.setOnClickListener { v: View? ->
                             dialog.dismiss()
                             paymentCallBack.onPaymentCallback(_PaymentmethodList, position)

                         }
                     }
                 })


        }


    }


}




