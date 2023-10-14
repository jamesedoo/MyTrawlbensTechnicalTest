package com.example.mytrawlbenstechnicaltest.source

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mytrawlbenstechnicaltest.api.DataApi
import com.example.mytrawlbenstechnicaltest.dao.CommentDao
import com.example.mytrawlbenstechnicaltest.database.CommentDatabase
import com.example.mytrawlbenstechnicaltest.entity.CommentEntity
import com.example.mytrawlbenstechnicaltest.model.DataItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataRepository(private val dataApi: DataApi, application: Application) {
    suspend fun getData(): List<DataItem> {
        return dataApi.getData()
    }

    suspend fun getSpecificData(id: Int): DataItem {
        return dataApi.getSpecificData(id)
    }

//    private var commentDao: CommentDao
//    private var allComments: LiveData<List<CommentEntity>>
//
//    private val database = CommentDatabase.getInstance(application)
//
//    init {
//        commentDao = database.noteDao()
//        allComments = commentDao.getAllNotes()
//    }
////
//    fun insert(comment: CommentEntity) {
////        Single.just(noteDao.insert(note))
////            .subscribeOn(Schedulers.io())
////            .observeOn(AndroidSchedulers.mainThread())
////            .subscribe()
////        subscribeOnBackground {
////            commentDao.insert(comment)
////        }
//        CoroutineScope(Dispatchers.IO).launch {
//            commentDao.insert(comment)
//        }
//    }
//
//    fun update(comment: CommentEntity) {
////        subscribeOnBackground {
////            commentDao.update(comment)
////        }
//        commentDao.update(comment)
//    }
//
//    fun delete(comment: CommentEntity) {
////        subscribeOnBackground {
////            commentDao.delete(comment)
////        }
//        commentDao.delete(comment)
//    }
//
//    fun deleteAllComments() {
////        subscribeOnBackground {
////            commentDao.deleteAllNotes()
////        }
//        commentDao.deleteAllNotes()
//    }
//
//    fun getAllComments(): LiveData<List<CommentEntity>> {
//        return allComments
//    }
}