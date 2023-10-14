package com.example.mytrawlbenstechnicaltest.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mytrawlbenstechnicaltest.dao.CommentDao
import com.example.mytrawlbenstechnicaltest.database.CommentDatabase
import com.example.mytrawlbenstechnicaltest.entity.CommentEntity
import com.example.mytrawlbenstechnicaltest.model.CommentItem
import com.example.mytrawlbenstechnicaltest.retrofit.RetrofitInstance
import com.example.mytrawlbenstechnicaltest.source.CommentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class CommentDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val commentRepository = CommentRepository(RetrofitInstance.getService(), application)
    private val _dataLiveData = MutableLiveData<CommentItem>()
    val dataLiveData: LiveData<CommentItem> = _dataLiveData

    private var mCommentDao: CommentDao?
    private var mCommentDb: CommentDatabase? = CommentDatabase.getDatabase(application)

    init {
        mCommentDao = mCommentDb?.commentDao()
    }

    fun getSpecificComment(id: Int) {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    commentRepository.getSpecificComment(id)
                }
                Log.d("APItestdetail", data.toString())

                _dataLiveData.value = data

            } catch (e: HttpException) {
                // Display a user-friendly error message
                Toast.makeText(context, "Error: Resource not found", Toast.LENGTH_SHORT).show()
                Log.d("APItest", e.message.toString())
            } catch (e: Throwable) {
                // Handle other types of exceptions
                // Display a generic error message
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.d("APItest", e.message.toString())
            }
        }
    }

    fun addFavourite(name: String?, postId: Int?, id: Int?, body: String?, email: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            var comment = CommentEntity(
                name,
                postId,
                id,
                body,
                email
            )
            mCommentDao?.addFavourite(comment)
        }
    }

    suspend fun checkFavourite(id: Int) = mCommentDao?.checkFavourite(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            mCommentDao?.removeFromFavorite(id)
        }
    }
}