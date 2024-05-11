package org.d3if0166.dailytask.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if0166.dailytask.database.TaskDao
import org.d3if0166.dailytask.model.Task

class MainViewModel(dao: TaskDao) : ViewModel() {
    val data: StateFlow<List<Task>> = dao.getTasks().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    private fun getDataDummy(): List<Task> {
        val data = mutableListOf<Task>()



        return data
    }
}