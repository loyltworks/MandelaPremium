package com.loyltworks.mandelapremium.ui.GiftCards.fragment.MyVoucher

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.LstPromotionList
import com.loyltworks.mandelapremium.model.lstMerchantinfo
import com.loyltworks.mandelapremium.ui.GiftCards.GiftCardsActivity
import com.loyltworks.mandelapremium.ui.GiftCards.adapter.MyVoucherAdapter
import com.loyltworks.mandelapremium.ui.OfferAndPromotion.OfferPromotionDetailsActivity
import com.loyltworks.mandelapremium.ui.OfferAndPromotion.adapter.offerAndPromotionAdapter
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.fragment_my_voucher.*
import kotlinx.android.synthetic.main.fragment_promotion_tab1.*

class MyVoucherFragment : Fragment(), MyVoucherAdapter.OnItemClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_voucher, container, false)

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
                    my_voucher_rv.visibility = View.VISIBLE
                    error_voucher.visibility = View.GONE
                    my_voucher_rv.adapter = MyVoucherAdapter(lstMerchantinfo, this)
                }else{
                    my_voucher_rv.visibility = View.GONE
                    error_voucher.visibility = View.VISIBLE
                }

            }else {
                my_voucher_rv.visibility = View.GONE
                error_voucher.visibility = View.VISIBLE
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