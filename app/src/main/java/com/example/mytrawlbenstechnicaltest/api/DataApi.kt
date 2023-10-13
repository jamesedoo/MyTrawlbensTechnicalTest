package com.example.mytrawlbenstechnicaltest.api

import com.example.mytrawlbenstechnicaltest.model.DataItem

interface DataApi {
    suspend fun getData(): List<DataItem>
}