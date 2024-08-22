package com.loyltworks.mandelapremium.ui.MyActivity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.LstPromotionList
import com.loyltworks.mandelapremium.model.RedemptionResponse
import kotlinx.android.synthetic.main.row_my_redemption.view.*

class MyRedemptionAdapter(
    var whatsNewResponse: RedemptionResponse?,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MyRedemptionAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(offersPromotions: LstPromotionList?)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val points = itemView.points
        val transactionType = itemView.transactionType
        val location = itemView.location
        val date = itemView.date

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_my_redemption, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val transactionHistoryResponse = whatsNewResponse!!.LstGiftCardIssueDetailsJson!![position]

        holder.points.text = transactionHistoryResponse.PointsAwarded.toString()
        holder.transactionType.text = transactionHistoryResponse.TransactionType.toString()
        holder.location.text = transactionHistoryResponse.LocationName.toString()
        holder.date.text = transactionHistoryResponse.JTransactiondate.toString().split(" ")[0]

        holder.itemView.setOnClickListener{
            itemClickListener.onItemClicked(null)

        }

    }

    override fun getItemCount(): Int {
        return whatsNewResponse!!.LstGiftCardIssueDetailsJson!!.size
    }

}