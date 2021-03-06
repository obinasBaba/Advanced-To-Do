package com.hfad.doodad.dataLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.hfad.doodad.model.Result.Error
import com.hfad.doodad.model.Result.Success
import com.hfad.doodad.dataLayer.database.Task
import com.hfad.doodad.dataLayer.database.TaskDao
import com.hfad.doodad.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LocalDataSource(private val dataBase : TaskDao ) : TaskRepository{

    override suspend fun refreshTasks()  {

    }

    override fun observeAll(): LiveData<Result<List<Task>>> {
        return dataBase.observeAll().map {
            Success(it)  //wrapping it with Result
        }
    }

    override fun observeTask(taskId: Int): LiveData<Result<Task>> {
        return dataBase.observeTask(taskId).map {
            Success(it)  //wrapping it with Result
        }
    }

    override suspend fun getAllTasks(): Result<List<Task>> = withContext(IO){
        return@withContext try {
            Success(dataBase.getAllTasks())
        }catch (e : Exception){
              Error(e)
        }
    }

    override suspend fun getTaskWithId(taskId: Int): Result<Task> = withContext(IO){
        try {
            val task = dataBase.getTaskWithId(taskId)
            return@withContext if (task != null) Success(task) else Error(Exception("Task Not Found"))
        }catch (e: Exception){
            return@withContext Error(e)
        }
    }

    override suspend fun deleteTask(taskId: Int)
      =  withContext(IO){
            dataBase.deleteTask(taskId)
        }

    override suspend fun deleteTaskWithId(taskId: Int) {

    }

    override suspend fun updateTaskStatus(completed: Boolean, taskId: Int) {

    }

    override suspend fun updateTask(task: Task) = withContext(IO){
        dataBase.updateTask(task)
    }

    override suspend fun saveTask(task: Task)   = withContext(IO){
        dataBase.insertTask(task)
    }

    override suspend fun clearCompleted() = withContext(IO){
        dataBase.clearCompleted()
    }

    override suspend fun count(): Int = withContext(IO){
        dataBase.count()
    }

    override suspend fun deleteTask(task: Task) = withContext(IO){
        dataBase.deleteTask(task)
    }
}