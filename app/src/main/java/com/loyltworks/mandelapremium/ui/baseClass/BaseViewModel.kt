package com.loyltworks.mandelapremium.ui.baseClass

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.loyltworks.mandelapremium.ApplicationClass
import com.loyltworks.mandelapremium.utils.fetchData.apiCall.ApiService
import com.loyltworks.mandelapremium.utils.fetchData.repository.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    //create a new Job
    private val parentJob = Job()
    //create a coroutine context with the job and the dispatcher
    private val coroutineContext : CoroutineContext get() = parentJob + Dispatchers.Default
    //create a coroutine scope with the coroutine context
    val scope = CoroutineScope(coroutineContext)

    // Safe repo init – null if SSL pin failed
    val apiRepository: ApiRepository? = ApiService.apiInterface?.let {
        ApiRepository(it)
    }

    init {
        if (apiRepository == null) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(ApplicationClass.appContext, "❌ Secure connection not ready. Restart app or check certificate.", Toast.LENGTH_LONG).show()
            }
        }
    }
    // cancel all the request
    fun cancelRequests() = coroutineContext.cancel()

}