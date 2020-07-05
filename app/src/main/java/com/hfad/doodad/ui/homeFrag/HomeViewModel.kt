package com.hfad.doodad.ui.homeFrag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hfad.doodad.R
import com.hfad.doodad.dataLayer.TaskRepository
import com.hfad.doodad.model.Event
import com.hfad.doodad.util.CONSTANTS.ADD_EDIT_RESULT_OK
import com.hfad.doodad.util.CONSTANTS.DELETE_RESULT_OK
import com.hfad.doodad.util.CONSTANTS.EDIT_RESULT_OK

class HomeViewModel( handle: SavedStateHandle, repository: TaskRepository) : ViewModel()
{
    private var resultMessageShown: Boolean = false


    private val _snackText = MutableLiveData<Event<Int>>()
    val snackText: LiveData<Event<Int>> = _snackText

    private var _eventNavigateToDetail : MutableLiveData<Event<Int>> = MutableLiveData()
    val eventNavigateToDetail = _eventNavigateToDetail

    private var _eventAddTask = MutableLiveData<Event<Unit>>()
    val eventAddTask = _eventAddTask

    //called by HomeBinding
    fun eventAddNewTask(){
        _eventAddTask.value = Event(Unit)
    }
    //Called by TaskRowBinding
    fun navigateToDetail(taskId : Int){
        _eventNavigateToDetail.value = Event(taskId)
    }

    private fun showSnackbarMessage(message: Int) {
        _snackText.value = Event(message)
    }

    fun showUserMessage(result: Int) {
        if (resultMessageShown)
            return

        when (result) {
            EDIT_RESULT_OK -> showSnackbarMessage(R.string.successfully_saved_task_message)
            ADD_EDIT_RESULT_OK -> showSnackbarMessage(R.string.successfully_added_task_message)
            DELETE_RESULT_OK -> showSnackbarMessage(R.string.successfully_deleted_task_message)
        }

        resultMessageShown = true
    }
}