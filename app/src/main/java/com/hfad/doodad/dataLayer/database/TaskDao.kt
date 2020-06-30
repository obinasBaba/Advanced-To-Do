package com.hfad.doodad.dataLayer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao

@Dao
interface TaskDao
{
    fun observeAll() : LiveData< List<Task>>
}