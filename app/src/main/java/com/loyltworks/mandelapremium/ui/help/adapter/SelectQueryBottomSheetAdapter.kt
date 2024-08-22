package com.loyltworks.mandelapremium.ui.help.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.ObjHelpTopicList
import kotlinx.android.synthetic.main.row_select_query_bottom_sheet.view.*
import kotlin.collections.ArrayList

class SelectQueryBottomSheetAdapter(
    var context: Context,
    var PaymentPosition: Int,
    var objHelpTopicList: ArrayList<ObjHelpTopicList>,
    var onpaymentCallback: PaymentClickCallback
) : RecyclerView.Adapter<SelectQueryBottomSheetAdapter.ViewHolder>() {


    interface PaymentClickCallback {
        fun onPaymentCallback(position: Int, _PaymentmethodList: ArrayList<ObjHelpTopicList>)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val dotImage = itemView.dot_image
        val paymentMethodName = itemView.payment_method_name

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_select_query_bottom_sheet, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val lstAttributesDetail = objHelpTopicList[position]

        if (PaymentPosition == position) {
            Glide.with(context)
                .load(R.drawable.red_circle)
                .placeholder(R.drawable.red_circle)
                .error(R.drawable.red_circle)
                .apply(RequestOptions().transform(RoundedCorners(50)))
                .into(holder.dotImage)
        } else {
            Glide.with(context)
                .load(R.drawable.black_circle)
                .placeholder(R.drawable.black_circle)
                .error(R.drawable.black_circle)
                .apply(RequestOptions().transform(RoundedCorners(50)))
                .into(holder.dotImage)
        }

        holder.paymentMethodName.text = lstAttributesDetail.HelpTopicName

        holder.itemView.setOnClickListener {
            onpaymentCallback.onPaymentCallback(position, objHelpTopicList)
            PaymentPosition = position
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
//        return 10
        return objHelpTopicList.size
    }

}