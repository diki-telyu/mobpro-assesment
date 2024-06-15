package org.d3if0166.dailytask.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val task_id: Long = 0L,
    val name: String,
    @ColumnInfo(defaultValue = "null")
    val detail: String? = null,
    @ColumnInfo(defaultValue = "null")
    val due_date: String? = null,
    @ColumnInfo(defaultValue = true.toString())
    val is_completed: Boolean,
    val user_id: String
)
