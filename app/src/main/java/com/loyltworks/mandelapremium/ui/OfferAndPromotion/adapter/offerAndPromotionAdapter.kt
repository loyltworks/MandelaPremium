package com.loyltworks.mandelapremium.ui.OfferAndPromotion.adapter

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
import com.loyltworks.mandelapremium.utils.AppController
import com.loyltworks.mandelapremium.utils.DateCheck
import kotlinx.android.synthetic.main.row_offer_and_promotion.view.*
import java.text.SimpleDateFormat
import java.util.*

class offerAndPromotionAdapter(
    var whatsNewResponse: GetPromotionResponse?,
    var promotionType: Int,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<offerAndPromotionAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(offersPromotions: LstPromotionList?)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val offer_tv = itemView.offer_tv
        val offers_img = itemView.offers_img
        val offers_Cardview = itemView.promotionCardView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_offer_and_promotion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val promotionListing = whatsNewResponse!!.LstPromotionJsonList!![position]


        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val ValidFrom: Date = sdf.parse(promotionListing.ValidFrom!!)
        var ValidTo: Date = sdf.parse(promotionListing.ValidTo!!)


        if (Date().after(ValidFrom) && Date().before(ValidTo) && promotionType == 0) { // today, current promotion
//            holder.offers_Cardview.visibility = View.VISIBL

            holder.itemView.visibility = View.VISIBLE
            holder.itemView.layoutParams =
                RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )


        } else if (Date().after(ValidTo) && promotionType == 2) {// previous
//            holder.offers_Cardview.visibility = View.VISIBLE
            holder.itemView.visibility = View.VISIBLE
            holder.itemView.layoutParams =
                RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

        } else if (Date().before(ValidFrom) && promotionType == 1) { //tomorror

            holder.itemView.visibility = View.VISIBLE
            holder.itemView.layoutParams =
                RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )


//            holder.offers_Cardview.visibility = View.VISIBLE

        } else {
            holder.itemView.visibility = View.GONE
//            holder.offers_Cardview.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)

        }


/*//        if(promotionListing.Validity!=null) {
            if (DateCheck.isToday(promotionListing.Validity!!) == 0 && promotionType == 0) { // Today
                holder.offers_Cardview.visibility = View.VISIBLE
            } else if (DateCheck.isToday(promotionListing.Validity!!) == -1 && promotionType == -1) { // yesterday
                holder.offers_Cardview.visibility = View.VISIBLE
            } else if (DateCheck.isToday(promotionListing.Validity!!) == 1 && promotionType == 1) { // tomorrow
                holder.offers_Cardview.visibility = View.VISIBLE
            } else {
                holder.offers_Cardview.visibility = View.GONE }*/

        holder.offer_tv.text = promotionListing.PromotionName






        try {
            Glide.with(holder.itemView).asBitmap().error(R.drawable.temp_offer_promotion)
                .placeholder(R.drawable.placeholder).load(
                    BuildConfig.PROMO_IMAGE_BASE + promotionListing.ProImage!!.replace(
                        "..",
                        ""
                    )
                ).into(holder.offers_img)
            holder.offers_img.setPadding(0, 0, 0, 0)
        } catch (e: Exception) {
        }

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(promotionListing)

        }

        /*   }else{
               holder.offers_Cardview.visibility = View.GONE
           }*/

    }

    override fun getItemCount(): Int {
        return whatsNewResponse!!.LstPromotionJsonList!!.size
    }

}