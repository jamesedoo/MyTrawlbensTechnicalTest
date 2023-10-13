package com.example.mytrawlbenstechnicaltest.source

import com.example.mytrawlbenstechnicaltest.api.DataApi
import com.example.mytrawlbenstechnicaltest.model.DataItem

class DataRepository(private val dataApi: DataApi) {
    suspend fun getData(): List<DataItem> {
        return dataApi.getData()
    }
}