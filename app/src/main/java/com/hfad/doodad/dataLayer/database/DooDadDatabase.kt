package com.hfad.doodad.dataLayer.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class DooDadDatabase : RoomDatabase() {

    private val lock : Any = Object()

    abstract fun getTaskDao() : TaskDao

    companion object{
         var dooDadDatabase:  DooDadDatabase? = null

        fun getInstance(ctx: Application): DooDadDatabase {
            return dooDadDatabase?.let {
                dooDadDatabase
            } ?: run {
                synchronized(this) {
                    Room.databaseBuilder<DooDadDatabase>(
                        ctx.applicationContext,
                        DooDadDatabase::class.java, "Tasks.db")
                        .build().apply {
                            dooDadDatabase = this
                        }
                }
            }
        }

    }

}