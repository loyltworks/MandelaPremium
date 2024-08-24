package com.loyltworks.mandelapremium.ui.MyActivity.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.*
import com.loyltworks.mandelapremium.ui.MyActivity.MyActivity
import com.loyltworks.mandelapremium.ui.MyActivity.MyActivityViewModel
import com.loyltworks.mandelapremium.ui.MyActivity.adapter.MyEarningAdapter
import com.loyltworks.mandelapremium.ui.MyActivity.adapter.MyRedemptionAdapter
import com.loyltworks.mandelapremium.utils.AppController
import com.loyltworks.mandelapremium.utils.DatePickerBox
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.fragment_my_earning.*
import kotlinx.android.synthetic.main.fragment_my_earning.view.btnText
import kotlinx.android.synthetic.main.fragment_my_earning.view.fromDate_tv
import kotlinx.android.synthetic.main.fragment_my_earning.view.toDate_tv
import kotlinx.android.synthetic.main.fragment_my_redemption.*
import kotlinx.android.synthetic.main.fragment_my_redemption.view.*
import kotlinx.android.synthetic.main.fragment_promotion_tab1.*
import kotlinx.android.synthetic.main.fragment_promotion_tab1.filterDisplay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MyRedemptionFragment : Fragment(), MyRedemptionAdapter.OnItemClickListener {

    var FromDate = ""
    var ToDate = ""
    var filterBy:Int = -1


    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val currentDate = sdf.format(Date())

    private lateinit var earningRedemptionViewModel: EarningRedemptionViewModel

    private lateinit var viewModel : MyActivityViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_my_redemption, container, false)


//        root.r_fromDate_tv.text = AppController.dateFormat(FromDate)
//        root.toDate_tv.text = AppController.dateFormat(ToDate)


        root.r_fromDate.setOnClickListener {
            DatePickerBox.date(activity) {
                root.r_fromDate_tv.text = it
                FromDate = it
                try {
                    DatePickerBox.dateCompare(activity, FromDate, ToDate) {
                        if (!it) {
                            r_fromDate_tv.text = "DD/MM/YYYY"
                            FromDate = ""
                        }
                    }
                } catch (e: Exception) {
                }
            }
        }


        root.r_toDate.setOnClickListener {
            DatePickerBox.date(activity) {
                root.r_toDate_tv.text = it
                ToDate = it
                DatePickerBox.dateCompare(activity, FromDate, ToDate) {
                    if (!it) {
                        r_toDate_tv.text = "DD/MM/YYYY"
                        ToDate = ""
                    }
                }
            }
        }


        root.r_filterBtn.setOnClickListener { v ->

            if(root.btnText.text == "Filter"){

                if (!TextUtils.isEmpty(FromDate) && !TextUtils.isEmpty(ToDate)) {
                    FromDate = r_fromDate_tv.text.toString()
                    ToDate = r_toDate_tv.text.toString()
                    getTransactionHistory(
                        filterBy,
                        AppController.dateAPIFormats(FromDate).toString(),
                        AppController.dateAPIFormats(ToDate).toString()
                    )
                    root.btnText.text = "Reset"
                } else Toast.makeText(activity, "Please select date range", Toast.LENGTH_SHORT).show()
            }else{
                root.btnText.text = "Filter"
                root.r_fromDate_tv.text = ""
                root.r_toDate_tv.text = ""
                FromDate = ""
                ToDate = ""
                getTransactionHistory(
                    filterBy,
                    AppController.dateAPIFormats(FromDate).toString(),
                    AppController.dateAPIFormats(ToDate).toString()
                )
            }

        }
        return root
    }


   /* override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        try {
            if (isVisibleToUser) {
                GetWishePoint((activity as MyActivity).filterID)
                lstAttributesDetail.forEachIndexed { index, lstAttributesDetail ->
                    if (lstAttributesDetail.AttributeId == (activity as MyActivity).filterID){
                        active_status.setSelection(index)
                    }
                }

            }
        } catch (e: Exception) {
        } finally {
        }
    }*/

    lateinit var lstAttributesDetail: ArrayList<LstAttributesDetail>
    var locationAttributeID: Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        earningRedemptionViewModel =
            ViewModelProvider(this).get(EarningRedemptionViewModel::class.java)

        viewModel = ViewModelProvider(this).get(MyActivityViewModel::class.java)


         lstAttributesDetail = requireActivity().intent.getSerializableExtra("mulipleProducts") as ArrayList<LstAttributesDetail>
        val selectedPosition = requireActivity().intent.getIntExtra("SelectedPosition",0)

        var fromlist =  requireActivity().intent.getIntExtra("fromList",0)

