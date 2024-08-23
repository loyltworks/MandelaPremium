package com.loyltworks.mandelapremium.ui.help

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.oneloyalty.goodpack.utils.BlockMultipleClick
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.help.supportLisitng.MyQueryActivity
import com.loyltworks.mandelapremium.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_help.*

class HelpActivity : BaseActivity(), View.OnClickListener {

    override fun callInitialServices() {
    }

    override fun callObservers() {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //set context
        context = this

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        //supportActionBar!!.setHomeAsUpIndicator(upArrow)

        my_query.setOnClickListener(this)
        contact_us.setOnClickListener(this)

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
            R.id.my_query -> {
                startActivity(Intent(context, MyQueryActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }
            R.id.contact_us -> {
                startActivity(Intent(context, CustomerServiceActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

            }
        }


    }


}