package com.example.mytrawlbenstechnicaltest.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mytrawlbenstechnicaltest.model.DataItem
import com.example.mytrawlbenstechnicaltest.retrofit.RetrofitInstance
import com.example.mytrawlbenstechnicaltest.source.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class DataDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val dataRepository = DataRepository(RetrofitInstance.getService())
    val dataLiveData = MutableLiveData<DataItem>()

    fun fetchData(id: Int) {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    dataRepository.getSpecificData(id)
                }
                Log.d("APItestdetail", data.toString())

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
}