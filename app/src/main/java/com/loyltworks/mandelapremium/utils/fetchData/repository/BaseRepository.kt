package com.loyltworks.mandelapremium.utils.fetchData.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.oneloyalty.goodpack.utils.internet.Internet
import retrofit2.Response
import java.net.SocketException

open class BaseRepository {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, error: String): T? {
        var output: T? = null
        try {
            val result = apiOutput(call, error)
            when (result) {
                is Output.Success ->
                    output = result.output
                is Output.Error -> Log.e("Error", "The $error and the ${result.exception}")
            }
            return output
        } catch (e: SocketException) {
            return output
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private suspend fun <T : Any> apiOutput(
        call: suspend () -> Response<T>,
        error: String
    ): Output<T> {
        return if (Internet.isNetworkConnected()) {

            val response = call.invoke()

            return if (response.isSuccessful) {

                if (response.body() != null)
                    Output.Success(response.body()!!)
                else
                    Output.Error(Exception(response.errorBody().toString()))

            } else Output.Error(Exception("OOps .. Something went wrong due to  ${response.code()} : ${response.raw()}"))

        } else Output.Error(Exception("OOps .. Something went wrong"))
    }
}