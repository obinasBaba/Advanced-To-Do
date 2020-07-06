package com.hfad.doodad.dataLayer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = -1,
    @ColumnInfo var title: String = "",
    var description: String = "",
    val completed: Boolean = false
){

    fun isActive() = !completed
    fun isFinish() = completed
}