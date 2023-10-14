package com.example.mytrawlbenstechnicaltest.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comment_table")
data class CommentEntity(
    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "postId")
    val postId: Int? = null,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "body")
    val body: String? = null,

    @ColumnInfo(name = "email")
    val email: String? = null
)