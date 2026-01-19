package com.loyltworks.mandelapremium.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.RowProductsBinding
import com.loyltworks.mandelapremium.model.AttributeResponse
import com.loyltworks.mandelapremium.model.LstAttributesDetail
import com.loyltworks.mandelapremium.utils.fetchData.ndk.UrlClass

class DashboardProductsAdapter(
    var productResponse: AttributeResponse?,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DashboardProductsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(position: Int, attributesList: List<LstAttributesDetail>)
    }

    class ViewHolder(binding: RowProductsBinding) : RecyclerView.ViewHolder(binding.root) {
        val brandImages = binding.brandImages
//        val offers_img = binding.offers_img
//        val next_ll = binding.next_ll
//        val voucher_ll = binding.voucher_ll
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val promotionListing = productResponse!!.lstAttributesDetails!![position]

        if (promotionListing.AttributeValue != null)
            Glide.with(holder.itemView.context)
                .load(UrlClass.catalogueImageBase() + promotionListing.AttributeValue.split("~")[1])
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