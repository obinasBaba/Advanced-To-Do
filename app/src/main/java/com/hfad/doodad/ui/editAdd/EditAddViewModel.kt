package com.hfad.doodad.ui.editAdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.doodad.R
import com.hfad.doodad.dataLayer.TaskRepository
import com.hfad.doodad.dataLayer.database.Task
import com.hfad.doodad.model.Event
import com.hfad.doodad.model.Result
import com.hfad.doodad.model.Result.*
import kotlinx.coroutines.launch
import java.lang.ArithmeticException
import kotlin.system.exitProcess

class EditAddViewModel(private val repository: TaskRepository ) : ViewModel()
{

    var taskId : Int? = null

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData< Boolean> = _isLoading

    private val _loaded = MutableLiveData<Boolean>()
    val loaded: LiveData< Boolean> = _loaded

//    var task = MutableLiveData<Task>()
    var originalTask = Task()

    val _title = MutableLiveData<String>( )
    val title: LiveData< String > = _title

    val _body = MutableLiveData<String>( )
    val body: LiveData< String > = _body

    val _snackText = MutableLiveData<Event<Int>>( )
    val snackText: LiveData< Event<Int> > = _snackText


    fun configNewOrUpdate(taskId : Int?){
        if (isLoading.value == true ){
            return
        }

        this.taskId = taskId
        if (taskId == null){
            return
        }

        if (loaded.value == true){
            return
        }

        _isLoading.value = true
        viewModelScope.launch {
            repository.getTaskWithId(taskId).let {
               if ( it is Success ) {
                   _title.value = it.data.title
                   _body.value = it.data.description
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

    fun updateTitle() : (String) -> Unit = { s -> _title.value = s }

    fun onTitleChange(  ) : (text: CharSequence?,
                             start: Int,
                             count: Int,
                             after: Int ) -> Unit =
        { text, _, _, _ ->
            _title.value =  text.toString()
        }


    fun onDoneClicked(){
       if (isTaskEmpty())
           _snackText.value = Event(R.string.empty_task_message)
    }

    private fun isTaskEmpty() = (title.value?.isEmpty() ?: false) and ( body.value?.isEmpty() ?: false )
}