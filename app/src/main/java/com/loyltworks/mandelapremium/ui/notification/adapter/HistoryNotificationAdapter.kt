package com.oneloyalty.goodpack.ui.history_notifiation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.HistoryNotificationResponse
import com.loyltworks.mandelapremium.model.LstPushHistory
import kotlinx.android.synthetic.main.row_history_notifications.view.*
import java.util.*

class HistoryNotificationAdapter(
    var hisotryListingResponse: HistoryNotificationResponse?,
    var itemClicked: ItemClicked
) : RecyclerView.Adapter<HistoryNotificationAdapter.ViewHolder>() {

    interface ItemClicked {
        fun itemclicks(notificationHistory: LstPushHistory?)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val NReadOrNot = itemView.NReadOrNot
        val NImage = itemView.NImage
//        val NtitleLL = itemView.NtitleLL
        val Ntitle = itemView.Ntitle
        val Ndate = itemView.Ndate
        val Ndesc = itemView.Ndesc
//        val NdescExpandable = itemView.NdescExpandable
        val view = itemView.view
//        val NReadMore = itemView.NReadMore
        val cardView = itemView.cardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_history_notifications,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notificationHistory = hisotryListingResponse!!.lstPushHistoryJson!![position]

        if (notificationHistory.ImagesURL != null && !notificationHistory.ImagesURL.isNullOrEmpty())
            Glide.with(holder.itemView)
            .load(BuildConfig.PROMO_IMAGE_BASE + notificationHistory.ImagesURL)
            .placeholder(R.drawable.placeholder) //                .error(R.drawable.ic_default_img)
            .into(holder.NImage)
        else holder.NImage.visibility = View.GONE

        holder.Ntitle.text = notificationHistory.Title
        holder.Ndesc.text = notificationHistory.PushMessage
//        holder.NdescExpandable.text = notificationHistory.PushMessage
        holder.Ndate.text = notificationHistory.JCreatedDate


    }

    override fun getItemCount(): Int {
        return hisotryListingResponse!!.lstPushHistoryJson?.size!!
//        return 10
    }

    fun removeItem(position: Int) {

        hisotryListingResponse!!.lstPushHistoryJson?.removeAt(position)
        notifyItemRemoved(position)
    }

//    fun restoreItem(item: LstPushHistory?, position: Int) {
//        notificationHistoryArrayList.add(position, item)
//        notifyItemInserted(position)
//    }

    fun getData(): List<LstPushHistory?>? {
        return hisotryListingResponse!!.lstPushHistoryJson
    }


}