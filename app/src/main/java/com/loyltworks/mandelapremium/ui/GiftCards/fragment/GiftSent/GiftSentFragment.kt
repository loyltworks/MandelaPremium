package com.loyltworks.mandelapremium.ui.GiftCards.fragment.GiftSent

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.FragmentGiftSentBinding
import com.loyltworks.mandelapremium.model.lstMerchantinfo
import com.loyltworks.mandelapremium.ui.GiftCards.GiftCardsActivity
import com.loyltworks.mandelapremium.ui.GiftCards.adapter.MyVoucherAdapter
import com.loyltworks.mandelapremium.ui.GiftCards.fragment.MyVoucher.MyVoucherDetailsActivity
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue


class GiftSentFragment : Fragment(), MyVoucherAdapter.OnItemClickListener {

    lateinit var binding: FragmentGiftSentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGiftSentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as GiftCardsActivity?)!!.giftCardsViewModel.getGiftCardListLiveData.observe(viewLifecycleOwner, Observer {

            LoadingDialogue.dismissDialog()
            if (it != null && !it.lstMerchantinfo.isNullOrEmpty()) {
                val lstMerchantinfo:MutableList<lstMerchantinfo> = mutableListOf()

                it.lstMerchantinfo.forEach{
                    if(it.GiftedUserId.equals(PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].UserId.toString()) && it.IsEncash == false && it.IsGifted == false){
                        lstMerchantinfo.add(it)
                    }
                }
                if(lstMerchantinfo.isNotEmpty()){
                    binding.giftSentRv.visibility = View.VISIBLE
                    binding.errorGiftSent.visibility = View.GONE
                    binding.giftSentRv.adapter = MyVoucherAdapter(lstMerchantinfo, this)
                }else{
                    binding.giftSentRv.visibility = View.GONE
                    binding.errorGiftSent.visibility = View.VISIBLE
                }
            }else {
                binding.giftSentRv.visibility = View.GONE
                binding.errorGiftSent.visibility = View.VISIBLE
            }
        })
    }


    override fun onItemClicked(lstMerchantinfo: lstMerchantinfo?) {
        val intent = Intent(context, MyVoucherDetailsActivity::class.java)
        intent.putExtra("lstMerchantinfo", lstMerchantinfo)
        intent.putExtra("GiftCard", 2)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }
}