package com.loyltworks.mandelapremium.utils

import android.content.Context
import com.loyltworks.mandelapremium.model.DashboardResponse
import com.loyltworks.mandelapremium.model.LoginResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

object PreferenceHelper {



    private const val PreferenceName = "LoyaltyPreferenceHelper_GP2020"
    private const val LoginDetails = "LoyaltyPreferenceHelper_GP2020_loginDetails"
    private const val DashboardDetails = "LoyaltyPreferenceHelper_GP2020_uusAppOpenTodaysuu"


    fun clear(context: Context){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

    fun setBooleanValue(context: Context, key: String, value: Boolean){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBooleanValue(context: Context, key: String): Boolean{
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }

    fun setStringValue(context: Context, key: String, value: String){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
            sharedPreferences.edit().putString(key, value).apply()
    }

    fun getStringValue(context: Context, key: String): String{
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "")!!
    }

    fun setLongValue(context: Context, key: String, value: Long){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
            sharedPreferences.edit().putLong(key, value).apply()
    }

    fun getLongValue(context: Context, key: String): Long{
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        return sharedPreferences.getLong(key, 0L)
    }

    fun setDashboardDetails(context: Context, dashboardResponse: DashboardResponse){
        val jsonAdapter: JsonAdapter<DashboardResponse> = jsonDashboardAdapter()
        val json = jsonAdapter.toJson(dashboardResponse)

        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(DashboardDetails, json).apply()
    }

    fun getDashboardDetails(context: Context) : DashboardResponse? {
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        val stringValue = sharedPreferences.getString(DashboardDetails, "")

        val jsonAdapter: JsonAdapter<DashboardResponse> = jsonDashboardAdapter()
        return if(!stringValue.isNullOrEmpty()) {
            jsonAdapter.fromJson(stringValue)
        }else{
            null
        }
    }

    fun setLoginDetails(context: Context, loginResponse: LoginResponse){
        val jsonAdapter: JsonAdapter<LoginResponse> = jsonAdapter()
        val json = jsonAdapter.toJson(loginResponse)

        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(LoginDetails, json).apply()
    }

    fun getLoginDetails(context: Context) : LoginResponse? {
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        val stringValue = sharedPreferences.getString(LoginDetails, "")

        val jsonAdapter: JsonAdapter<LoginResponse> = jsonAdapter()
        return if(!stringValue.isNullOrEmpty()) {
            jsonAdapter.fromJson(stringValue)
        }else{
            null
        }
    }


    private fun jsonAdapter(): JsonAdapter<LoginResponse> {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(LoginResponse::class.java)
    }

    private fun jsonDashboardAdapter(): JsonAdapter<DashboardResponse> {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(DashboardResponse::class.java)
    }

}
