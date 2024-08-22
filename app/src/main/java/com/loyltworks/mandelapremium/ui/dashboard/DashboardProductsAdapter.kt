package com.loyltworks.mandelapremium.ui.dashboard

import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.AttributeResponse
import com.loyltworks.mandelapremium.model.GetProductResponse
import com.loyltworks.mandelapremium.model.LstAttributesDetail
import com.loyltworks.mandelapremium.model.LstProductsList
import kotlinx.android.synthetic.main.row_products.view.*

class DashboardProductsAdapter(
    var productResponse: AttributeResponse?,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DashboardProductsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(position: Int, attributesList: List<LstAttributesDetail>)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandImages = itemView.brandImages
//        val offers_img = itemView.offers_img
//        val next_ll = itemView.next_ll
//        val voucher_ll = itemView.voucher_ll
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_products, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val promotionListing = productResponse!!.lstAttributesDetails!![position]

        if (promotionListing.AttributeValue != null)
            Glide.with(holder.itemView.context)
                .load(BuildConfig.GIFTCARD_IMAGE_BASE + promotionListing.AttributeValue.split("~")[1])
                .error(R.drawable.temp_brand_product_name)
                .into(holder.brandImages)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(position, productResponse!!.lstAttributesDetails!!)
        }

    }

    override fun getItemCount(): Int {
        return productResponse!!.lstAttributesDetails!!.size
    }

}