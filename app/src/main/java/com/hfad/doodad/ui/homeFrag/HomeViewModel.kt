package com.hfad.doodad.ui.homeFrag

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.hfad.doodad.R
import com.hfad.doodad.dataLayer.TaskRepository
import com.hfad.doodad.dataLayer.database.Task
import com.hfad.doodad.model.Event
import com.hfad.doodad.model.Result
import com.hfad.doodad.model.Result.Success
import com.hfad.doodad.model.TaskFilterType
import com.hfad.doodad.model.TaskFilterType.*
import com.hfad.doodad.util.CONSTANTS.ADD_EDIT_RESULT_OK
import com.hfad.doodad.util.CONSTANTS.DELETE_RESULT_OK
import com.hfad.doodad.util.CONSTANTS.EDIT_RESULT_OK
import kotlinx.coroutines.launch

// Used to save the current filtering in SavedStateHandle.
const val TASKS_FILTER_SAVED_STATE_KEY = "TASKS_FILTER_SAVED_STATE_KEY"


class HomeViewModel(private val savedState: SavedStateHandle,private val repository: TaskRepository) : ViewModel() {

    private val _currentFilteringLabel = MutableLiveData<Int>()
    val currentFilteringLabel: LiveData<Int> = _currentFilteringLabel

    private var resultMessageShown: Boolean = false
    val isDataLoadingError = MutableLiveData< Boolean>()

    private val _isDataLoading = MutableLiveData<Boolean>()
    val isDataLoading: LiveData<Boolean> = _isDataLoading

    private val _snackText = MutableLiveData<Event<Int>>()
    val snackText: LiveData<Event<Int>> = _snackText

    private var _eventNavigateToDetail : MutableLiveData<Event<Int>> = MutableLiveData()
    val eventNavigateToDetail = _eventNavigateToDetail

    private var _eventAddTask = MutableLiveData<Event<Unit>>()
    val eventAddTask = _eventAddTask

    private val _updateRequired = MutableLiveData<Boolean>(false)

    private val _items = _updateRequired.switchMap { fetch ->
        if ( fetch ){
            _isDataLoading.value = true
            viewModelScope.launch {
                repository.refreshTasks()
            }
            _isDataLoading.value = false
        }

        repository.observeAll().distinctUntilChanged().switchMap {
            filterTasks(it)
        }
    }

    val items: LiveData<List<Task>> = _items

    init {
        setFiltering( getSavedFilterType() )
        loadData(true)
    }

    fun setFiltering(type: TaskFilterType) {
        when(type) {
            ALL_TASKS -> {
                setFilter(
                    R.string.label_all
                )
            }
            COMPLETED -> {
                setFilter(
                    R.string.label_completed
                )
            }
            UNCOMPLETED -> {
                setFilter(
                    R.string.label_active
                )
            }
        }

        loadData(false)
    }

    fun setFilter(@StringRes labelCompleted: Int) {
        _currentFilteringLabel.value = labelCompleted
    }

    private fun loadData(refresh: Boolean) {
        _updateRequired.value = refresh
    }

    //called by FAB
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

    private fun filterTasks(result: Result<List<Task>>): LiveData<List<Task>> {
        val filteredTasks = MutableLiveData<List<Task>>()
        if (result is Success) {
            isDataLoadingError.value = false
            filteredTasks.value = filterItems(result.data, getSavedFilterType())

        } else {
            filteredTasks.value = emptyList()
            isDataLoadingError.value = true
            showSnackbarMessage(R.string.loading_tasks_error)
        }
        return filteredTasks
    }

    private fun filterItems(rawTasks: List< Task>, type: TaskFilterType ) : List<Task>{
        val tasksToShow = ArrayList<Task>()
        for (task in rawTasks ) {
            when(type) {
                ALL_TASKS -> { tasksToShow.add(task) }
                COMPLETED -> { if (task.isFinish()) tasksToShow.add(task) }
                UNCOMPLETED -> { if (task.isActive()) tasksToShow.add(task) }
            }
        }
        return tasksToShow
    }

    private fun getSavedFilterType(   ) =
        savedState.get<TaskFilterType>( TASKS_FILTER_SAVED_STATE_KEY ) ?: ALL_TASKS

    fun clearCompletedTasks() {
        viewModelScope.launch {
            if (repository.clearCompleted() > 0)
                showSnackbarMessage(R.string.completed_tasks_cleared)
            else
                showSnackbarMessage(R.string.no_tasks_active)
        }
    }

}