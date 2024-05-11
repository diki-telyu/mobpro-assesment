package org.d3if0166.dailytask.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if0166.dailytask.database.TaskDao
import org.d3if0166.dailytask.ui.screen.DetailViewModel
import org.d3if0166.dailytask.ui.screen.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val dao: TaskDao,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        }

        throw IllegalArgumentException("Unknow ViewModel class")
    }
}