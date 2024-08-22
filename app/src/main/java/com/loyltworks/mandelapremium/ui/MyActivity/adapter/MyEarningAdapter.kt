package com.loyltworks.mandelapremium.ui.MyActivity.adapter

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
import com.loyltworks.mandelapremium.model.TransactionHistoryResponse
import com.loyltworks.mandelapremium.utils.AppController
import kotlinx.android.synthetic.main.row_my_earning.view.*
import kotlinx.android.synthetic.main.row_offer_and_promotion.view.*

class MyEarningAdapter(
    var whatsNewResponse: TransactionHistoryResponse?,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MyEarningAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(offersPromotions: LstPromotionList?)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val points = itemView.points
        val transactionType = itemView.transactionType
        val location = itemView.location
        val date = itemView.date
        val invoiceNumber = itemView.invoiceNumber

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_my_earning, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val transactionHistoryResponse = whatsNewResponse!!.lstRewardTransJsonDetails!!.get(position)

        holder.points.text = transactionHistoryResponse.RewardPoints.toString()
        holder.transactionType.text = transactionHistoryResponse.TransactionType.toString()
        holder.location.text = transactionHistoryResponse.LocationName.toString()
        holder.date.text = AppController.dateFormatddmmyy(transactionHistoryResponse.JTranDate.toString().split(" ")[0])
        holder.invoiceNumber.text = transactionHistoryResponse.InvoiceNo.toString()

        holder.itemView.setOnClickListener{
            itemClickListener.onItemClicked(null)

        }

    }

    override fun getItemCount(): Int {
        return whatsNewResponse!!.lstRewardTransJsonDetails?.size!!
    }

}