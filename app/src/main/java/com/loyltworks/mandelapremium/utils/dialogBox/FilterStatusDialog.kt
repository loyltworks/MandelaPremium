package com.loyltworks.mandelapremium.utils.dialogBox

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.FilterStatus
import java.util.*

object FilterStatusDialog {

  /*  interface FilterStatusCallBack {
        fun onFilterStatusResponse(filterStatuses: ArrayList<FilterStatus?>?, position: Int)
    }

    fun showFilterStatusDialog(
        context: Context,
        statusPosition: Int,
        filterStatuses: ArrayList<FilterStatus?>?,
        filterStatusCallBack: FilterStatusCallBack
    ) {
        val dialogBuilder = AlertDialog.Builder(context, R.style.DialogTheme)
        val inflater = dialogBuilder.create().layoutInflater
        val dialogView: View = inflater.inflate(R.layout.filter_by_status_dialog, null)
        dialogBuilder.setView(dialogView)
        val mRecyclerview =
            dialogView.findViewById<View>(R.id.filter_status_recyclerview) as RecyclerView
        val mApplyStatus = dialogView.findViewById<View>(R.id.apply_status) as TextView
        mRecyclerview.setHasFixedSize(true)
        val infoDialog = dialogBuilder.create()
        infoDialog.setCancelable(true)
        infoDialog.show()
        Objects.requireNonNull(infoDialog.window)
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = infoDialog.window
        window!!.setGravity(Gravity.CENTER)
        val layoutParams = infoDialog.window!!.attributes
        layoutParams.windowAnimations = R.style.DialogAnimation
        layoutParams.y = 0 // bottom margin
        window.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        infoDialog.window!!.attributes = layoutParams
        val filterStatusAdapter: filterStatusAdapter
        filterStatusAdapter =
            filterStatusAdapter(context, statusPosition, filterStatuses, object : OnStatusClick() {
                fun onFilterStatusCallback(
                    position: Int,
                    filterStatuses: ArrayList<FilterStatus?>?
                ) {
                    _position = position
                    //                infoDialog.dismiss();
                }
            })
        mRecyclerview.adapter = filterStatusAdapter

     mApplyStatus.setOnClickListener {
            infoDialog.dismiss()
            filterStatusCallBack.onFilterStatusResponse(filterStatuses, _position)
        }
    }
*/
}