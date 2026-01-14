package com.loyltworks.mandelapremium.ui.MyActivity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyltworks.mandelapremium.databinding.RowMyRedemptionBinding
import com.loyltworks.mandelapremium.model.LstPromotionList
import com.loyltworks.mandelapremium.model.RedemptionResponse

class MyRedemptionAdapter(
    var whatsNewResponse: RedemptionResponse?,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MyRedemptionAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(offersPromotions: LstPromotionList?)
    }

    class ViewHolder(binding: RowMyRedemptionBinding) : RecyclerView.ViewHolder(binding.root) {
        val points = binding.points
        val transactionType = binding.transactionType
        val location = binding.location
        val date = binding.date

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowMyRedemptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
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