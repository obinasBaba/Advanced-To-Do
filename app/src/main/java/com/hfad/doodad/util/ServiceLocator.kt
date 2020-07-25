package com.hfad.doodad.util

import android.app.Application
import com.hfad.doodad.dataLayer.DefaultRepository
import com.hfad.doodad.dataLayer.LocalDataSource
import com.hfad.doodad.dataLayer.RemoteDataSource
import com.hfad.doodad.dataLayer.TaskRepository
import com.hfad.doodad.dataLayer.database.DooDadDatabase
import com.hfad.doodad.dataLayer.database.TaskDao

object ServiceLocator
{
    private var taskRepository : TaskRepository? = null

    private fun getDataBase(ctx : Application ) : TaskDao{
      return DooDadDatabase.getInstance(ctx).getTaskDao()
    }

    private fun getLocal(ctx: Application)  = LocalDataSource(getDataBase(ctx))
    private fun getRemote()  = RemoteDataSource()


    fun provideTasksRepository( ctx: Application ) : TaskRepository {
       return synchronized(this   ){
            taskRepository ?: DefaultRepository(

                local = getLocal(ctx), remote = getRemote()

            ).apply {
                taskRepository = this
            }
        }
    }
}