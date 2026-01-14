package com.loyltworks.mandelapremium.ui.help.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.RowSelectQueryBottomSheetBinding
import com.loyltworks.mandelapremium.model.ObjHelpTopicList

class SelectQueryBottomSheetAdapter(var context: Context, var PaymentPosition: Int, var objHelpTopicList: ArrayList<ObjHelpTopicList>, var onpaymentCallback: PaymentClickCallback) : RecyclerView.Adapter<SelectQueryBottomSheetAdapter.ViewHolder>() {

    interface PaymentClickCallback {
        fun onPaymentCallback(position: Int, _PaymentmethodList: ArrayList<ObjHelpTopicList>)
    }

    class ViewHolder(binding: RowSelectQueryBottomSheetBinding) : RecyclerView.ViewHolder(binding.root) {
        val dotImage = binding.dotImage
        val paymentMethodName = binding.paymentMethodName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowSelectQueryBottomSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
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
        return objHelpTopicList.size
    }
}