package com.hfad.doodad.ui.editAdd

import androidx.lifecycle.*
import com.hfad.doodad.R
import com.hfad.doodad.dataLayer.TaskRepository
import com.hfad.doodad.dataLayer.database.Task
import com.hfad.doodad.model.Event
import com.hfad.doodad.model.Result.*
import kotlinx.coroutines.launch
import java.lang.ArithmeticException

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

    //for TwoWay dataBinding
    val title = MutableLiveData<String>( )
    val body = MutableLiveData<String>( )

    private val _snackText = MutableLiveData<Event<Int>>( )
    val snackText: LiveData< Event<Int> > = _snackText


    fun configNewOrUpdate(taskId : Int?){
        if (isLoading.value == true ){
            return
        }

        this.taskId = taskId
        if (taskId == null){
           if (taskToModify.value == null){
               taskToModify.value = Task()
           }
            isNewTask = true
            return
        }

        if (loaded.value == true){
            return
        }

        _isLoading.value = true
        viewModelScope.launch {
            repository.getTaskWithId(taskId).let {
               if ( it is Success ) {
                   title.value = it.data.title
                   body.value = it.data.description
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

    fun addTask(){

    }

    fun onDoneClicked(){
       if (isTaskEmpty())
           _snackText.value = Event(R.string.empty_task_message)

    }

    private fun isTaskEmpty() = (title.value?.isEmpty() ?: true) or ( body.value?.isEmpty() ?: true)
}