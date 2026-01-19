package com.loyltworks.mandelapremium.utils.fetchData.repository

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.loyltworks.mandelapremium.ApplicationClass
import retrofit2.Response
import java.net.UnknownHostException
import javax.net.ssl.SSLPeerUnverifiedException

open class BaseRepository {

    suspend fun <T> safeApiCall(call: suspend () -> Response<T>): T? {
        return try {
            val response = call()
            return response.body()
        } catch (e: SSLPeerUnverifiedException) {
            showToast("❌ Secure connection failed. Please restart you app.")
            null
        } catch (e: UnknownHostException) {
            showToast("No internet connection.")
            null
        } catch (e: Exception) {
            // Fallback message — avoid exposing technical info to users
            showToast("Something went wrong. Please try again later.")
            null
        }
    }

    private fun showToast(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(ApplicationClass.appContext, message, Toast.LENGTH_LONG).show()
        }
    }
}