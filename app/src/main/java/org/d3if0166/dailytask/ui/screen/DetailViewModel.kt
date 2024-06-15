package org.d3if0166.dailytask.ui.screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3if0166.dailytask.database.TaskDao
import org.d3if0166.dailytask.model.Task
import org.d3if0166.dailytask.network.ApiStatus
import org.d3if0166.dailytask.network.TaskApi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel() : ViewModel() {


//    fun update(task_id: Long, name: String, detail: String, due_date: String, user_id: String) {
//        val task = Task(
//            task_id = task_id,
//            name = name,
//            detail = detail,
//            due_date = due_date,
//            is_completed = true,
//            user_id = user_id
//        )
//
//        viewModelScope.launch(Dispatchers.IO) {
//            dao.update(task)
//        }
//    }
//
//    fun delete(id: Long) {
//        viewModelScope.launch(Dispatchers.IO) {
//            dao.deleteById(id)
//        }
//    }
//
//    suspend fun getTask(task_id: Long): Task? {
//        return dao.getTaskById(task_id)
//    }
}