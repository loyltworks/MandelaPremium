package com.loyltworks.mandelapremium.ui.GiftCards.fragment.GiftReceived

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.FragmentGiftReceivedBinding
import com.loyltworks.mandelapremium.model.lstMerchantinfo
import com.loyltworks.mandelapremium.ui.GiftCards.GiftCardsActivity
import com.loyltworks.mandelapremium.ui.GiftCards.adapter.MyVoucherAdapter
import com.loyltworks.mandelapremium.ui.GiftCards.fragment.MyVoucher.MyVoucherDetailsActivity
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue


class GiftReceivedFragment : Fragment(), MyVoucherAdapter.OnItemClickListener {
    
    lateinit var binding: FragmentGiftReceivedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGiftReceivedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as GiftCardsActivity?)!!.giftCardsViewModel.getGiftCardListLiveData.observe(
            viewLifecycleOwner,
            Observer {
                LoadingDialogue.dismissDialog()
                if (it != null && !it.lstMerchantinfo.isNullOrEmpty()) {

                    val lstMerchantinfo:MutableList<lstMerchantinfo> = mutableListOf()

                    it.lstMerchantinfo.forEach{
                        if(it.ReceiverLoyaltyId.equals(PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].UserName.toString()) && it.IsEncash == false && it.IsGifted == false){
                            lstMerchantinfo.add(it)
                        }
                    }
                    if(lstMerchantinfo.isNotEmpty()){
                        binding.giftReceivedRv.visibility = View.VISIBLE
                        binding.errorGiftRecevied.visibility = View.GONE
                        binding.giftReceivedRv.adapter = MyVoucherAdapter(it.lstMerchantinfo, this)
                    }else{
                        binding.giftReceivedRv.visibility = View.GONE
                        binding.errorGiftRecevied.visibility = View.VISIBLE
                    }

                }else {
                    binding.giftReceivedRv.visibility = View.GONE
                    binding.errorGiftRecevied.visibility = View.VISIBLE
                }

            })
    }

    override fun onItemClicked(lstMerchantinfo: lstMerchantinfo?) {
        val intent = Intent(context, MyVoucherDetailsActivity::class.java)
        intent.putExtra("lstMerchantinfo", lstMerchantinfo)
        intent.putExtra("GiftCard", 1)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }
}


