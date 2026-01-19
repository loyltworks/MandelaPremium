package com.loyltworks.mandelapremium.ui.GiftCards.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.RowMyVoucherBinding
import com.loyltworks.mandelapremium.model.lstMerchantinfo
import com.loyltworks.mandelapremium.utils.fetchData.ndk.UrlClass

class MyVoucherAdapter(
    var lstMerchantinfo: List<lstMerchantinfo>,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MyVoucherAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(lstMerchantinfo: lstMerchantinfo?)
    }

    class ViewHolder(binding: RowMyVoucherBinding) : RecyclerView.ViewHolder(binding.root) {
        val cardImage = binding.cardImage
        val rewardTextview = binding.rewardsTextview
        val rewardTextviewLayout = binding.rewardsTextviewLayout
        val giftCardName = binding.giftCardName
        val validTillDate = binding.validTillDate
        val carNumber = binding.cardNumber
    }

    var value : Int = 0;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowMyVoucherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val merchantinfo = lstMerchantinfo[position]


        /*  MY VOUCHER
                IsEncash = 1 AND IsGifted=0

                GIFTED TO ME
                ReceiverLoyaltyId == CURRENT_LOYALTY_ID AND IsEncash = 0 AND IsGifted=0

                 GIFTED BY ME
                GiftedUserId == CURRENT_USER_ID AND IsEncash = 0 AND IsGifted=0   */

        /*if (merchantinfo.IsEncash == true && merchantinfo.IsGifted == false && GiftCardsType == 0) { // for all MyVoucher
            holder.itemView.visibility = View.VISIBLE
            holder.itemView.layoutParams =
                RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )


        } else if (merchantinfo.ReceiverLoyaltyId.equals(PreferenceHelper.getLoginDetails(holder.itemView.context)?.UserList!![0].UserName.toString()) && merchantinfo.IsEncash == false && merchantinfo.IsGifted == false && GiftCardsType == 1) { // For Gift Received
            holder.itemView.visibility = View.VISIBLE
            holder.itemView.layoutParams =
                RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

            holder.rewardTextviewLayout.visibility = View.GONE


        } else if (merchantinfo.GiftedUserId.equals(PreferenceHelper.getLoginDetails(holder.itemView.context)?.UserList!![0].UserId.toString()) && merchantinfo.IsEncash == false && merchantinfo.IsGifted == false && GiftCardsType == 2) { // Gift sent
            holder.itemView.visibility = View.VISIBLE

            holder.itemView.layoutParams =
                RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )


        } else {
            holder.itemView.visibility = View.GONE

            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)

        }*/


        holder.rewardTextview.text = merchantinfo.MerchantName!!.split("~")[3]
        holder.giftCardName.text = merchantinfo.MerchantName!!.split("~")[1]
        holder.carNumber.text = "Card No. " + merchantinfo.MerchantName!!.split("~")[0]
        holder.validTillDate.text = "Valid Till " + merchantinfo.MerchantName!!.split("~")[6]

        Glide.with(holder.itemView).asBitmap().error(R.drawable.dummy_image)
            .placeholder(R.drawable.dummy_image).load(
                UrlClass.catalogueImageBase() + merchantinfo.Imageurl!!.replace(
                    "~",
                    ""
                )
            ).into(holder.cardImage)


        holder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(merchantinfo)

        }

    }

    override fun getItemCount(): Int {
        return lstMerchantinfo.size
    }

}