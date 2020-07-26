package com.hfad.doodad.dataLayer.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao
{
    @Query("SELECT * FROM tasks")
    fun observeAll() : LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun observeTask(taskId: Int) : LiveData< Task >

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks() : List< Task >

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskWithId(taskId : Int) : Task?

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTaskWithId(taskId: Int)

    @Query("UPDATE tasks SET completed = :completed WHERE id = :taskId ")
    suspend fun updateTaskStatus(completed: Boolean, taskId: Int)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM tasks WHERE completed = 1")
    fun clearCompleted() : Int

}