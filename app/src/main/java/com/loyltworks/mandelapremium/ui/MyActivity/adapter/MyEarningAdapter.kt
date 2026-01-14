package com.loyltworks.mandelapremium.ui.MyActivity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyltworks.mandelapremium.databinding.RowMyEarningBinding
import com.loyltworks.mandelapremium.model.LstPromotionList
import com.loyltworks.mandelapremium.model.TransactionHistoryResponse
import com.loyltworks.mandelapremium.utils.AppController

class MyEarningAdapter(
    var whatsNewResponse: TransactionHistoryResponse?,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MyEarningAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(offersPromotions: LstPromotionList?)
    }

    class ViewHolder(binding: RowMyEarningBinding) : RecyclerView.ViewHolder(binding.root) {
        val points = binding.points
        val transactionType = binding.transactionType
        val location = binding.location
        val date = binding.date
        val invoiceNumber = binding.invoiceNumber

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowMyEarningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
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