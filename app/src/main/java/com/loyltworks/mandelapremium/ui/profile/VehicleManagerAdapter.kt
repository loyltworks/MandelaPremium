package com.loyltworks.mandelapremium.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyltworks.mandelapremium.databinding.RowVehicleManagerLayoutBinding
import com.loyltworks.mandelapremium.model.LstProductsList
import com.loyltworks.mandelapremium.model.LstVehicleJson
import java.lang.String
import kotlin.Int

class VehicleManagerAdapter(val lstVehicleJson: ArrayList<LstVehicleJson>) : RecyclerView.Adapter<VehicleManagerAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(lstProductsList: LstProductsList)
    }

    class ViewHolder(binding: RowVehicleManagerLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val vehicle_brand = binding.vehicleBrand
        val fuel_type = binding.fuelType
        val model_no = binding.modelNo
        val registration_number = binding.registrationNumber
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val binding =
            RowVehicleManagerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lstVehicleJson = lstVehicleJson[position]
        holder.vehicle_brand.setText(lstVehicleJson.vehicleType)
        holder.model_no.setText(lstVehicleJson.modelNo)
        holder.fuel_type.setText(lstVehicleJson.fuelType)
        holder.registration_number.setText(String.valueOf(lstVehicleJson.vehicleNo))

    }

    override fun getItemCount(): Int {
        return lstVehicleJson.size
    }

}