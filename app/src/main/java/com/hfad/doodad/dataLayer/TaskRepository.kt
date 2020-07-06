package com.hfad.doodad.dataLayer

import androidx.lifecycle.LiveData
import com.hfad.doodad.dataLayer.database.Task
import com.hfad.doodad.model.Result

/** gate way to dataLayer */
interface TaskRepository {

    suspend fun refreshTasks()

    fun observeAll(): LiveData<Result<List<Task>>>

    fun observeTask(taskId: Int): LiveData<Result<Task>>

    suspend fun getAllTasks(): Result<List<Task>>

    suspend fun getTaskWithId(taskId: Int): Result<Task>

    suspend fun deleteTask(taskId: Int)

    suspend fun deleteTaskWithId(taskId: Int)

    suspend fun updateTaskStatus(completed: Boolean, taskId: Int)

    suspend fun updateTask(task: Task)

    suspend fun saveTask(task: Task)

    suspend fun deleteTask(task: Task)
}