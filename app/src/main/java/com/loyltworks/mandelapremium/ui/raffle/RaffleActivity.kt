package com.loyltworks.mandelapremium.ui.raffle

import android.annotation.SuppressLint
import android.os.Bundle
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityRaffleBinding
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity

class RaffleActivity  : BaseActivity() {

    lateinit var binding: ActivityRaffleBinding

    override fun callInitialServices() {
    }

    override fun callObservers() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRaffleBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //set context
        context = this

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        //supportActionBar!!.setHomeAsUpIndicator(upArrow)

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