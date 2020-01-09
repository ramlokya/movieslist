package com.tetrasoft.ramesh.movelist.network

import com.tetrasoft.ramesh.movelist.ado.PopularDto
import com.tetrasoft.ramesh.movelist.network.ApiBaseConfig.Companion.API_KEY
import com.tetrasoft.ramesh.movelist.network.ApiBaseConfig.Companion.POPULAR
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET(POPULAR)
    fun getPopularData(@Query(API_KEY) apiKey : String) : Single<Response<PopularDto>>
}