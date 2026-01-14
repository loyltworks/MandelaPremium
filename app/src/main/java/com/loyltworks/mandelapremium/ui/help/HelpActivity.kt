package com.loyltworks.mandelapremium.ui.help

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityHelpBinding
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.help.supportLisitng.MyQueryActivity
import com.oneloyalty.goodpack.utils.BlockMultipleClick

class HelpActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding: ActivityHelpBinding
   
    override fun callInitialServices() {
    }

    override fun callObservers() {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //set context
        context = this

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        //supportActionBar!!.setHomeAsUpIndicator(upArrow)

        binding.myQuery.setOnClickListener(this)
        binding.contactUs.setOnClickListener(this)
        binding.back.setOnClickListener(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    override fun onClick(v: View?) {
        if (BlockMultipleClick.click()) return
        when (v!!.id) {
            R.id.myQuery -> {
                startActivity(Intent(context, MyQueryActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }
            R.id.contactUs -> {
                startActivity(Intent(context, CustomerServiceActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }
            R.id.back -> {
                onBackPressed()
            }
        }


    }


}