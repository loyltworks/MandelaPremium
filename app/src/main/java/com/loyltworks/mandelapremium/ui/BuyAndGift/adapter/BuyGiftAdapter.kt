package com.loyltworks.mandelapremium.ui.BuyAndGift.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.RowBuyGiftBinding
import com.loyltworks.mandelapremium.model.LstVoucherDetails

class BuyGiftAdapter(
    var lstVoucherDetails: List<LstVoucherDetails>,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<BuyGiftAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(lstVoucherDetails: LstVoucherDetails?)
    }

    class ViewHolder(binding: RowBuyGiftBinding) : RecyclerView.ViewHolder(binding.root) {
        val offer_tv = binding.giftName
        val gift_card_img = binding.giftCardImg
        val buy_gift_btn = binding.buyGiftBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowBuyGiftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val lstVoucherDetails = lstVoucherDetails[position]

        holder.offer_tv.text = lstVoucherDetails.CardName

        try {
            Glide.with(holder.itemView).asBitmap().error(R.drawable.dummy_image)
                .placeholder(R.drawable.dummy_image).load(
                    BuildConfig.GIFTCARD_IMAGE_BASE + lstVoucherDetails.ImageUrl!!.replace(
                        "~",
                        ""
                    )
                ).into(holder.gift_card_img)
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