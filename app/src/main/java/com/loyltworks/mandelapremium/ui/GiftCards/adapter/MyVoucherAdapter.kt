package com.loyltworks.mandelapremium.ui.GiftCards.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.lstMerchantinfo
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import kotlinx.android.synthetic.main.row_my_voucher.view.*

class MyVoucherAdapter(
    var lstMerchantinfo: List<lstMerchantinfo>,
    var GiftCardsType: Int,

    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MyVoucherAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(lstMerchantinfo: lstMerchantinfo?)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImage = itemView.card_image
        val rewardTextview = itemView.rewards_textview
        val giftCardName = itemView.gift_card_name
        val validTillDate = itemView.valid_till_date
        val carNumber = itemView.card_number
        val GiftCardView = itemView.giftCard

    }

    var value : Int = 0;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_my_voucher, parent, false)
        return ViewHolder(view)
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

        if (merchantinfo.IsEncash == true && merchantinfo.IsGifted == false && GiftCardsType == 0) { // for all MyVoucher
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

            holder.rewardTextview.visibility = View.GONE


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

        }


        holder.rewardTextview.text = merchantinfo.MerchantName!!.split("~")[3] + " Rewards"
        holder.giftCardName.text = merchantinfo.MerchantName!!.split("~")[1]
        holder.carNumber.text = "Card No. " + merchantinfo.MerchantName!!.split("~")[0]
        holder.validTillDate.text = "Valid Till " + merchantinfo.MerchantName!!.split("~")[6]

        Glide.with(holder.itemView).asBitmap().error(R.drawable.temp_offer_promotion)
            .placeholder(R.drawable.placeholder).load(
                BuildConfig.GIFTCARD_IMAGE_BASE + merchantinfo.Imageurl!!.replace(
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