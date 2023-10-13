package com.example.mytrawlbenstechnicaltest.retrofit

import com.example.mytrawlbenstechnicaltest.api.DataApi
import com.example.mytrawlbenstechnicaltest.model.DataItem
import retrofit2.http.GET

interface RetrofitApi : DataApi{
    @GET("comments")
    override suspend fun getData(): List<DataItem>
}