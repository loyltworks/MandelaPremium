package com.loyltworks.mandelapremium.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.RowOffersBinding
import com.loyltworks.mandelapremium.model.LstPromotionList
import com.loyltworks.mandelapremium.utils.fetchData.ndk.UrlClass

class OffersAdapter(val lstPromotionJsonList: List<LstPromotionList>,val promotionClickListener: PromotionClickListener) : RecyclerView.Adapter<OffersAdapter.ViewHolder>() {

    interface PromotionClickListener{
        fun onPromotionClicked(lstPromotionJson: LstPromotionList)
    }
    class ViewHolder(binding: RowOffersBinding) : RecyclerView.ViewHolder(binding.root) {
        val promoImage = binding.promoImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowOffersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = lstPromotionJsonList[position]

        Glide.with(holder.itemView.context)
            .load(UrlClass.promoImageBase() + data.ProImage?.replace("..", ""))
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