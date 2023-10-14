package com.example.mytrawlbenstechnicaltest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mytrawlbenstechnicaltest.dao.CommentDao
import com.example.mytrawlbenstechnicaltest.database.CommentDatabase
import com.example.mytrawlbenstechnicaltest.entity.CommentEntity

class CommentFavouriteViewModel(application: Application) : AndroidViewModel(application) {
    private var mCommentDao: CommentDao?
    private var mCommentDb: CommentDatabase? = CommentDatabase.getDatabase(application)

    init {
        mCommentDao = mCommentDb?.commentDao()
    }

    fun fetchData(): LiveData<List<CommentEntity>>?{
        return mCommentDao?.getFavoriteUser()
    }
}