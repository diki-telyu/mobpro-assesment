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
    val data: StateFlow<List<Task>> = dao.getTasks().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun taskDone(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.setTaskDone(id)
        }
    }
}