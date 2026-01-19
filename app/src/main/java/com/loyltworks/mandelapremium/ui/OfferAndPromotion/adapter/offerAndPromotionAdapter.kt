package com.loyltworks.mandelapremium.ui.OfferAndPromotion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.RowOfferAndPromotionBinding
import com.loyltworks.mandelapremium.model.LstPromotionList
import com.loyltworks.mandelapremium.utils.fetchData.ndk.UrlClass

class offerAndPromotionAdapter(
    var lstPromotionJsonList: MutableList<LstPromotionList>,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<offerAndPromotionAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(offersPromotions: LstPromotionList?)
    }

    class ViewHolder(binding: RowOfferAndPromotionBinding) : RecyclerView.ViewHolder(binding.root) {
        val offer_tv = binding.offerTv
        val offers_img = binding.offerImg
        val offer_view = binding.offerView
        val offer_validity = binding.offerValidity

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowOfferAndPromotionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val promotionListing = lstPromotionJsonList[position]


        var ValidFrom = promotionListing.ValidFrom?.split(" ")?.get(0)
        var ValidTo = promotionListing.ValidTo?.split(" ")?.get(0)

        holder.offer_validity.text = "$ValidFrom to $ValidTo"

        holder.offer_tv.text = promotionListing.PromotionName


        try {
            Glide.with(holder.itemView).asBitmap().error(R.drawable.dummy_image)
                .placeholder(R.drawable.dummy_image)
                .load(UrlClass.promoImageBase() + promotionListing.ProImage!!.replace("..", ""))
                .into(holder.offers_img)
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