package com.loyltworks.mandelapremium.ui.GiftCards.adapter

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
import kotlinx.android.synthetic.main.row_my_voucher_details.view.*
import kotlinx.android.synthetic.main.row_offer_and_promotion.view.*

class MyVoucherDetailsAdapter(
    var lstPromotions: List<LstPromotions>,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MyVoucherDetailsAdapter.ViewHolder>() {

    var firstClicked = true

    interface OnItemClickListener {
        fun onItemClicked(lstPromotions: LstPromotions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_my_voucher_details, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card_parent_img = itemView.card_parent_img

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val lstPromotion = lstPromotions[position]

        if (firstClicked) {
            itemClickListener.onItemClicked(lstPromotions[0])
            firstClicked = false
        }

        Glide.with(holder.itemView).asBitmap().error(R.drawable.dummy_image)
            .placeholder(R.drawable.placeholder).load(
                BuildConfig.GIFTCARD_IMAGE_BASE + lstPromotion.ImagePath!!.replace(
                    "~",
                    ""
                )
            ).into(holder.card_parent_img)


        /*  if (position % 2 == 1)
              holder.textviews.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.lightBlack))*/

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(lstPromotion)

        }

    }

    override fun getItemCount(): Int {
        return lstPromotions.size
    }

}