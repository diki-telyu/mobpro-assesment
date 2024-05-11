package org.d3if0166.dailytask.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val judul: String,
    val detail: String,
    val tanggal: String,
    @ColumnInfo(defaultValue = true.toString())
    val status: Boolean,
//    @ColumnInfo(defaultValue = false.toString())
//    val checked: Boolean = false
)
