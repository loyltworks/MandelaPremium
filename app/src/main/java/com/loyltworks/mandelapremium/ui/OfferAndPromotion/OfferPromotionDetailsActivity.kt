package com.loyltworks.mandelapremium.ui.OfferAndPromotion

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.LstPromotionList
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.dashboard.DashBoardViewModel
import kotlinx.android.synthetic.main.activity_offer_promotion_details.*

class OfferPromotionDetailsActivity : BaseActivity() {

    private lateinit var promotionViewModel: PromotionViewModel

    lateinit var offersPromotions: LstPromotionList


    override fun callInitialServices() {
    }

    override fun callObservers() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_promotion_details)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (intent != null)
            offersPromotions = intent.getSerializableExtra("PromotionDetails") as LstPromotionList

        promotionViewModel = ViewModelProvider(this).get(PromotionViewModel::class.java)

        //set context
        context = this

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)

//        Log.d("fjkjdkas", " :"+ offersPromotions.PromotionName.toString())

        promotion_name.text = offersPromotions.PromotionName
        promotion_claim_id.text = offersPromotions.Claim


        promotion_valid_till.text = offersPromotions.ValidFrom!!.split(" ")[0] + " to " + offersPromotions.ValidTo!!.split(" ")[0]
        redeemable_outlet.text = offersPromotions.OutletName

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            promotion_offer_details.text =
                Html.fromHtml(offersPromotions.ProLongDesc, Html.FROM_HTML_MODE_COMPACT)
        else promotion_offer_details.text = Html.fromHtml(offersPromotions.ProLongDesc)

        Glide.with(this).asBitmap().error(R.drawable.temp_offer_promotion)
            .placeholder(R.drawable.placeholder).load(
                BuildConfig.PROMO_IMAGE_BASE + offersPromotions.ProImage!!.replace(
                    "..",
                    ""
                )
            ).into(promotion_image)
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