package com.loyltworks.mandelapremium.utils.fetchData

import android.util.Log
import android.util.Xml
import com.loyltworks.mandelapremium.ApplicationClass
import kotlinx.coroutines.*
import com.loyltworks.mandelapremium.utils.fetchData.ndk.UrlClass
import org.xmlpull.v1.XmlPullParser
import java.io.StringReader
import java.net.HttpURLConnection
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import java.net.URL

object ShaKeyManager {


    private val internalScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun fetchShaKeys(
        onSuccess: (() -> Unit)? = null,
        onFailure: ((Throwable) -> Unit)? = null) {
        internalScope.launch {
            try {
                val connection = URL(UrlClass.shaKey()).openConnection() as HttpURLConnection
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                connection.requestMethod = "GET"
                connection.connect()

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val xml = connection.inputStream.bufferedReader().use { it.readText() }
                    val cleanXml = xml.replace("\uFEFF", "").trim()

                    val parser = Xml.newPullParser()
                    parser.setInput(StringReader(cleanXml))

                    var eventType = parser.eventType
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            when (parser.name) {
                                "LWS" -> {
                                    parser.next()
                                    PreferenceHelper.setStringValue(ApplicationClass.appContext,BuildConfig.LWS_KEY,parser.text?.trim().toString())

                                }
                                "Arokia" -> {
                                    parser.next()
                                    PreferenceHelper.setStringValue(ApplicationClass.appContext,BuildConfig.AROKIA_KEY,parser.text?.trim().toString())
                                }
                                "Century" -> {
                                    parser.next()
                                    PreferenceHelper.setStringValue(ApplicationClass.appContext,BuildConfig.CENTURY_KEY,parser.text?.trim().toString())
                                }
                            }
                        }
                        eventType = parser.next()
                    }

                    withContext(Dispatchers.Main) {
                        onSuccess?.invoke()
                    }

                } else {
                    val error = RuntimeException("HTTP error ${connection.responseCode}")
                    Log.e("ShaKeyManager", error.message ?: "Unknown error")
                    withContext(Dispatchers.Main) {
                        onFailure?.invoke(error)
                    }
                }
            } catch (e: Exception) {
                Log.e("ShaKeyManager", "Failed to fetch SHA keys", e)
                withContext(Dispatchers.Main) {
                    onFailure?.invoke(e)
                }
            }
        }
    }
}
