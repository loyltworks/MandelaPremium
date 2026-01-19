package com.loyltworks.mandelapremium.ui.help.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.RowLeftChatCellBinding
import com.loyltworks.mandelapremium.databinding.RowRightChatCellBinding
import com.loyltworks.mandelapremium.model.QueryChatElementResponse
import com.loyltworks.mandelapremium.utils.fetchData.ndk.UrlClass
import com.vmb.fileSelect.FileSelector


class QueryChatAdapter(var queryListingResponse: QueryChatElementResponse?, var chatImageDisplay: ChatImageDisplay, ) : RecyclerView.Adapter<QueryChatAdapter.ViewHolder>() {

    var leftItemView: RowLeftChatCellBinding? = null
    var rightItemView: RowRightChatCellBinding? = null

    val LEFT_CELL = 1
    val RIGHT_CELL = 2

    interface ChatImageDisplay {
        fun onClickChatImage(Url: String?)
    }

    class ViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        var row_query_sender: TextView? = null
        var row_query_missed_call: ImageView?= null
        var row_query_time: TextView?= null
        var row_query_text: TextView?= null
        var row_query_text_pdf: TextView?= null
        var chatImage: ImageView?= null

        init {
            if (binding is RowRightChatCellBinding){
                row_query_sender = binding.rowQuerySender
                row_query_missed_call = binding.rowQueryMissedCall
                row_query_time = binding.rowQueryTime
                row_query_text = binding.rowQueryText
                row_query_text_pdf = binding.pdf
                chatImage = binding.chatImage
            }else if (binding is RowLeftChatCellBinding){
                row_query_sender = binding.rowQuerySender
                row_query_missed_call = binding.rowQueryMissedCall
                row_query_time = binding.rowQueryTime
                row_query_text = binding.rowQueryText
                row_query_text_pdf = binding.pdf
                chatImage = binding.chatImage
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return if (viewType == LEFT_CELL) {
            leftItemView = RowLeftChatCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolder(leftItemView!!)
        } else {
            rightItemView = RowRightChatCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(rightItemView!!)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (queryListingResponse!!.objQueryResponseJsonList!!.get(position).UserType == "Customer") RIGHT_CELL else LEFT_CELL
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // initial hide
        holder.row_query_text_pdf?.visibility = View.GONE

        val lstQueryDetail = queryListingResponse!!.objQueryResponseJsonList!![position]
        holder.row_query_sender?.text = lstQueryDetail.RepliedBy.toString()
        holder.row_query_time?.text = lstQueryDetail.JCreatedDate.toString()
        if (!lstQueryDetail.QueryResponseInfo.isNullOrEmpty() || lstQueryDetail.ImageUrl != null) {
            if (lstQueryDetail.ImageUrl != null) {

                val extension =
                    lstQueryDetail.ImageUrl.toString().substringAfterLast(".").toLowerCase()
                if (!FileSelector.isImage(extension)) {

                    Log.d("fjksdjfksdl", lstQueryDetail.ImageUrl.toString())

                    holder.chatImage?.visibility = View.GONE
                    holder.row_query_text_pdf?.visibility = View.VISIBLE


                    holder.row_query_text_pdf?.text =
                        lstQueryDetail.ImageUrl.toString().substringAfterLast("/")

                } else {
                    holder.chatImage?.visibility = View.VISIBLE
                        holder.row_query_text_pdf?.visibility = View.GONE

                }


                if (!lstQueryDetail.QueryResponseInfo.isNullOrEmpty()) {
                    holder.row_query_text?.visibility = View.VISIBLE
                    holder.row_query_text?.text = lstQueryDetail.QueryResponseInfo.toString()
                } else holder.row_query_text?.visibility = View.GONE
                holder.row_query_missed_call?.visibility = View.GONE
                Glide.with(holder.itemView)
                    .load(
                        UrlClass.promoImageBase() + lstQueryDetail.ImageUrl!!.replace(
                            "~",
                            ""
                        )
                    )
                    .placeholder(R.drawable.dummy_image)
                    .error(R.drawable.dummy_image)
                    .apply(RequestOptions().transform(RoundedCorners(50)))
                    .into(holder.chatImage!!)
            } else {
                holder.row_query_text?.visibility = View.VISIBLE
                holder.chatImage?.visibility = View.GONE
                holder.row_query_missed_call?.visibility = View.GONE
                holder.row_query_text?.text = lstQueryDetail.QueryResponseInfo.toString()
            }
        } else {
            holder.chatImage?.visibility = View.GONE
            holder.row_query_text?.visibility = View.GONE
            holder.row_query_missed_call?.visibility = View.VISIBLE
        }

        holder.chatImage?.setOnClickListener(View.OnClickListener {
            chatImageDisplay.onClickChatImage(
                UrlClass.promoImageBase() + lstQueryDetail.ImageUrl!!
                    .replace("~", "")
            )
        })
    }

    override fun getItemCount(): Int {
        return queryListingResponse!!.objQueryResponseJsonList?.size!!
//        return 10
    }

}