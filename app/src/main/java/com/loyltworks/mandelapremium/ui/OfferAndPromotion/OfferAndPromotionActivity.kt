package com.loyltworks.mandelapremium.ui.OfferAndPromotion

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityOfferAndPromotionBinding
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity

class OfferAndPromotionActivity: BaseActivity() {

    lateinit var binding: ActivityOfferAndPromotionBinding

    override fun callInitialServices() {
    }

    override fun callObservers() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOfferAndPromotionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set context
        context = this



        var mFragment: Fragment? = null
        mFragment = OfferAndPromotionsFragment()
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.frameLayout, mFragment).commit()


        binding.back.setOnClickListener{
            onBackPressed()
        }






    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }


}