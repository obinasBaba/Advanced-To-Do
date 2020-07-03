package com.hfad.doodad

import android.app.Application
import com.hfad.doodad.dataLayer.LocalDataSource
import com.hfad.doodad.dataLayer.TaskRepository
import com.hfad.doodad.dataLayer.database.DooDadDatabase
import com.hfad.doodad.dataLayer.database.TaskDao

object ServiceLocator
{
    private var taskRepository : TaskRepository? = null

    private fun getDataBase(ctx : Application ) : TaskDao{
      return DooDadDatabase.getInstance(ctx).getTaskDao()
    }

    fun provideTasksRepository( ctx: Application ) : TaskRepository {
       return synchronized(this   ){
            taskRepository  ?: LocalDataSource(getDataBase( ctx )).apply {
                taskRepository = this
            }
        }
    }
}