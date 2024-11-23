package com.loyltworks.mandelapremium.ui.baseClass

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.LayerDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.ui.notification.HistoryNotificationActivity
import com.loyltworks.mandelapremium.utils.Count.Companion.setCounting
import com.loyltworks.mandelapremium.utils.dialogBox.NoInternetDialog
import com.loyltworks.mandelapremium.utils.internet.ConnectivityReceiver
import com.loyltworks.mandelapremium.utils.internet.ConnectivityReceiverListener
import com.oneloyalty.goodpack.utils.BlockMultipleClick

/** Created by : aslam
 *  Project : MandelaPremium
 *  Date : 18 jan 2021 */

abstract class BaseActivity : AppCompatActivity(), ConnectivityReceiverListener {

    open var context: Context? = null


    private var hideNotInternetDialogue = false  // default show no internet dialogue

    lateinit var viewGroup: ViewGroup

    // connectivity Receiver class
    private var connectivityReceiver = ConnectivityReceiver


    // call the services on internet present
    abstract fun callInitialServices()

    // call the observers onCreate
    abstract fun callObservers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "${this.localClassName} : Created Base Activity")


    }


    override fun onStart() {
        super.onStart()

        context = this

        // call the observers onCreate
        callObservers()


        Log.d(TAG, "${this.localClassName} : OnStart Base Activity")
        // register connectivity receiver
        registerConnectivityReceiver(true)

        // get view groups
        viewGroup =
            (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup

    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "${this.localClassName} : Pause Base Activity")

    }

    override fun onDestroy() {
        Log.d(TAG, "${this.localClassName} : Destroyed Base Activity")
        registerConnectivityReceiver(false)
        super.onDestroy()
    }

    private fun registerConnectivityReceiver(isRegister: Boolean) {
        if (isRegister) {
            // set network listener callback
            connectivityReceiver.connectivityReceiverListener = this
            // register the network check broadcast receiver
            registerReceiver(
                connectivityReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        } else {

            try {
                // unregister the connectivity receiver
                unregisterReceiver(connectivityReceiver)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }

        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
            Log.d(TAG, "${this.localClassName} : onNetworkConnectionChanged: $isConnected")
            // dismiss no internet dialog on internet back
            NoInternetDialog.dismissDialog()
            // call the services on internet back
            callInitialServices()
        } else {
            Log.d(TAG, "${this.localClassName} : onNetworkConnectionChanged: $isConnected")
            if (!hideNotInternetDialogue) {
                // show no internet dialog on no internet
                NoInternetDialog.showDialog(this)
            }
        }

    }

    // optional for display or hide no internet dialogue
    fun hideNoInternetDialog(hide: Boolean) {
        hideNotInternetDialogue = hide
    }

    companion object {
        const val TAG = "BaseActivity"
    }

    // SnackBar display
    @RequiresApi(Build.VERSION_CODES.M)
     fun snackBar(MSG: String, color: Int) {
        Snackbar.make(viewGroup, MSG, Snackbar.LENGTH_LONG).setBackgroundTint(
            getColor(color)
        ).show()
    }


    /*  */
    /**
     * ############## Multiple Click disable  ###################
     */
    /*

    // disable multiple click
    public long mLastClickTime = 0;

    public boolean isClicked() {
        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return true;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        return false;
    }

    */
    /**
     * ############## EOF Multiple Click disable  ###################
     */
    /*
*/
    /**
     * ############## Draw count on icon  ###################
     */
    var notification: MenuItem? = null

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        notification = menu.findItem(R.id.notification)
        val icon = notification!!.icon as LayerDrawable
        setCounting(this, icon, "0")


//        cart.setVisible(true);
        if (context != null) {
            if (context!!.javaClass.simpleName == "DashboardActivity") {
                notification!!.isVisible = true
            } else {
                notification!!.isVisible = false
            }
        } else {
            notification!!.isVisible = true
        }

        return true
    }

    open fun setBadgeCount(count: String?) {
        val icon = notification!!.icon as LayerDrawable
        setCounting(this, icon, count!!)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.notification -> {
                if (BlockMultipleClick.click()) return false
                startActivity(Intent(this, HistoryNotificationActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return true
    }


}