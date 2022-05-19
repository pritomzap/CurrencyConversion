package com.currencyconversion.app.service.network

import android.os.Build
import android.util.Log
import com.currencyconversion.app.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.net.ssl.*


class ApiClient @Inject constructor(){
    companion object{
        private const val TIMEOUT_MILLS = 180 * 1000
        private const val TAG = "ApiFactory"
    }
    private var retrofit: Retrofit? = null

    fun getRetrofit(baseUrl: String): Retrofit? {
        if (retrofit == null) {
            val client = getNewHttpClient(true)
            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(interceptor)
            }
            client.addNetworkInterceptor { chain ->
                    val request = chain.request().newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Content-Type","application/json")
                            .addHeader("apikey",BuildConfig.API_KEY)
                    chain.proceed(request.build())
            }.retryOnConnectionFailure(true)
            val gson = GsonBuilder()
                    .setLenient()
                    .create()
            val builder = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getUnsafeOkHttpClient(client)!!.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))

            retrofit = builder.build()
        }
        return retrofit
    }


    private fun getUnsafeOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient.Builder? {
        return try {
            val trustAllCerts = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                        override fun getAcceptedIssuers(): Array<X509Certificate> { return arrayOf() }
                    }
                )

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun getNewHttpClient(isAuthRequired: Boolean): OkHttpClient.Builder {
        return enableTls12OnPreLollipop(OkHttpClient.Builder(), isAuthRequired)
                .connectTimeout(TIMEOUT_MILLS.toLong(), TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT_MILLS.toLong(), TimeUnit.MILLISECONDS)
                .writeTimeout(TIMEOUT_MILLS.toLong(), TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(false)
    }

    private fun enableTls12OnPreLollipop(client: OkHttpClient.Builder, isAuthRequired: Boolean): OkHttpClient.Builder {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                    override fun getAcceptedIssuers(): Array<X509Certificate?> { return arrayOfNulls(0) }
                }
            )
        val hostnameVerifier = HostnameVerifier { _: String, _: SSLSession? -> true }
        if (Build.VERSION.SDK_INT in 16..21) {
            try {
                val sc = SSLContext.getInstance("SSL")
                sc.init(null, trustAllCerts, SecureRandom())
                client.sslSocketFactory(Tls12SocketFactory(sc.socketFactory), trustAllCerts[0] as X509TrustManager)
                val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build()
                val specs: MutableList<ConnectionSpec> = ArrayList()
                specs.add(cs)
                specs.add(ConnectionSpec.COMPATIBLE_TLS)
                specs.add(ConnectionSpec.CLEARTEXT)
                client.connectionSpecs(specs)
            } catch (exc: Exception) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc)
            }
        }
        client.hostnameVerifier(hostnameVerifier)
        return client
    }
}