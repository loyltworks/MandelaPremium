package com.loyltworks.mandelapremium.ui.help.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.MyQueryListingElementBinding
import com.loyltworks.mandelapremium.model.ObjCustomerAllQueryList
import com.loyltworks.mandelapremium.model.QueryListingResponse
import com.loyltworks.mandelapremium.utils.AppController

class MyQueryAdapter(var queryListingResponse : QueryListingResponse?, var onItemClickListener: OnClickCallBack) : RecyclerView.Adapter<MyQueryAdapter.ViewHolder>() {

    interface OnClickCallBack{
        fun onQueryListItemClickResponse(itemView: View,position: Int, productList: List<ObjCustomerAllQueryList?>?)
        fun onClickFeedback(feedbackType: Int, position: Int, productList: List<ObjCustomerAllQueryList?>?)
    }

    class ViewHolder(binding: MyQueryListingElementBinding) : RecyclerView.ViewHolder(binding.root) {
        val row_query_ref_no = binding.rowQueryRefNo
        val row_query_date = binding.rowQueryDate
        val row_query_text = binding.rowQueryText
        val row_query_status = binding.rowQueryStatus
        val status_icon = binding.statusIcon


        //val row_query = itemView.row_query

//        val star_relative = itemView.star_relative

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MyQueryListingElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        holder.rating_bar.isEnabled = false

//        holder.itemView.setOnClickListener { v ->
//            holder.itemView.context.startActivity(Intent(holder.itemView.context, QueryChatActivity::class.java))
//        }

        val lstQueryDetail = queryListingResponse!!.objCustomerAllQueryJsonList!![position]

        holder.row_query_ref_no.text = lstQueryDetail.CustomerTicketRefNo
        holder.row_query_date.text = AppController.dateFormatddmmyy(lstQueryDetail.JCreatedDate.toString().split(" ")[0])

        holder.row_query_text.text = lstQueryDetail.HelpTopic.toString()
        holder.row_query_status.text = lstQueryDetail.TicketStatus.toString()

        when (lstQueryDetail.TicketStatus) {
            "Closed" -> {
                holder.status_icon.setImageResource(R.drawable.ic_close_red)
            }

            "Resolved" -> {
                holder.status_icon.setImageResource(R.drawable.ic_mask_group_12)
            }
            else -> {
                holder.status_icon.setImageResource(R.drawable.ic_mask_group_9)
            }
        }
        holder.itemView.setOnClickListener { v ->
            onItemClickListener.onQueryListItemClickResponse(v,position,queryListingResponse!!.objCustomerAllQueryJsonList)
        }





    }

    override fun getItemCount(): Int {
//        return 8
        return queryListingResponse!!.objCustomerAllQueryJsonList?.size!!
    }

}