package com.oneloyalty.goodpack.ui.history_notifiation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.RowHistoryNotificationsBinding
import com.loyltworks.mandelapremium.model.HistoryNotificationResponse
import com.loyltworks.mandelapremium.model.LstPushHistory

class HistoryNotificationAdapter(
    var hisotryListingResponse: HistoryNotificationResponse?,
    var itemClicked: ItemClicked
) : RecyclerView.Adapter<HistoryNotificationAdapter.ViewHolder>() {

    interface ItemClicked {
        fun itemclicks(notificationHistory: LstPushHistory?)
    }

    class ViewHolder(binding: RowHistoryNotificationsBinding) : RecyclerView.ViewHolder(binding.root) {

        //        val NReadOrNot = binding.NReadOrNot
        val NImage = binding.NImage
        val NImageLayout = binding.NImageLayout
//        val NtitleLL = binding.NtitleLL
        val Ntitle = binding.Ntitle
        val Ndate = binding.Ndate
        val Ndesc = binding.Ndesc
//        val NdescExpandable = binding.NdescExpandable
//        val NReadMore = binding.NReadMore
        val cardView = binding.cardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowHistoryNotificationsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notificationHistory = hisotryListingResponse!!.lstPushHistoryJson!![position]

        if (notificationHistory.ImagesURL != null && !notificationHistory.ImagesURL.isNullOrEmpty())
            Glide.with(holder.itemView)
            .load(BuildConfig.PROMO_IMAGE_BASE + notificationHistory.ImagesURL)
            .placeholder(R.drawable.dummy_image) //                .error(R.drawable.ic_default_img)
            .into(holder.NImage)
        else holder.NImageLayout.visibility = View.GONE

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