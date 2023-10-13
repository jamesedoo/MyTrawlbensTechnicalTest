package com.example.mytrawlbenstechnicaltest.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class DataItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("postId")
    val postId: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("body")
    val body: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)