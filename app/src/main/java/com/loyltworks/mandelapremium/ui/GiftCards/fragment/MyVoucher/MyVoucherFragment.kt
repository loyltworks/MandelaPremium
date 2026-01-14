package com.loyltworks.mandelapremium.ui.GiftCards.fragment.MyVoucher

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.FragmentMyVoucherBinding
import com.loyltworks.mandelapremium.model.lstMerchantinfo
import com.loyltworks.mandelapremium.ui.GiftCards.GiftCardsActivity
import com.loyltworks.mandelapremium.ui.GiftCards.adapter.MyVoucherAdapter
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue

class MyVoucherFragment : Fragment(), MyVoucherAdapter.OnItemClickListener {


    lateinit var binding: FragmentMyVoucherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyVoucherBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as GiftCardsActivity?)!!.giftCardsViewModel.getGiftCardListLiveData.observe(viewLifecycleOwner, Observer {

            LoadingDialogue.dismissDialog()
            if (it != null && !it.lstMerchantinfo.isNullOrEmpty()) {

                val lstMerchantinfo:MutableList<lstMerchantinfo> = mutableListOf()

                it.lstMerchantinfo.forEach{
                    if(it.IsEncash == true && it.IsGifted == false){
                        lstMerchantinfo.add(it)
                    }
                }
                if(lstMerchantinfo.isNotEmpty()){
                    binding.myVoucherRv.visibility = View.VISIBLE
                    binding.errorVoucher.visibility = View.GONE
                    binding.myVoucherRv.adapter = MyVoucherAdapter(lstMerchantinfo, this)
                }else{
                    binding.myVoucherRv.visibility = View.GONE
                    binding.errorVoucher.visibility = View.VISIBLE
                }

            }else {
                binding.myVoucherRv.visibility = View.GONE
                binding.errorVoucher.visibility = View.VISIBLE
            }
        })
    }

    override fun onItemClicked(lstMerchantinfo: lstMerchantinfo?) {
        val intent = Intent(context, MyVoucherDetailsActivity::class.java)
        intent.putExtra("lstMerchantinfo", lstMerchantinfo)
        intent.putExtra("GiftCard", 0)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

    }
}