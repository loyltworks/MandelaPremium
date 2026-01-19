package com.loyltworks.mandelapremium.utils.fetchData.apiCall

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.loyltworks.mandelapremium.ApplicationClass
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import com.loyltworks.mandelapremium.utils.fetchData.ndk.UrlClass
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLPeerUnverifiedException


object ApiService {

    var sslPinValid: Boolean = true
        private set

    private var retrofit: Retrofit? = null

    class NullOnEmptyConverterFactory : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
        ): Converter<ResponseBody, *> {
            val delegate = retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
            return Converter { body ->
                try {
                    if (body.contentLength() == 0L) null else delegate.convert(body)
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    val apiInterface: ApiInterface? by lazy {
        try {
            getRetrofit()?.create(ApiInterface::class.java)
        } catch (e: Exception) {
            null
        }
    }

    private fun getRetrofit(): Retrofit? {

        val sha = if(UrlClass.checkLiveDemo()=="DEMO"){
            PreferenceHelper.getStringValue(ApplicationClass.appContext,BuildConfig.LWS_KEY)
        } else{
            PreferenceHelper.getStringValue(ApplicationClass.appContext,BuildConfig.CENTURY_KEY)
        }


        val hostname = UrlClass.domain()

        if (sha.isNullOrBlank()) {
            sslPinValid = false
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(ApplicationClass.appContext, "❌ SSL Pinning SHA is missing.", Toast.LENGTH_LONG).show()
            }
            return null
        }

        if (retrofit != null) return retrofit!!

        val builder = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(logging)
        }

        builder.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Basic Q2VudHVyeUxpdmU6I0NlbjE0NUZzV2ts")
                .build()
            try {
                chain.proceed(request)
            } catch (e: SSLPeerUnverifiedException) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(ApplicationClass.appContext, "❌ SSL Pinning failed!", Toast.LENGTH_LONG).show()
                }
                throw e
            }
        }

        val certPinner = CertificatePinner.Builder()
            .add(hostname, sha)
            .build()
        builder.certificatePinner(certPinner)

        retrofit = Retrofit.Builder()
            .baseUrl(UrlClass.baseUrl())
            .client(builder.build())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return retrofit
    }
}