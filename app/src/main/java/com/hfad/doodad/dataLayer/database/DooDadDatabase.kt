package com.hfad.doodad.dataLayer.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class DooDadDatabase : RoomDatabase() {

    abstract fun getTaskDao() : TaskDao

}