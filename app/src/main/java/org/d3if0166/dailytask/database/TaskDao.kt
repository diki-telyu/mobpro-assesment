package org.d3if0166.dailytask.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import org.d3if0166.dailytask.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(tasks: Task)

    @Update
    suspend fun update(tasks: Task)

    @Query("SELECT * FROM tasks ORDER BY tanggal DESC")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): Task?

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteById(id: Long)
}