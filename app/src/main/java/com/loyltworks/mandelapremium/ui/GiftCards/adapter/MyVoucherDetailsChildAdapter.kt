package com.loyltworks.mandelapremium.ui.GiftCards.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.GetPromotionResponse
import com.loyltworks.mandelapremium.model.LstPromotionList
import com.loyltworks.mandelapremium.model.LstPromotions
import kotlinx.android.synthetic.main.row_my_voucher_child_details.view.*
import kotlinx.android.synthetic.main.row_offer_and_promotion.view.*

class MyVoucherDetailsChildAdapter(
    var lstPromotions: List<LstPromotions>,
    var itemClickListener: OnItemChildClickListener
) : RecyclerView.Adapter<MyVoucherDetailsChildAdapter.ViewHolder>() {

    var firstClicked = true

    interface OnItemChildClickListener {
        fun onItemChildClicked(lstPromotions: LstPromotions)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val voucher_child_image = itemView.voucher_child_image

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_my_voucher_child_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val lstPromotion = lstPromotions[position]

        if(firstClicked) {
            itemClickListener.onItemChildClicked(lstPromotions[0])
            firstClicked = false
        }

        Glide.with(holder.itemView).asBitmap().error(R.drawable.dummy_image)
            .placeholder(R.drawable.dummy_image).load(
                BuildConfig.GIFTCARD_IMAGE_BASE + lstPromotion.ImagePath!!.replace(
                    "~",
                    ""
                )
            ).into(holder.voucher_child_image)


        holder.itemView.setOnClickListener {
            itemClickListener.onItemChildClicked(lstPromotion)
        }

    }

    override fun getItemCount(): Int {
        return lstPromotions.size
    }

}