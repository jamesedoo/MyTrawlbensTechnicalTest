package com.example.mytrawlbenstechnicaltest.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mytrawlbenstechnicaltest.model.CommentItem
import com.example.mytrawlbenstechnicaltest.retrofit.RetrofitInstance
import com.example.mytrawlbenstechnicaltest.source.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val commentRepository = CommentRepository(RetrofitInstance.getService(), application)
    private val _dataLiveData = MutableLiveData<List<CommentItem>>()
    val dataLiveData: LiveData<List<CommentItem>> = _dataLiveData

    fun getComments() {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    commentRepository.getComments()
                }
                Log.d("APItest", data.toString())

                _dataLiveData.value = data

            } catch (e: HttpException) {
                Toast.makeText(context, "Error: Resource not found", Toast.LENGTH_SHORT).show()
                Log.d("APItest", e.message.toString())
            } catch (e: Throwable) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.d("APItest", e.message.toString())
            }
        }
    }
}