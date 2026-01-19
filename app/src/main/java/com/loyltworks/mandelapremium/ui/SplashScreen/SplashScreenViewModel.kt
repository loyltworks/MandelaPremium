package com.loyltworks.mandelapremium.ui.SplashScreen

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loyltworks.mandelapremium.ui.forceUpdateAndMaintenance.ForceUpdateActivity
import com.loyltworks.mandelapremium.ui.forceUpdateAndMaintenance.MaintenanceActivity
import com.loyltworks.mandelapremium.utils.fetchData.SecureUpdateChecker
import com.loyltworks.mandelapremium.utils.fetchData.ndk.UrlClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class SplashScreenViewModel : ViewModel() {

    private  var  IS_MAINTENANCE = 1

    private val _isUpdateAvailable = MutableLiveData<Boolean>()

    fun get_isUpdateAvailable(): LiveData<Boolean> {
        return _isUpdateAvailable
    }

    fun checkIfUpdateAvailabe(context: Context, currentVersionCode: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            SecureUpdateChecker.checkForUpdate(context, UrlClass.updateUrl()) { success, resp ->
                if (success && resp != null) {
                    try {
                        val json = JSONObject(resp)
                        if (json.optBoolean("Status")) {
                            val res = json.getJSONObject("Result")
                            val serverVer = res.optInt("version_number", currentVersionCode)
                            val maintenance = res.optInt("is_maintenance", 0)
                            when {
                                serverVer > currentVersionCode -> {
                                    context.startActivity(Intent(context, ForceUpdateActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TOP })
                                    (context as Activity).finish()
                                }
                                maintenance == IS_MAINTENANCE -> {
                                    context.startActivity(Intent(context, MaintenanceActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TOP })
                                    (context as Activity).finish()
                                }
                                else -> _isUpdateAvailable.postValue(false)
                            }
                        } else {
                            _isUpdateAvailable.postValue(false)
                        }
                    } catch (e: Exception) {
                        _isUpdateAvailable.postValue(false)
                    }
                } else {
                    _isUpdateAvailable.postValue(false)
                }
            }
        }
    }
}