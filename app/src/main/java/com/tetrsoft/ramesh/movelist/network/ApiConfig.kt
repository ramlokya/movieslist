package com.tetrasoft.ramesh.movelist.network

import com.tetrasoft.ramesh.movelist.network.ApiBaseConfig.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {

        fun getApiInstance(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}