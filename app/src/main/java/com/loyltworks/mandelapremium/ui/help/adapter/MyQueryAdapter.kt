package com.loyltworks.mandelapremium.ui.help.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.ObjCustomerAllQueryList
import com.loyltworks.mandelapremium.model.QueryListingResponse
import com.loyltworks.mandelapremium.ui.help.help_topic_chat_status.QueryChatActivity
import com.loyltworks.mandelapremium.utils.AppController
import kotlinx.android.synthetic.main.my_query_listing_element.view.*

class MyQueryAdapter(var queryListingResponse : QueryListingResponse?, var onItemClickListener: OnClickCallBack) : RecyclerView.Adapter<MyQueryAdapter.ViewHolder>() {

    interface OnClickCallBack{
        fun onQueryListItemClickResponse(itemView: View,position: Int, productList: List<ObjCustomerAllQueryList?>?)
        fun onClickFeedback(feedbackType: Int, position: Int, productList: List<ObjCustomerAllQueryList?>?)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val row_query_ref_no = itemView.row_query_ref_no
        val row_query_date = itemView.row_query_date
        val row_query_text = itemView.row_query_text
        val row_query_status = itemView.row_query_status
        val status_icon = itemView.status_icon
        val star_row = itemView.star_row
        val row_for_new_comment = itemView.row_for_new_comment
        val feedback_no = itemView.feedback_no
        val feedback_yes = itemView.feedback_yes
        val rating_bar = itemView.rating_bar
        val row_query = itemView.row_query
        val empty_linear = itemView.empty_linear
//        val star_relative = itemView.star_relative

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_query_listing_element, parent, false)
        return ViewHolder(view)
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
                if (lstQueryDetail.Rating != null && lstQueryDetail.Rating != 0) {

                    holder.star_row.visibility = View.VISIBLE
                    holder.row_for_new_comment.visibility = View.GONE
                    holder.rating_bar.rating = lstQueryDetail.Rating.toString().toFloat()

                } else {

                    holder.star_row.visibility = View.GONE
                    holder.row_for_new_comment.visibility = View.VISIBLE

                }

                holder.status_icon.setImageResource(R.drawable.ic_close_red)
            }

            "Resolved" -> {
                holder.star_row.visibility = View.GONE
                holder.row_for_new_comment.visibility = View.GONE
                holder.status_icon.setImageResource(R.drawable.ic_mask_group_12)
            }
            else -> {
                holder.star_row.visibility = View.GONE
                holder.row_for_new_comment.visibility = View.GONE
                holder.status_icon.setImageResource(R.drawable.ic_mask_group_9)
            }
        }
        holder.row_query.setOnClickListener { v ->
            onItemClickListener.onQueryListItemClickResponse(v,position,queryListingResponse!!.objCustomerAllQueryJsonList)
        }


        holder.feedback_no.setOnClickListener {
            onItemClickListener.onClickFeedback(0,position, queryListingResponse!!.objCustomerAllQueryJsonList)
        }

        holder.feedback_yes.setOnClickListener {
            onItemClickListener.onClickFeedback(1,position, queryListingResponse!!.objCustomerAllQueryJsonList)
        }

        holder.star_row.setOnClickListener {
            onItemClickListener.onClickFeedback(2,position, queryListingResponse!!.objCustomerAllQueryJsonList)
        }

        holder.empty_linear.setOnClickListener {
            onItemClickListener.onClickFeedback(2,position, queryListingResponse!!.objCustomerAllQueryJsonList)
        }


    }

    override fun getItemCount(): Int {
//        return 8
        return queryListingResponse!!.objCustomerAllQueryJsonList?.size!!
    }

}