package com.smartherd.pokemon.services

import android.os.Build
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import java.util.concurrent.TimeUnit

object ServiceBuilder {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val headerInterceptor = Interceptor { chain ->
        val request: Request = chain.request().newBuilder()
            .addHeader("x-device-type", Build.DEVICE)
            .addHeader("Accept-Language", Locale.getDefault().language)
            .build()
        chain.proceed(request)
    }

    private val okHttpBuilder = OkHttpClient.Builder().apply {
        callTimeout(5, TimeUnit.SECONDS)
        addInterceptor(headerInterceptor)
        addInterceptor(logger)
    }

    private val retrofitBuilder = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        addConverterFactory(GsonConverterFactory.create())
        client(okHttpBuilder.build())
    }

    private val retrofit = retrofitBuilder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}
