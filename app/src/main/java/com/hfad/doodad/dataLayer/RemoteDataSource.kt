package com.hfad.doodad.dataLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hfad.doodad.dataLayer.database.Task
import com.hfad.doodad.model.Result
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

 class RemoteDataSource : TaskRepository{

    override suspend fun refreshTasks() {
        //TODO Change the scope
        GlobalScope.launch {
            delay(3000)
        }
    }

    private val dummyLiveData1 = MutableLiveData<Result<Task>>()
        private val dummyLiveData2 = Result.Loading

    override fun observeAll(): LiveData<Result<List<Task>>> {


       return MutableLiveData(dummyLiveData2)
    }

    override fun observeTask(taskId: Int): LiveData<Result<Task>> {
       return dummyLiveData1
    }

    override suspend fun getAllTasks(): Result<List<Task>> {
        return dummyLiveData2
    }

    override suspend fun getTaskWithId(taskId: Int): Result<Task> {
        return dummyLiveData2
    }

    override suspend fun deleteTask(taskId: Int) {}

    override suspend fun deleteTaskWithId(taskId: Int) {}

    override suspend fun updateTaskStatus(completed: Boolean, taskId: Int) {}

    override suspend fun updateTask(task: Task) {}

    override suspend fun saveTask(task: Task) {}

     override suspend fun clearCompleted() = 0

     override suspend fun deleteTask(task: Task) {}
}