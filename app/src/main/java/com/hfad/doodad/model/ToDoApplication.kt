package com.hfad.doodad.model

import android.app.Application
import com.hfad.doodad.util.ServiceLocator

class ToDoApplication : Application() {

    val taskRepository
    get() = ServiceLocator.provideTasksRepository(this)

}