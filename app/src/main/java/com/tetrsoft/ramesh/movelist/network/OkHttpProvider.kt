package com.tetrasoft.ramesh.movelist.network

import okhttp3.OkHttpClient

object OkHttpProvider {
    val instance: OkHttpClient = OkHttpClient.Builder().build()
}