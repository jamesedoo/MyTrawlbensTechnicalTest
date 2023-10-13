package com.example.mytrawlbenstechnicaltest.retrofit

import com.example.mytrawlbenstechnicaltest.api.DataApi
import com.example.mytrawlbenstechnicaltest.model.DataItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi : DataApi{
    @GET("comments")
    override suspend fun getData(): List<DataItem>

    @GET("comments/{id}")
    override suspend fun getSpecificData(@Path("id") id: Int): DataItem
//    override suspend fun getSpecificData(@Query(value = "id", encoded = true) id: Int): List<DataItem>
}