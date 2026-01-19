package com.loyltworks.mandelapremium.utils.fetchData

import android.content.Context
import android.util.Base64
import android.widget.Toast
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.security.KeyStore
import java.security.MessageDigest
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLPeerUnverifiedException
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory
import com.loyltworks.mandelapremium.BuildConfig
import javax.net.ssl.X509TrustManager

object SecureUpdateChecker {
    fun checkForUpdate(context: Context, targetUrl: String, callback: (Boolean, String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val sha256 = PreferenceHelper.getStringValue(context,BuildConfig.AROKIA_KEY)

            if (sha256.isNullOrBlank() || !sha256.startsWith("sha256/")) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "❌ Missing or invalid SSL SHA key", Toast.LENGTH_LONG).show()
                }
                callback(false, null)
                return@launch
            }

            try {
                val response = performPinnedRequest(targetUrl, sha256)
                callback(true, response)
            } catch (e: SSLPeerUnverifiedException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "❌ SSL Pinning failed — certificate mismatch.", Toast.LENGTH_LONG).show()
                }
                callback(false, null)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "No internet connection !!", Toast.LENGTH_LONG).show()
                }
                callback(false, null)
            }
        }
    }

    private fun performPinnedRequest(urlString: String, expectedSha256: String): String {
        val url = URL(urlString)
        val connection = (url.openConnection() as HttpsURLConnection).apply {
            connectTimeout = 15000
            readTimeout = 15000
            sslSocketFactory = buildPinnedSSLSocketFactory(expectedSha256)
        }

        connection.connect()
        val reader = BufferedReader(InputStreamReader(connection.inputStream))
        val response = reader.readText()
        reader.close()

        return response
    }

    private fun buildPinnedSSLSocketFactory(expectedSha256: String): SSLSocketFactory {
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(null as? KeyStore)

        val defaultTrustManager = trustManagerFactory.trustManagers
            .first { it is X509TrustManager } as X509TrustManager

        val pinnedTrustManager = object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> = defaultTrustManager.acceptedIssuers

            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                defaultTrustManager.checkClientTrusted(chain, authType)
            }

            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                val serverSha = getSha256FromCert(chain[0])

                if (!serverSha.trim().equals(expectedSha256.trim(), ignoreCase = false)) {
                    throw SSLPeerUnverifiedException("❌ Certificate pinning failure.")
                }
            }
        }

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf(pinnedTrustManager), null)
        return sslContext.socketFactory
    }

    private fun getSha256FromCert(cert: X509Certificate): String {
        val publicKey = cert.publicKey.encoded
        val digest = MessageDigest.getInstance("SHA-256").digest(publicKey)
        val base64 = Base64.encodeToString(digest, Base64.NO_WRAP)
        return "sha256/$base64"
    }
}
