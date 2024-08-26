package com.loyltworks.mandelapremium.ui.MyActivity.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.*
import com.loyltworks.mandelapremium.ui.MyActivity.MyActivityViewModel
import com.loyltworks.mandelapremium.ui.MyActivity.adapter.MyEarningAdapter
import com.loyltworks.mandelapremium.utils.AppController
import com.loyltworks.mandelapremium.utils.DatePickerBox
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.activity_my.*
import kotlinx.android.synthetic.main.fragment_my_earning.*
import kotlinx.android.synthetic.main.fragment_my_earning.view.*
import kotlinx.android.synthetic.main.fragment_my_redemption.*
import java.text.SimpleDateFormat
import java.util.*


class MyEarningFragment : Fragment(), MyEarningAdapter.OnItemClickListener {

    var FromDate = ""
    var ToDate = ""
    var filterBy: Int = -1

    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val currentDate = sdf.format(Date())

    private lateinit var earningRedemptionViewModel: EarningRedemptionViewModel

    private lateinit var viewModel: MyActivityViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val root = inflater.inflate(R.layout.fragment_my_earning, container, false)

        /*    FromDate = currentDate.toString()
            ToDate = currentDate.toString()*/

//        root.fromDate_tv.text = AppController.dateFormat(FromDate)
//        root.toDate_tv.text = AppController.dateFormat(ToDate)


        root.fromDate.setOnClickListener {
            DatePickerBox.date(activity) {
                root.fromDate_tv.text = it
                FromDate = it
                try {
                    DatePickerBox.dateCompare(activity, FromDate, ToDate) {
                        if (!it) {
                            fromDate_tv.text = "DD/MM/YYYY"
                            FromDate = ""
                        }
                    }
                } catch (e: Exception) {
                }
            }
        }


        root.toDate.setOnClickListener {
            DatePickerBox.date(activity) {
                root.toDate_tv.text = it
                ToDate = it
                DatePickerBox.dateCompare(activity, FromDate, ToDate) {
                    if (!it) {
                        toDate_tv.text = "DD/MM/YYYY"
                        ToDate = ""
                    }
                }
            }
        }


        root.earning_filterBtn.setOnClickListener { v ->

            if(root.btnText.text == "Filter"){
                if (!TextUtils.isEmpty(FromDate) && !TextUtils.isEmpty(ToDate)) {
                    FromDate = fromDate_tv.text.toString()
                    ToDate = toDate_tv.text.toString()
                    getTransactionHistory(
                        filterBy,
                        AppController.dateAPIFormats(FromDate).toString(),
                        AppController.dateAPIFormats(ToDate).toString()
                    )
                    root.btnText.text = "Reset"
                } else Toast.makeText(activity, "Please select date range", Toast.LENGTH_SHORT).show()
            }else{
                root.btnText.text = "Filter"
                root.fromDate_tv.text = ""
                root.toDate_tv.text = ""
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

    lateinit var lstAttributesDetail: ArrayList<LstAttributesDetail>
    var locationAttributeID: Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        earningRedemptionViewModel =
            ViewModelProvider(this).get(EarningRedemptionViewModel::class.java)
        viewModel = ViewModelProvider(this).get(MyActivityViewModel::class.java)


        lstAttributesDetail =
            requireActivity().intent.getSerializableExtra("mulipleProducts") as ArrayList<LstAttributesDetail>
        val selectedPosition = requireActivity().intent.getIntExtra("SelectedPosition", 0)
        var fromlist = requireActivity().intent.getIntExtra("fromList", 0)

//        if (fromlist == 0) {
//            product_name_point_balance_myearning.visibility = View.GONE
//        } else {
//            product_name_point_balance_myearning.visibility = View.VISIBLE
//        }

        locationAttributeID = lstAttributesDetail[selectedPosition].AttributeId!!.toInt()
//        product_name.text = lstAttributesDetail[selectedPosition].AttributeType

        GetWishePoint(locationAttributeID)

        filterBy = lstAttributesDetail[selectedPosition].AttributeId!!

        getTransactionHistory(filterBy, FromDate, ToDate)



//        earnig_product_status.adapter = CustomSpinnersAdapter(requireContext(), lstAttributesDetail)
//        earnig_product_status.setSelection(selectedPosition)
//
//        earnig_product_status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?, view: View?, position: Int, id: Long
//            ) {
//                filterBy =
//                    (parent?.getItemAtPosition(position) as LstAttributesDetail).AttributeId!!
//                (activity as MyActivity).filterID = filterBy
//                product_name.text =
//                    (parent.getItemAtPosition(position) as LstAttributesDetail).AttributeType
//                GetWishePoint(filterBy)
//
//                if (filterBy != -1) {
//                    fromlist = 1
//                    product_name_point_balance_myearning.visibility = View.VISIBLE
//                } else product_name_point_balance_myearning.visibility = View.GONE
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//
//        }

//        myearing_rv.adapter = MyEarningAdapter(null, this)
    }

    fun GetWishePoint(locationAttributeID: Int) {

        val wishPointRequest = WishPointRequest()
        wishPointRequest.ActorId =
            PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].UserId.toString()
        wishPointRequest.ActionType = "1"
        wishPointRequest.LocationId = locationAttributeID.toString()
        viewModel.wishPointsLiveData(wishPointRequest)

    }


