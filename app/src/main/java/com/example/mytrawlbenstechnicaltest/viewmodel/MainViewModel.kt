package com.example.mytrawlbenstechnicaltest.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mytrawlbenstechnicaltest.entity.CommentEntity
import com.example.mytrawlbenstechnicaltest.model.DataItem
import com.example.mytrawlbenstechnicaltest.repository.Repository
import com.example.mytrawlbenstechnicaltest.retrofit.RetrofitInstance
import com.example.mytrawlbenstechnicaltest.source.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MainViewModel(application: Application) : AndroidViewModel(application) {
//    private val context = getApplication<Application>().applicationContext
    private val dataRepository = DataRepository(RetrofitInstance.getService(), application)
    val dataLiveData = MutableLiveData<List<DataItem>>()
//    val searchList = MutableLiveData<List<DataItem>>()

    fun fetchData() {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    dataRepository.getData()
                }
                Log.d("APItest", data.toString())

//                val jsonString = data.toString()
//                val gson = Gson()
//                val dataItemListType = object : TypeToken<List<DataItem>>() {}.type
//                val dataItems: List<DataItem> = gson.fromJson(jsonString, dataItemListType)
//                dataItems.forEach { item ->
//                    println("postId: ${item.postId}, id: ${item.id}, name: ${item.name}, email: ${item.email}, body: ${item.body}")
//                }

                dataLiveData.value = data

            } catch (e: HttpException) {
                // Display a user-friendly error message
//                Toast.makeText(context, "Error: Resource not found", Toast.LENGTH_SHORT).show()
                Log.d("APItest", e.message.toString())
            } catch (e: Throwable) {
                // Handle other types of exceptions
                // Display a generic error message
//                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.d("APItest", e.message.toString())
            }
        }
    }

//    private val repository = Repository(application)
//    private val allNotes = dataRepository.getAllComments()

//    fun insert(comment: CommentEntity) {
//        repository.insert(comment)
//    }
//
//    fun update(comment: CommentEntity) {
//        repository.update(comment)
//    }
//
//    fun delete(comment: CommentEntity) {
//        repository.delete(comment)
//    }
//
//    fun deleteAllNotes() {
//        repository.deleteAllComments()
//    }
//
//    fun getAllNotes(): List<CommentEntity> {
//        return allNotes
//    }

//    fun addDataToSearchList(dataItem: DataItem) {
//        if (dataItem.email?.toLowerCase(Locale.getDefault())?.contains(searchText) == true) {
//            searchList.value = listOf(dataItem)
//        }
//        searchList.value = dataLiveData.value
//    }
//    fun addAllDataToSearchList() {
//        searchList.value = dataLiveData.value
//    }
//    fun clearSearchList() {
//        searchList.value = listOf<DataItem>()
//    }
}