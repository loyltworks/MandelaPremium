package com.loyltworks.mandelapremium.ui.OfferAndPromotion.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.GetWhatsNewRequest
import com.loyltworks.mandelapremium.model.LstAttributesDetail
import com.loyltworks.mandelapremium.model.LstPromotionList
import com.loyltworks.mandelapremium.model.WishPointRequest
import com.loyltworks.mandelapremium.ui.MyActivity.MyActivityViewModel
import com.loyltworks.mandelapremium.ui.OfferAndPromotion.OfferAndPromotionsFragment
import com.loyltworks.mandelapremium.ui.OfferAndPromotion.OfferPromotionDetailsActivity
import com.loyltworks.mandelapremium.ui.OfferAndPromotion.adapter.offerAndPromotionAdapter
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.fragment_promotion_tab1.promotion_error
import kotlinx.android.synthetic.main.fragment_promotion_tab1.promotion_rv
import java.text.SimpleDateFormat
import java.util.Date


class PromotionTab2Fragment : Fragment(), offerAndPromotionAdapter.OnItemClickListener {

    var filterBy:Int = -1

    lateinit var parentFrag: OfferAndPromotionsFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var root =  inflater.inflate(R.layout.fragment_promotion_tab1, container, false)

//        root.filterBtn.setOnClickListener { v ->
//            if (filterBy != -1) {
//                LoadingDialogue.showDialog(requireContext())
//                getOfferPromotionsHistory(filterBy)
//            } else Toast.makeText(activity, "Please select product", Toast.LENGTH_SHORT).show()
//
//        }

        return root
    }

    lateinit var lstAttributesDetail: ArrayList<LstAttributesDetail>
    var locationAttributeID: Int = 0
    private lateinit var viewModel : MyActivityViewModel



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         parentFrag = parentFragment as OfferAndPromotionsFragment
        viewModel = ViewModelProvider(this).get(MyActivityViewModel::class.java)



        parentFrag.promotionViewModel.getPromotionListLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.LstPromotionJsonList.isNullOrEmpty()) {
                val lstPromotionJsonList:MutableList<LstPromotionList> = mutableListOf()

                it.LstPromotionJsonList?.forEachIndexed{index, lstPromotionList ->

                    val sdf = SimpleDateFormat("MM/dd/yyyy")
                    val ValidFrom: Date = sdf.parse(lstPromotionList.ValidFrom!!)

                    if (Date().before(ValidFrom)){
                        lstPromotionJsonList.add(lstPromotionList)
                    }
                }
                if(lstPromotionJsonList.isNotEmpty()){
                    promotion_rv.adapter = offerAndPromotionAdapter(lstPromotionJsonList,  this)
                    promotion_rv.visibility = View.VISIBLE
                    promotion_error.visibility = View.GONE
                }else{
                    promotion_rv.visibility = View.GONE
                    promotion_error.visibility = View.VISIBLE
                }
            }else{
                promotion_rv.visibility = View.GONE
                promotion_error.visibility = View.VISIBLE
            }
        })


         lstAttributesDetail = requireActivity().intent.getSerializableExtra("mulipleProducts") as ArrayList<LstAttributesDetail>

        val selectedPosition = requireActivity().intent.getIntExtra("SelectedPosition",0)

        var fromlist =  requireActivity().intent.getIntExtra("fromList",0)

//        try {
//            if (fromlist == 0) {
//                product_name_point_balance_promotion_one.visibility = View.GONE
//            } else {
//                product_name_point_balance_promotion_one.visibility = View.VISIBLE
//            }
//
//            product_namessss.text = lstAttributesDetail[selectedPosition].AttributeType
//        } catch (e: Exception) {
//        }


        filterBy = lstAttributesDetail[selectedPosition].AttributeId!!
        locationAttributeID = lstAttributesDetail[selectedPosition].AttributeId!!.toInt()



        GetWishePoint(locationAttributeID)


        getOfferPromotionsHistory(filterBy)



//        current_product_status.adapter = CustomSpinnersAdapter(requireContext(), lstAttributesDetail)
//        current_product_status.setSelection(selectedPosition)
//
//
//        current_product_status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                filterBy = (parent?.getItemAtPosition(position) as LstAttributesDetail).AttributeId!!
//
//                try {
//                    product_namessss.text = (parent.getItemAtPosition(position) as LstAttributesDetail).AttributeType
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//
//                GetWishePoint(filterBy)
//
//                try {
//                    if (filterBy != -1){
//                        fromlist = 1
//                        product_name_point_balance_promotion_one.visibility = View.VISIBLE
//                    } else   product_name_point_balance_promotion_one.visibility = View.GONE
//                } catch (e: Exception) {
//                }
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

    private fun getOfferPromotionsHistory(filterID: Int) {

        parentFrag.promotionViewModel.getPromotion(
            GetWhatsNewRequest(
                "259",
                filterID,
                PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].UserId.toString()
            )
        )
    }

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
//
//    }

    override fun onItemClicked(offersPromotions: LstPromotionList?) {
        val intent = Intent(context, OfferPromotionDetailsActivity::class.java)
        intent.putExtra("PromotionDetails", offersPromotions)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        viewModel.wisePointLiveData.observe(viewLifecycleOwner, {
//            LoadingDialogue.dismissDialog()
//            if (it != null && !it.ObjCustomerDashboardList.isNullOrEmpty()){
//
//                try {
//                    product_pointssss.text =  it.ObjCustomerDashboardList[0].BehaviourWisePoints.toString()
//                } catch (e: Exception) {
//                }
//
//            }else {
//                product_pointssss.text = "0"
//            }
//
//        })

    }



}