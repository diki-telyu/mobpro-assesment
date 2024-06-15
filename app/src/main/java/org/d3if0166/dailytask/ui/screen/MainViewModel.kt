package org.d3if0166.dailytask.ui.screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3if0166.dailytask.database.TaskDao
import org.d3if0166.dailytask.model.Task
import org.d3if0166.dailytask.network.ApiStatus
import org.d3if0166.dailytask.network.TaskApi
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel : ViewModel() {
    var data = mutableStateOf(emptyList<Task>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    fun retrieveData(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            val userId = "eq.$email"
            try {
                data.value = TaskApi.service.getTasks(userId)
                status.value = ApiStatus.SUCCESS
            }
            catch (e: Exception) {
                Log.d("MainViewModel", "Failed: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun insert(name: String, detail: String, due_date: String, user_id: String) {
        val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imthc2NiZWxyZmNjaG50YXpscnR5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTgwMjY4NjMsImV4cCI6MjAzMzYwMjg2M30.eJvywCHCZTOkYXmjASxORAozv2G6sVpB1KsvhlHEuKA"

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = TaskApi.service.addTaskByUserId(
                    apiKey,
                    name.toRequestBody("text/plain".toMediaTypeOrNull()),
                            detail.toRequestBody("text/plain".toMediaTypeOrNull()),
                            due_date.toRequestBody("text/plain".toMediaTypeOrNull()),
                            user_id.toRequestBody("text/plain".toMediaTypeOrNull())
                )

                if(result.status == "success")
                    retrieveData(user_id)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
            }
        }
    }

    fun taskDone(task_id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
//            dao.setTaskDone(id)
        }
    }

    fun clearMessage() { errorMessage.value=null }
}