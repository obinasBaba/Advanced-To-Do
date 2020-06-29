package com.hfad.doodad.dataLayer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = -1,
    @ColumnInfo val title: String = "",
    val description: String = "",
    val completed: Boolean = false
)