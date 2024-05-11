package org.d3if0166.dailytask.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.d3if0166.dailytask.database.TaskDao
import org.d3if0166.dailytask.model.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel(private val dao: TaskDao) : ViewModel() {
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    val data: StateFlow<List<Task>> = dao.getTasks().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    private fun getDataDummy(): List<Task> {
        val data = mutableListOf<Task>()

        return data
    }

    fun taskDone(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.setTaskDone(id)
        }
    }
}