package com.example.mytrawlbenstechnicaltest.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mytrawlbenstechnicaltest.dao.CommentDao
import com.example.mytrawlbenstechnicaltest.database.CommentDatabase
import com.example.mytrawlbenstechnicaltest.entity.CommentEntity
import com.example.mytrawlbenstechnicaltest.model.DataItem
import com.example.mytrawlbenstechnicaltest.retrofit.RetrofitInstance
import com.example.mytrawlbenstechnicaltest.source.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {
    private var mCommentDao: CommentDao?
    private var mCommentDb: CommentDatabase? = CommentDatabase.getDatabase(application)

    init {
        mCommentDao = mCommentDb?.commentDao()
    }

//    fun fetchData() {
//        viewModelScope.launch {
//            try {
//                val data = withContext(Dispatchers.IO) {
//                    mCommentDao?.getFavoriteUser()?.value
//                }
//                Log.d("APItest", data.toString())
//
////                val jsonString = data.toString()
////                val gson = Gson()
////                val dataItemListType = object : TypeToken<List<DataItem>>() {}.type
////                val dataItems: List<DataItem> = gson.fromJson(jsonString, dataItemListType)
////                dataItems.forEach { item ->
////                    println("postId: ${item.postId}, id: ${item.id}, name: ${item.name}, email: ${item.email}, body: ${item.body}")
////                }
//
//                dataLiveData.value = data
//
//            } catch (e: HttpException) {
//                // Display a user-friendly error message
////                Toast.makeText(context, "Error: Resource not found", Toast.LENGTH_SHORT).show()
//                Log.d("APItest", e.message.toString())
//            } catch (e: Throwable) {
//                // Handle other types of exceptions
//                // Display a generic error message
////                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//                Log.d("APItest", e.message.toString())
//            }
//        }
//    }

    fun fetchData(): LiveData<List<CommentEntity>>?{
        return mCommentDao?.getFavoriteUser()
    }
}