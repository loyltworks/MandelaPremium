package com.loyltworks.mandelapremium.ui.BuyAndGift.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.GetPromotionResponse
import com.loyltworks.mandelapremium.model.LstPromotionList
import com.loyltworks.mandelapremium.model.LstVoucherDetails
import kotlinx.android.synthetic.main.row_buy_gift.view.*
import kotlinx.android.synthetic.main.row_offer_and_promotion.view.*

class BuyGiftAdapter(
    var lstVoucherDetails: List<LstVoucherDetails>,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<BuyGiftAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(lstVoucherDetails: LstVoucherDetails?)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val offer_tv = itemView.gift_name
        val gift_card_img = itemView.gift_card_img
        val buy_gift_btn = itemView.buy_gift_btn

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_buy_gift, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val lstVoucherDetails = lstVoucherDetails[position]


        holder.offer_tv.text = lstVoucherDetails.CardName

        try {
            Glide.with(holder.itemView).asBitmap().error(R.drawable.temp_offer_promotion)
                .placeholder(R.drawable.placeholder).load(
                    BuildConfig.GIFTCARD_IMAGE_BASE + lstVoucherDetails.ImageUrl!!.replace(
                        "~",
                        ""
                    )
                ).into(holder.gift_card_img)
//            holder.gift_card_img.setPadding(0, 0, 0, 0)
        } catch (e: Exception) {
        }


        holder.buy_gift_btn.setOnClickListener {
            itemClickListener.onItemClicked(lstVoucherDetails)

        }

    }

    override fun getItemCount(): Int {
        return lstVoucherDetails.size
    }

}