//        if (fromlist == 0) {
//            product_name_point_balance_myredemption.visibility = View.GONE
//        } else {
//            product_name_point_balance_myredemption.visibility = View.VISIBLE
//        }



        locationAttributeID = lstAttributesDetail[selectedPosition].AttributeId!!.toInt()

//        product_names.text = lstAttributesDetail[selectedPosition].AttributeType

        GetWishePoint(locationAttributeID)

        filterBy = lstAttributesDetail[selectedPosition].AttributeId!!

        getTransactionHistory(filterBy,FromDate,ToDate)

//        active_status.adapter = CustomSpinnersAdapter(requireContext(), lstAttributesDetail)
//        active_status.setSelection(selectedPosition)
//
//        active_status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                filterBy = (parent?.getItemAtPosition(position) as LstAttributesDetail).AttributeId!!
//                (activity as MyActivity).myearing_rv.adapter = MyEarningAdapter(it, this) filterID = filterBy
//                product_names.text = (parent.getItemAtPosition(position) as LstAttributesDetail).AttributeType
//                GetWishePoint(filterBy)
//
//                if (filterBy != -1) {
//                    fromlist = 1
//                    product_name_point_balance_myredemption.visibility = View.VISIBLE
//                }
//                else  product_name_point_balance_myredemption.visibility = View.GONE
//
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//
//        }

    }


    fun GetWishePoint(locationAttributeID:Int) {



        val wishPointRequest = WishPointRequest()
        wishPointRequest.ActorId = PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].UserId.toString()
        wishPointRequest.ActionType = "1"
        wishPointRequest.LocationId = locationAttributeID.toString()
        viewModel.wishPointsLiveData(wishPointRequest)

    }

    fun FilterDislplay() {

        if (filterDisplay.visibility == View.VISIBLE) {
            filterDisplay.animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_up)
            filterDisplay.visibility = View.GONE
        } else if (filterDisplay.visibility == View.GONE) {
            filterDisplay.animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_down)
            filterDisplay.visibility = View.VISIBLE
        }

    }

    override fun onItemClicked(offersPromotions: LstPromotionList?) {
    }

    private fun getTransactionHistory(FilterID: Int,FromDate: String, ToDate: String) {
        LoadingDialogue.showDialog(requireContext())
        val redemptionRequest = RedemptionRequest()
        redemptionRequest.ActorId =
            PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].UserId.toString()
        redemptionRequest.ActionType = 54
        redemptionRequest.StatusId = -1
        redemptionRequest.LocationId = 0  // if not selected location pass 0

        if (FromDate.isNotEmpty()) redemptionRequest.JFromDate = FromDate
        if (ToDate.isNotEmpty()) redemptionRequest.JToDate = ToDate
        redemptionRequest.LocationId = FilterID

        earningRedemptionViewModel.redemptionLiveData(redemptionRequest)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        earningRedemptionViewModel.redemptionLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()

            if (it != null && !it.LstGiftCardIssueDetailsJson.isNullOrEmpty()) {
                MyRedemption_rv.adapter = MyRedemptionAdapter(it, this)
                redemption_error.visibility = View.GONE
                MyRedemption_rv.visibility = View.VISIBLE

            } else {
                redemption_error.visibility = View.VISIBLE
                MyRedemption_rv.visibility = View.GONE
//                FromDate = ""
//                ToDate = ""

            }

        })

        viewModel.wisePointLiveData.observe(viewLifecycleOwner, {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.ObjCustomerDashboardList.isNullOrEmpty()){
                var productPointBalance = 0

                it.ObjCustomerDashboardList.forEachIndexed { index, objCustomerDashboards ->
                    productPointBalance += it.ObjCustomerDashboardList[index].BehaviourWisePoints!!
                }

//                product_points.text = productPointBalance.toString()

            }else{
//                product_points.text = "0"

            }

        })

    }


}