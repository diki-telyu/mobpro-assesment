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

    @Query("SELECT * FROM tasks WHERE is_completed=true")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE task_id = :id")
    suspend fun getTaskById(id: Long): Task?

    @Query("DELETE FROM tasks WHERE task_id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE tasks SET is_completed=0 WHERE task_id = :id")
    suspend fun setTaskDone(id: Long)

    @Query("SELECT * FROM tasks WHERE is_completed=0")
    fun getDoneTasks(): Flow<List<Task>>

}