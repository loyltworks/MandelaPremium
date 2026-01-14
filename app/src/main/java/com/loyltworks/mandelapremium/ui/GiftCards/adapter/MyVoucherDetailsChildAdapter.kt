package com.loyltworks.mandelapremium.ui.GiftCards.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.RowMyVoucherChildDetailsBinding
import com.loyltworks.mandelapremium.model.LstPromotions

class MyVoucherDetailsChildAdapter(
    var lstPromotions: List<LstPromotions>,
    var itemClickListener: OnItemChildClickListener
) : RecyclerView.Adapter<MyVoucherDetailsChildAdapter.ViewHolder>() {

    var firstClicked = true

    interface OnItemChildClickListener {
        fun onItemChildClicked(lstPromotions: LstPromotions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowMyVoucherChildDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(binding: RowMyVoucherChildDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
        val voucher_child_image = binding.voucherChildImage
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