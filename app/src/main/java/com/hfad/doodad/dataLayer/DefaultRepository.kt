package com.hfad.doodad.dataLayer

import androidx.lifecycle.LiveData
import com.hfad.doodad.dataLayer.database.Task
import com.hfad.doodad.model.Result
import kotlinx.coroutines.delay

class DefaultRepository(private val remote : TaskRepository, private val local : TaskRepository ) : TaskRepository
{
    override suspend fun refreshTasks() {
        remote.refreshTasks()
    }

    override fun observeAll(): LiveData<Result<List<Task>>> {
        return local.observeAll()
    }

    override fun observeTask(taskId: Int): LiveData<Result<Task>> {
        return local.observeTask(taskId)
    }

    override suspend fun getAllTasks(): Result<List<Task>> {
        return local.getAllTasks()
    }

    override suspend fun getTaskWithId(taskId: Int): Result<Task> {
        return local.getTaskWithId(taskId)
    }

    override suspend fun deleteTask(taskId: Int) {
         local.deleteTask(taskId)
    }

    override suspend fun deleteTask(task: Task) {
        local.deleteTask(task)
    }

    override suspend fun deleteTaskWithId(taskId: Int) {
        local.deleteTaskWithId(taskId)
    }

    override suspend fun updateTaskStatus(completed: Boolean, taskId: Int) {
         local.updateTaskStatus(completed, taskId)
    }

    override suspend fun updateTask(task: Task) {
         local.updateTask(task)
    }

    override suspend fun saveTask(task: Task) {
         return local.saveTask(task)
    }

    override suspend fun clearCompleted() : Int{
        return local.clearCompleted()
    }

    override suspend fun count(): Int {
        return local.count()
    }

}