package com.example.mytrawlbenstechnicaltest.source

import android.app.Application
import com.example.mytrawlbenstechnicaltest.api.CommentApi
import com.example.mytrawlbenstechnicaltest.model.CommentItem

class CommentRepository(private val commentApi: CommentApi, application: Application) {
    suspend fun getComments(): List<CommentItem> {
        return commentApi.getComments()
    }

    suspend fun getSpecificComment(id: Int): CommentItem {
        return commentApi.getSpecificComment(id)
    }
}