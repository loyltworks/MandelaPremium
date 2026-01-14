package com.loyltworks.mandelapremium.ui.profile.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyltworks.mandelapremium.databinding.FragmentTab2Binding
import com.loyltworks.mandelapremium.model.LstVehicleJson
import com.loyltworks.mandelapremium.model.RegistrationRequest
import com.loyltworks.mandelapremium.ui.profile.VehicleManagerAdapter
import com.loyltworks.mandelapremium.utils.PreferenceHelper

class Tab2 : Fragment() {

    lateinit var binding: FragmentTab2Binding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)


        binding = FragmentTab2Binding.inflate(layoutInflater)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // profile detail
        viewModel.getProfile(RegistrationRequest(ActionType = "6", CustomerId = PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].UserId.toString()))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.myProfileResponse.observe(viewLifecycleOwner, Observer {
            if(it!=null && !it.GetCustomerDetailsMobileAppResult?.lstVehicleJson.isNullOrEmpty()){
                binding.error.visibility = View.GONE
                binding.vehicleMgrRv.visibility = View.VISIBLE
                binding.vehicleMgrRv.adapter = VehicleManagerAdapter(it.GetCustomerDetailsMobileAppResult?.lstVehicleJson as ArrayList<LstVehicleJson>)
            }else{
                binding.error.visibility = View.VISIBLE
                binding.vehicleMgrRv.visibility = View.GONE
            }
        })
    }
}