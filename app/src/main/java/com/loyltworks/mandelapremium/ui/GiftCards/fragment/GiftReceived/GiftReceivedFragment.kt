package com.loyltworks.mandelapremium.ui.GiftCards.fragment.GiftReceived

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyltworks.mandelapremium.R
import androidx.lifecycle.Observer
import com.loyltworks.mandelapremium.model.lstMerchantinfo
import com.loyltworks.mandelapremium.ui.GiftCards.GiftCardsActivity
import com.loyltworks.mandelapremium.ui.GiftCards.adapter.MyVoucherAdapter
import com.loyltworks.mandelapremium.ui.GiftCards.fragment.MyVoucher.MyVoucherDetailsActivity
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.fragment_gift_received.*
import kotlinx.android.synthetic.main.fragment_my_voucher.*


class GiftReceivedFragment : Fragment(), MyVoucherAdapter.OnItemClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gift_received, container, false)

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
                        gift_received_rv.visibility = View.VISIBLE
                        error_gift_recevied.visibility = View.GONE
                        gift_received_rv.adapter = MyVoucherAdapter(it.lstMerchantinfo, this)
                    }else{
                        gift_received_rv.visibility = View.GONE
                        error_gift_recevied.visibility = View.VISIBLE
                    }

                }else {
                    gift_received_rv.visibility = View.GONE
                    error_gift_recevied.visibility = View.VISIBLE
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


