package org.d3if0166.dailytask.model

import androidx.room.Entity

@Entity(tableName = "tasks")
data class Task(
    val id: Long,
    val judul: String,
    val detail: String,
    val tanggal: String
)