    /* override fun setUserVisibleHint(isVisibleToUser: Boolean) {
         super.setUserVisibleHint(isVisibleToUser)
         if (isVisibleToUser) {
             try {
                 GetWishePoint((activity as MyActivity).filterID)
             } catch (e: Exception) {
             }
         }
     }*/

//    fun FilterDislplay() {
//
//        if (filterDisplay.visibility == View.VISIBLE) {
//            filterDisplay.animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_up)
//            filterDisplay.visibility = View.GONE
//        } else if (filterDisplay.visibility == View.GONE) {
//            filterDisplay.animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_down)
//            filterDisplay.visibility = View.VISIBLE
//        }
//
//    }

    override fun onItemClicked(offersPromotions: LstPromotionList?) {
    }


    private fun getTransactionHistory(FilterID: Int, FromDate: String, ToDate: String) {

        LoadingDialogue.showDialog(requireContext())
        val transactionHistoryRequest = TransactionHistoryRequest()
        transactionHistoryRequest.ActorId = PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].UserId.toString()
        transactionHistoryRequest.MerchantId = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJson!![0].MerchantId
        transactionHistoryRequest.LocationId = FilterID
        transactionHistoryRequest.CreatedBy = -1

        if (FromDate.isNotEmpty()) transactionHistoryRequest.JFromDate = FromDate
        if (ToDate.isNotEmpty()) transactionHistoryRequest.JToDate = ToDate

        earningRedemptionViewModel.transactionHistoryLiveData(transactionHistoryRequest)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        earningRedemptionViewModel.transactionHistoryListLiveData.observe(viewLifecycleOwner, Observer {
                LoadingDialogue.dismissDialog()
                if (it != null && !it.lstRewardTransJsonDetails.isNullOrEmpty()) {

                    myearing_rv.adapter = MyEarningAdapter(it, this)
                    earning_error.visibility = View.GONE
                    myearing_rv.visibility = View.VISIBLE

                } else {

                    earning_error.visibility = View.VISIBLE
                    myearing_rv.visibility = View.GONE

//                FromDate = ""
//                ToDate = ""

                }
            })

        viewModel.wisePointLiveData.observe(viewLifecycleOwner, {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.ObjCustomerDashboardList.isNullOrEmpty()) {

                var productPointBalance = 0

                //      product_point.text =  it.ObjCustomerDashboardList[0].BehaviourWisePoints.toString()

                it.ObjCustomerDashboardList.forEachIndexed { index, objCustomerDashboards ->
                    productPointBalance += it.ObjCustomerDashboardList[index].BehaviourWisePoints!!
                }

//                product_point.text = productPointBalance.toString()

            } else {
//                product_point.text = "0"

            }

        })

    }


}
