package com.loyltworks.mandelapremium.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.LstPromotionList
import kotlinx.android.synthetic.main.row_offers.view.promoImage

class OffersAdapter(val lstPromotionJsonList: List<LstPromotionList>,val promotionClickListener: PromotionClickListener) : RecyclerView.Adapter<OffersAdapter.ViewHolder>() {

    interface PromotionClickListener{
        fun onPromotionClicked(lstPromotionJson: LstPromotionList)
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val promoImage = itemView.promoImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_offers, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = lstPromotionJsonList[position]

        Glide.with(holder.itemView.context)
            .load(BuildConfig.PROMO_IMAGE_BASE + data.ProImage?.replace("..", ""))
            .placeholder(R.drawable.dummy_image)
            .fitCenter()
            .into(holder.promoImage)

        holder.itemView.setOnClickListener{
            promotionClickListener.onPromotionClicked(data)
        }
    }

    override fun getItemCount(): Int {
        return lstPromotionJsonList.size
    }
}