package com.hfad.doodad.bindings

import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hfad.doodad.dataLayer.database.Task
import com.hfad.doodad.ui.TaskAdapter

@BindingAdapter("app::show")
fun showLoading( view: ProgressBar, boolean: Boolean ){

}

@BindingAdapter("app:items")
fun items(view: RecyclerView, tasks: List<Task>? ){
//    (view.adapter as TaskAdapter).submitList(tasks)
}