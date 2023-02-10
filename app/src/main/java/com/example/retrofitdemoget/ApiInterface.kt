package com.example.retrofitdemoget

import retrofit2.http.GET
import retrofit2.Call

interface ApiInterface {
    @GET("posts")
    fun getData(): Call<List<MyDataItem>>
}