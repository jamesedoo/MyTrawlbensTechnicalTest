package com.example.mytrawlbenstechnicaltest.retrofit

import com.example.mytrawlbenstechnicaltest.api.CommentApi
import com.example.mytrawlbenstechnicaltest.model.CommentItem
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApi : CommentApi{
    @GET("comments")
    override suspend fun getComments(): List<CommentItem>

    @GET("comments/{id}")
    override suspend fun getSpecificComment(@Path("id") id: Int): CommentItem
}