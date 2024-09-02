package com.loyltworks.mandelapremium.ui.notification

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.oneloyalty.goodpack.ui.history_notifiation.adapter.HistoryNotificationAdapter
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.model.HistoryNotificationDetailsRequest
import com.loyltworks.mandelapremium.model.HistoryNotificationRequest
import com.loyltworks.mandelapremium.model.LstPushHistory
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.dashboard.DashboardActivity
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.SwipeToDeleteCallback
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.activity_history_notification.*
import java.util.Observer

class HistoryNotificationActivity : BaseActivity(), HistoryNotificationAdapter.ItemClicked {

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

                Toast.makeText(this,"Notification removed from the list", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this,"Failed to remove the notification from the list", Toast.LENGTH_SHORT).show()
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
        itemTouchhelper.attachToRecyclerView(history_rv)
    }

    override fun callObservers() {

        viewModel.historyNotificationtLiveData.observe(this, androidx.lifecycle.Observer {
            if (it != null && !it.lstPushHistoryJson.isNullOrEmpty()) {
                historyNotificationAdapter =  HistoryNotificationAdapter(it, this)
                history_rv.adapter = historyNotificationAdapter
                no_history_notification_tv.visibility = View.GONE
                history_rv.visibility = View.VISIBLE
                LoadingDialogue.dismissDialog()
            } else {
                no_history_notification_tv.visibility = View.VISIBLE
                history_rv.visibility = View.GONE
                LoadingDialogue.dismissDialog()
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel =
            ViewModelProvider(this).get(HistoryNotificationViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_notification)
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
//        history_rv.adapter = historyNotificationAdapter
        no_history_notification_tv.visibility = View.GONE
        history_rv.visibility = View.VISIBLE

        back.setOnClickListener{
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

