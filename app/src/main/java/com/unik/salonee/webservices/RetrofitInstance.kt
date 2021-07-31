package com.unik.modelapp.webservices

import com.google.gson.GsonBuilder
import com.unik.modelapp.utilities.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/*
import com.google.gson.GsonBuilder
import com.unik.modelapp.utilities.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit*/

class RetrofitInstance {
    companion object {

        private var retrofit: Retrofit? = null
        private var client: OkHttpClient? = null

        // var BASE_URL: String = "http://models.alloydarius.com"

        fun create(): SaloneeAPIService {

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    .client(getOkHttpClient())
                    .build()
            }
            return retrofit!!.create(SaloneeAPIService::class.java)
        }

        fun getOkHttpClient(): OkHttpClient {

            if (client == null) {

                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)

                val headers = HttpLoggingInterceptor()
                headers.setLevel(HttpLoggingInterceptor.Level.HEADERS)


                client = OkHttpClient.Builder()
                    .readTimeout(40, TimeUnit.SECONDS)
                    .connectTimeout(40, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .addInterceptor(headers)
                    .build()
            }
            return client!!
        }

    }
}