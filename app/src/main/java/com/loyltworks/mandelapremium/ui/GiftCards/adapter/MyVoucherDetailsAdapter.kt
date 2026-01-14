package com.loyltworks.mandelapremium.ui.GiftCards.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.RowMyVoucherDetailsBinding
import com.loyltworks.mandelapremium.model.LstPromotions

class MyVoucherDetailsAdapter(
    var lstPromotions: List<LstPromotions>,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MyVoucherDetailsAdapter.ViewHolder>() {

    var firstClicked = true

    interface OnItemClickListener {
        fun onItemClicked(lstPromotions: LstPromotions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowMyVoucherDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(binding: RowMyVoucherDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
        val card_parent_img = binding.cardParentImg

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val lstPromotion = lstPromotions[position]

        if (firstClicked) {
            itemClickListener.onItemClicked(lstPromotions[0])
            firstClicked = false
        }

        Glide.with(holder.itemView).asBitmap().error(R.drawable.dummy_image)
            .placeholder(R.drawable.dummy_image).load(
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