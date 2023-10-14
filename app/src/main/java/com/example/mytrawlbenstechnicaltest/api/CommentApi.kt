package com.example.mytrawlbenstechnicaltest.api

import com.example.mytrawlbenstechnicaltest.model.CommentItem

interface CommentApi {
    suspend fun getComments(): List<CommentItem>
    suspend fun getSpecificComment(id: Int): CommentItem
}