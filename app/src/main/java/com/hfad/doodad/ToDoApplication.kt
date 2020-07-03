package com.hfad.doodad

import android.app.Application
import com.hfad.doodad.dataLayer.LocalDataSource

class ToDoApplication : Application() {

    val taskRepository
    get() = ServiceLocator.provideTasksRepository(this)

    override fun onCreate() {
        super.onCreate()
    }
}