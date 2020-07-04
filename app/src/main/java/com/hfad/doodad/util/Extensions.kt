package com.hfad.doodad.util

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.hfad.doodad.model.Event
import com.hfad.doodad.model.EventObserver
import com.hfad.doodad.model.ModelFactory
import com.hfad.doodad.model.ToDoApplication

fun Fragment.getViewModelFactory() : ModelFactory =
    ModelFactory(
        this,
        (requireActivity().applicationContext as ToDoApplication).taskRepository
    )


fun View.showSnackBar( lifecycleOwner: LifecycleOwner, liveSnack: LiveData<Event<Int>>, length : Int) {
    liveSnack.observe(lifecycleOwner, EventObserver{
        Snackbar.make(this, context.getText(it.data), length ).show()
    })
}