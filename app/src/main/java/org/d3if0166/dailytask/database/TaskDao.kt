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

    @Query("SELECT * FROM tasks WHERE status=1 ORDER BY tanggal DESC")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): Task?

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE tasks SET status=0 WHERE id = :id")
    suspend fun setTaskDone(id: Long)

    @Query("SELECT * FROM tasks WHERE status=0 ORDER BY tanggal DESC")
    fun getDoneTasks(): Flow<List<Task>>

}