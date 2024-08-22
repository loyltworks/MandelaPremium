package com.loyltworks.mandelapremium.ui.profile.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.LstVehicleJson
import com.loyltworks.mandelapremium.model.RegistrationRequest
import com.loyltworks.mandelapremium.ui.profile.VehicleManagerAdapter
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import kotlinx.android.synthetic.main.fragment_tab2.*

class Tab2 : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        return inflater.inflate(R.layout.fragment_tab2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // profile detail
        viewModel.getProfile(RegistrationRequest(ActionType = "6", CustomerId = PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].UserId.toString()))

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.myProfileResponse.observe(viewLifecycleOwner, Observer {
            if(it!=null && !it.GetCustomerDetailsMobileAppResult.lstVehicleJson.isNullOrEmpty()){
                error.visibility = View.GONE
                vehicle_mgr_rv.visibility = View.VISIBLE
                vehicle_mgr_rv.adapter = VehicleManagerAdapter(it.GetCustomerDetailsMobileAppResult.lstVehicleJson as ArrayList<LstVehicleJson>)
            }else{
                error.visibility = View.VISIBLE
                vehicle_mgr_rv.visibility = View.GONE
            }
        })
    }

}