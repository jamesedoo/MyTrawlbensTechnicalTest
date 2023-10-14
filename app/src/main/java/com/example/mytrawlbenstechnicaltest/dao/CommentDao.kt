package com.example.mytrawlbenstechnicaltest.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mytrawlbenstechnicaltest.entity.CommentEntity

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(commentEntity: CommentEntity)

    @Query("SELECT count(*) FROM comment_table WHERE comment_table.id = :id")
    suspend fun checkFavourite(id: Int): Int

    @Query("DELETE FROM comment_table WHERE comment_table.id = :id")
    suspend fun removeFromFavorite(id: Int): Int

    @Query("SELECT * FROM comment_table")
    fun getFavoriteUser(): LiveData<List<CommentEntity>>
}