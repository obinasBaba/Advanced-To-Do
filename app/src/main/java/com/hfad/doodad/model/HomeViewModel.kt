package com.hfad.doodad.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hfad.doodad.dataLayer.TaskRepository

class HomeViewModel(handle: SavedStateHandle, repository: TaskRepository) : ViewModel()
{
    private var _eventAddTask = MutableLiveData<Event<Unit>>()
    val eventAddTask = _eventAddTask

    private var _addNewTask : MutableLiveData<Event< Int >> = MutableLiveData<Event<Int>>()
    val addNewTask = _addNewTask

    fun eventAddTask(id : Int){
        _addNewTask.value = Event(id)
    }

    fun addNewTask(){

    }
}