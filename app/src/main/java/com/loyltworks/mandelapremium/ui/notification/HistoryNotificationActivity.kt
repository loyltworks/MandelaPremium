package com.loyltworks.mandelapremium.ui.notification

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityHistoryNotificationBinding
import com.loyltworks.mandelapremium.model.HistoryNotificationDetailsRequest
import com.loyltworks.mandelapremium.model.HistoryNotificationRequest
import com.loyltworks.mandelapremium.model.LstPushHistory
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.SwipeToDeleteCallback
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import com.oneloyalty.goodpack.ui.history_notifiation.adapter.HistoryNotificationAdapter

class HistoryNotificationActivity : BaseActivity(), HistoryNotificationAdapter.ItemClicked {
   
    lateinit var binding: ActivityHistoryNotificationBinding

    private lateinit var viewModel: HistoryNotificationViewModel
    private lateinit var historyNotificationAdapter: HistoryNotificationAdapter

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun callInitialServices() {

        LoadingDialogue.showDialog(this)


        viewModel.getNotificationHistoryResponse(HistoryNotificationRequest(
            ActionType = "0",
            ActorId = PreferenceHelper.getLoginDetails(applicationContext)?.UserList!![0].UserId.toString(),
            LoyaltyId =PreferenceHelper.getLoginDetails(applicationContext)?.UserList!![0].UserName
        ))

        viewModel.historyNotificationtDetailByIDLiveData.observe(this, androidx.lifecycle.Observer {
            if (it != null && !it.lstPushHistoryJson.isNullOrEmpty()) {
                setReadMoreHistoryNotificationObserver()
            }
        })

        viewModel.historyNotificationtDeleteByIDLiveData.observe(this, androidx.lifecycle.Observer {
            if (it != null && it.ReturnValue!! > 0) {

                Toast.makeText(this,"Notification was removed from the list", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this,"Notification failed to remove from the list", Toast.LENGTH_SHORT).show()
            }
        })

        enableSwipeToDeleteAndUndo()
    }

    private fun setReadMoreHistoryNotificationObserver() {
        viewModel.historyNotificationtDetailByIDLiveData.observe(this, androidx.lifecycle.Observer {  })

    }

    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback: SwipeToDeleteCallback = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                val position = viewHolder.adapterPosition
                val item: LstPushHistory = historyNotificationAdapter?.getData()?.get(position)!!
                historyNotificationAdapter?.removeItem(position)
                viewModel.getDeleteHistoryNotificationResponse(
                    HistoryNotificationDetailsRequest(
                        ActionType = "2",
                        ActorId = PreferenceHelper.getLoginDetails(applicationContext)?.UserList!![0].UserId.toString(),
                        LoyaltyId = PreferenceHelper.getLoginDetails(applicationContext)?.UserList!![0].UserName,
                        PushHistoryIds = item.PushHistoryId.toString()
                    )
                )
            }
        }

        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(binding.historyRv)
    }

    override fun callObservers() {

        viewModel.historyNotificationtLiveData.observe(this, androidx.lifecycle.Observer {
            if (it != null && !it.lstPushHistoryJson.isNullOrEmpty()) {
                historyNotificationAdapter =  HistoryNotificationAdapter(it, this)
                binding.historyRv.adapter = historyNotificationAdapter
                binding.noHistoryNotificationTv.visibility = View.GONE
                binding.historyRv.visibility = View.VISIBLE
                LoadingDialogue.dismissDialog()
            } else {
                binding.noHistoryNotificationTv.visibility = View.VISIBLE
                binding.historyRv.visibility = View.GONE
                LoadingDialogue.dismissDialog()
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryNotificationViewModel::class.java)
        binding = ActivityHistoryNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)

        //set context
        context = this

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)
        //supportActionBar!!.setDisplayShowTitleEnabled(false)

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        //supportActionBar!!.setHomeAsUpIndicator(upArrow)

//        historyNotificationAdapter = HistoryNotificationAdapter(null, this)
//        binding.historyRv.adapter = historyNotificationAdapter
        binding.noHistoryNotificationTv.visibility = View.GONE
        binding.historyRv.visibility = View.VISIBLE

        binding.back.setOnClickListener{
            onBackPressed()
        }
    }

    override fun itemclicks(notificationHistory: LstPushHistory?) {
        viewModel.getHistoryNotificationDetailById(
            HistoryNotificationDetailsRequest(
                ActionType = "0",
                ActorId = PreferenceHelper.getLoginDetails(applicationContext)?.UserList!![0].UserId.toString(),
                LoyaltyId = PreferenceHelper.getLoginDetails(applicationContext)?.UserList!![0].UserName,
                PushHistoryIds = notificationHistory?.PushHistoryId.toString()
            )
        )
    }

}

