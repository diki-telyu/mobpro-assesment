package org.d3if0166.dailytask.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0166.dailytask.database.TaskDao
import org.d3if0166.dailytask.model.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: TaskDao) : ViewModel() {
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(judul: String, isi: String) {
        val task = Task(
            judul = judul,
            detail = isi,
            tanggal = formatter.format(Date()),
            status = true
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(task)
        }
    }

    fun update(id: Long, judul: String, isi: String) {
        val task = Task(
            id = id,
            judul = judul,
            detail = isi,
            tanggal = formatter.format(Date()),
            status = true
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(task)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

    suspend fun getTask(id: Long): Task? {
        return dao.getTaskById(id)
    }
}