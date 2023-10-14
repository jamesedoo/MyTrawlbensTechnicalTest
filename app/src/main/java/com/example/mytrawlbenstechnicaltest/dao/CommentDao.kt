package com.example.mytrawlbenstechnicaltest.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mytrawlbenstechnicaltest.entity.CommentEntity

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(commentEntity: CommentEntity)

    @Query("SELECT count(*) FROM comment_table WHERE comment_table.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM comment_table WHERE comment_table.id = :id")
    suspend fun removeFromFavorite(id: Int): Int

    @Query("SELECT * FROM comment_table")
    fun getFavoriteUser(): LiveData<List<CommentEntity>>

//    @Update
//    fun update(commentEntity: CommentEntity)
//
//    @Delete
//    fun delete(commentEntity: CommentEntity)
//
//    @Query("delete from comment_table")
//    fun deleteAllNotes()
//
//    @Query("select * from comment_table order by id desc")
//    fun getAllNotes(): LiveData<List<CommentEntity>>
}