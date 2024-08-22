package com.loyltworks.mandelapremium.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.LstProductsList
import com.loyltworks.mandelapremium.model.LstVehicleJson
import kotlinx.android.synthetic.main.row_vehicle_manager_layout.view.*
import java.lang.String

class VehicleManagerAdapter(val lstVehicleJson: ArrayList<LstVehicleJson>) : RecyclerView.Adapter<VehicleManagerAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(lstProductsList: LstProductsList)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vehicle_brand = itemView.vehicle_brand
        val fuel_type = itemView.fuel_type
        val model_no = itemView.model_no
        val registration_number = itemView.registration_number
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_vehicle_manager_layout,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lstVehicleJson = lstVehicleJson[position]
        holder.vehicle_brand.setText(lstVehicleJson.VehicleType)
        holder.model_no.setText(lstVehicleJson.ModelNo)
        holder.fuel_type.setText(lstVehicleJson.FuelType)
        holder.registration_number.setText(String.valueOf(lstVehicleJson.VehicleNo))

    }

    override fun getItemCount(): Int {
        return lstVehicleJson.size
    }

}