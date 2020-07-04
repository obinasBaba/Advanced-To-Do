package com.hfad.doodad.ui.editAdd

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.doodad.dataLayer.TaskRepository
import com.hfad.doodad.model.Result
import com.hfad.doodad.model.Result.*
import kotlinx.coroutines.launch

class EditAddViewModel(private val repository: TaskRepository ) : ViewModel()
{
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    private val _loaded = MutableLiveData<Boolean>()
    val loaded = _loaded

    var taskId : Int? = null

    fun start(taskId : Int?){
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

        isLoading.value = true
        viewModelScope.launch {
            val result =repository.getTaskWithId(taskId)
            if (result is Success){

            }else{

            }
        }
    }
}