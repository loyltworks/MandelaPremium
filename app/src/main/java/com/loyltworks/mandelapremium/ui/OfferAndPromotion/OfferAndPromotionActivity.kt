package com.loyltworks.mandelapremium.ui.OfferAndPromotion

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.GetWhatsNewRequest
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import kotlinx.android.synthetic.main.activity_offer_and_promotion.*

class OfferAndPromotionActivity: BaseActivity() {


    override fun callInitialServices() {
    }

    override fun callObservers() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_and_promotion)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        //set context
        context = this

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        //supportActionBar!!.setHomeAsUpIndicator(upArrow)

        var mFragment: Fragment? = null
        mFragment = OfferAndPromotionsFragment()
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.frameLayout, mFragment).commit()




        // Calling offer promotion fragment method

      /*  filter_date_display.setOnClickListener {
            var fragment = supportFragmentManager.findFragmentById(R.id.frameLayout) as OfferAndPromotionsFragment
            fragment.ChooseFragmentToShowingFilterView()

        }
*/

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