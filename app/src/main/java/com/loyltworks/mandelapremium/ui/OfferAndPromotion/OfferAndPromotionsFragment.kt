package com.loyltworks.mandelapremium.ui.OfferAndPromotion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.loyltworks.mandelapremium.databinding.FragmentOfferAndPromotionsBinding
import com.loyltworks.mandelapremium.model.GetPromotionResponse
import com.loyltworks.mandelapremium.ui.OfferAndPromotion.fragment.PromotionTab1Fragment
import com.loyltworks.mandelapremium.ui.OfferAndPromotion.fragment.PromotionTab2Fragment
import com.loyltworks.mandelapremium.ui.OfferAndPromotion.fragment.PromotionTab3Fragment
import com.loyltworks.mandelapremium.ui.profile.MyPagerAdapter

class OfferAndPromotionsFragment : Fragment() {

    lateinit var binding: FragmentOfferAndPromotionsBinding
    lateinit var promotionViewModel: PromotionViewModel

    lateinit var getPromotionResponse: GetPromotionResponse

    lateinit var myPagerAdapter: MyPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOfferAndPromotionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /** &&&&&&&&&&&&&&&&&&   Tab Items Declared with MyPageAdapter  &&&&&&&&&&&&&&&&&&&&&&  */

        setupViewPager(binding.pager)
        binding.tablayout!!.setupWithViewPager(binding.pager)

        promotionViewModel = ViewModelProvider(this).get(PromotionViewModel::class.java)

       /* promotionViewModel.getPromotion(
            GetWhatsNewRequest(
                "99", PreferenceHelper.getLoginDetails(
                    requireContext()
                )?.UserList!![0].UserId.toString()
            )
        )*/
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       /* promotionViewModel.getPromotionListLiveData.observe(viewLifecycleOwner, Observer {
            ProgressDialogue.dismissDialog()
            if (it != null && !it.LstPromotionJsonList.isNullOrEmpty()) {
                getPromotionResponse = it
            }
        })*/
    }


    private fun setupViewPager(viewPager: ViewPager) {

        myPagerAdapter = MyPagerAdapter(childFragmentManager)
        myPagerAdapter.addFrag(PromotionTab1Fragment(), "Current")
        myPagerAdapter.addFrag(PromotionTab2Fragment(), "Upcoming")
        myPagerAdapter.addFrag(PromotionTab3Fragment(), "Previous")
        viewPager.adapter = myPagerAdapter

    }


//    fun ChooseFragmentToShowingFilterView() {
//
//        /*
//            val promotionTab1Fragment: PromotionTab1Fragment? = fragmentManager?.findFragmentByTag("PromotionTab1Fragment") as PromotionTab1Fragment?
//                if (promotionTab1Fragment != null && promotionTab1Fragment.isVisible) {
//                         val fragment: PromotionTab1Fragment = binding.pager.adapter!!.instantiateItem(binding.pager, binding.pager.currentItem) as PromotionTab1Fragment
//                         fragment.FilterDislplay()}
//                     */
//
//
//        val tabPosition = binding.tablayout.selectedTabPosition
//
//        when (tabPosition) {
//            0 -> {
//                val fragment: PromotionTab1Fragment = binding.pager.adapter!!.instantiateItem(
//                    binding.pager,
//                    binding.pager.currentItem
//                ) as PromotionTab1Fragment
//                fragment.FilterDislplay()
//            }
//            1 -> {
//                val fragment: PromotionTab2Fragment = binding.pager.adapter!!.instantiateItem(
//                    binding.pager,
//                    binding.pager.currentItem
//                ) as PromotionTab2Fragment
//                fragment.FilterDislplay()
//            }
//            2 -> {
//                val fragment: PromotionTab3Fragment = binding.pager.adapter!!.instantiateItem(
//                    binding.pager,
//                    binding.pager.currentItem
//                ) as PromotionTab3Fragment
//                fragment.FilterDislplay()
//            }
//        }
//
//
//    }


}