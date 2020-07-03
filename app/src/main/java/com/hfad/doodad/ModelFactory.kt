package com.hfad.doodad

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hfad.doodad.dataLayer.TaskRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ModelFactory(owner : Fragment,private val repository: TaskRepository) : AbstractSavedStateViewModelFactory( owner, null )
{
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {

        return with(modelClass){
            when{
                isAssignableFrom(HomeViewModel::class.java) -> {
                    HomeViewModel(handle, repository)
                }
                else -> throw IllegalArgumentException(" THIS IS ILLEGAL &&&&&&&")
            }
        } as T
    }
}

fun Fragment.getViewModelFactory() : ModelFactory =
    ModelFactory(this,( requireActivity().applicationContext as ToDoApplication).taskRepository)