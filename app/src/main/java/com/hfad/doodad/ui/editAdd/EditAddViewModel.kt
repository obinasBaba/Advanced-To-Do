package com.hfad.doodad.ui.editAdd

import androidx.lifecycle.*
import com.hfad.doodad.R
import com.hfad.doodad.dataLayer.TaskRepository
import com.hfad.doodad.dataLayer.database.Task
import com.hfad.doodad.model.Event
import com.hfad.doodad.model.Result.Success
import kotlinx.coroutines.launch
import java.lang.RuntimeException

class EditAddViewModel(private val repository: TaskRepository ) : ViewModel()
{

    private var isNewTask: Boolean = false
    var taskId : Int? = null

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData< Boolean> = _isLoading

    private val _loaded = MutableLiveData<Boolean>()
    val loaded: LiveData< Boolean> = _loaded

    var originalTask = Task()
    var taskToModify = MediatorLiveData< Task >()

    private val _signOff = MutableLiveData<Event<Unit>>()
    val signOff = _signOff

    //for TwoWay dataBinding
    val title = MutableLiveData<String>( )
    val description = MutableLiveData<String>( )

    private val _snackText = MutableLiveData<Event<Int>>( )
    val snackText: LiveData< Event<Int> > = _snackText


    fun createNewOrUpdate(taskId : Int?){

        //preventing interruption from rotation( return if already started to load )
        if (isLoading.value == true ){
            return
        }

        this.taskId = taskId

        //first time and new
        if (taskId == null){
           if (taskToModify.value == null){
               taskToModify.value = Task()
           }
            isNewTask = true
            return
        }

        //not new( there is selected task to display )
        //preventing accessing repo again if loaded already
        if (loaded.value == true){
            return
        }

        //not new and not loaded yet
        _isLoading.value = true
        viewModelScope.launch {
            repository.getTaskWithId(taskId).let {
               if ( it is Success ) {
                   title.value = it.data.title
                   description.value = it.data.description
                   originalTask = it.data
                   taskToModify.value = originalTask.copy()
                   _loaded.value = true
               }else{
                   throw ArithmeticException("no datatata")
               }
                _isLoading.value = false
            }
        }
    }

    private fun createOrUpdate(){
        if (isNewTask && taskId == null)
            createTask()
        else
            updateTask()
    }

    private fun updateTask() = viewModelScope.launch{
        if (isNewTask){
            throw RuntimeException("updateTask() canceld because it is new task")
        }else{
            repository.saveTask(taskToModify.value!!)
            _signOff.value = Event(Unit)
        }
    }

    private fun createTask() = viewModelScope.launch {
        taskToModify.value?.title = title.value!!
        taskToModify.value?.description = description.value!!
        repository.saveTask(taskToModify.value!!)
        _signOff.value = Event(Unit)

    }

    fun onDoneClicked(){
       if (isTaskEmpty()) {
           _snackText.value = Event(R.string.empty_task_message)
           return
       }

        createOrUpdate()
    }

    private fun isTaskEmpty() = (title.value?.isEmpty() ?: true) or ( description.value?.isEmpty() ?: true)
}