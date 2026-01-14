package com.loyltworks.mandelapremium.ui.OfferAndPromotion

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityOfferPromotionDetailsBinding
import com.loyltworks.mandelapremium.model.LstPromotionList
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity

class OfferPromotionDetailsActivity : BaseActivity() {
    lateinit var binding: ActivityOfferPromotionDetailsBinding

    private lateinit var promotionViewModel: PromotionViewModel

    lateinit var offersPromotions: LstPromotionList


    override fun callInitialServices() {
    }

    override fun callObservers() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOfferPromotionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (intent != null)
            offersPromotions = intent.getSerializableExtra("PromotionDetails") as LstPromotionList

        promotionViewModel = ViewModelProvider(this).get(PromotionViewModel::class.java)

        //set context
        context = this

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)

//        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
//            resources.getDrawable(R.drawable.ic_back_arrow)
        //supportActionBar!!.setHomeAsUpIndicator(upArrow)

//        Log.d("fjkjdkas", " :"+ offersPromotions.PromotionName.toString())

        binding.promotionName.text = offersPromotions.PromotionName
        //promotion_claim_id.text = offersPromotions.Claim


        binding.promotionValidTill.text = offersPromotions.ValidFrom!!.split(" ")[0] + " to " + offersPromotions.ValidTo!!.split(" ")[0]
        binding.redeemableOutlet.text = offersPromotions.OutletName

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            binding.promotionOfferDetails.text =
                Html.fromHtml(offersPromotions.ProLongDesc, Html.FROM_HTML_MODE_COMPACT)
        else binding.promotionOfferDetails.text = Html.fromHtml(offersPromotions.ProLongDesc)

        Glide.with(this).asBitmap().error(R.drawable.dummy_image)
            .placeholder(R.drawable.dummy_image).load(
                BuildConfig.PROMO_IMAGE_BASE + offersPromotions.ProImage!!.replace(
                    "..",
                    ""
                )
            ).into(binding.promotionImage)


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