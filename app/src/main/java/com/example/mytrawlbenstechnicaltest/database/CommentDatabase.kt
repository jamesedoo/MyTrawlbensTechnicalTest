package com.example.mytrawlbenstechnicaltest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mytrawlbenstechnicaltest.dao.CommentDao
import com.example.mytrawlbenstechnicaltest.entity.CommentEntity

@Database(entities = [CommentEntity::class], version = 1)
abstract class CommentDatabase : RoomDatabase() {

    abstract fun commentDao(): CommentDao

    companion object {
        @Volatile
        private var INSTANCE: CommentDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): CommentDatabase {
            if (INSTANCE == null) {
                synchronized(CommentDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        CommentDatabase::class.java, "comment_database")
                        .build()
                }
            }
            return INSTANCE as CommentDatabase
        }
    }
}