package com.loyltworks.mandelapremium.ui.OfferAndPromotion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.LstPromotionList
import kotlinx.android.synthetic.main.row_offer_and_promotion.view.*
import java.text.SimpleDateFormat
import java.util.*

class offerAndPromotionAdapter(
    var lstPromotionJsonList: MutableList<LstPromotionList>,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<offerAndPromotionAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(offersPromotions: LstPromotionList?)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val offer_tv = itemView.offer_tv
        val offers_img = itemView.offers_img
        val offer_view = itemView.offer_view
        val offer_validity = itemView.offer_validity

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_offer_and_promotion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val promotionListing = lstPromotionJsonList[position]


        var ValidFrom = promotionListing.ValidFrom?.split(" ")?.get(0)
        var ValidTo = promotionListing.ValidTo?.split(" ")?.get(0)

        holder.offer_validity.text = "$ValidFrom to $ValidTo"

        holder.offer_tv.text = promotionListing.PromotionName


        try {
            Glide.with(holder.itemView).asBitmap().error(R.drawable.temp_offer_promotion)
                .placeholder(R.drawable.placeholder).load(
                    BuildConfig.PROMO_IMAGE_BASE + promotionListing.ProImage!!.replace(
                        "..", ""
                    )
                ).into(holder.offers_img)
            holder.offers_img.setPadding(0, 0, 0, 0)
        } catch (e: Exception) {
        }

        holder.offer_view.setOnClickListener {
            itemClickListener.onItemClicked(promotionListing)

        }

        /*   }else{
               holder.offers_Cardview.visibility = View.GONE
           }*/

    }

    override fun getItemCount(): Int {
        return lstPromotionJsonList.size
    }

}