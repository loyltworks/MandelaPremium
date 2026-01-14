package com.loyltworks.mandelapremium.ui.help

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityCustomerServiceBinding
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity

class CustomerServiceActivity : BaseActivity() {

    lateinit var binding: ActivityCustomerServiceBinding
    override fun callInitialServices() {
    }

    override fun callObservers() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set context
        context = this

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        //supportActionBar!!.setHomeAsUpIndicator(upArrow)


        binding.emailBtn.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "help@mandelaclubpremium.com", null
                )
            )
            intent.putExtra(Intent.EXTRA_SUBJECT, "")
            intent.putExtra(Intent.EXTRA_TEXT, "")
            startActivity(Intent.createChooser(intent, "Choose an Email client :"))
        }


        binding.callBtn.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_DIAL, Uri.fromParts("tel", "+ 25699000000", null)
                )
            )
        }

        binding.back.setOnClickListener {
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