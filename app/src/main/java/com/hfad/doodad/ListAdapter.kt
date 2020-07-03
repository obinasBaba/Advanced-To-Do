package com.hfad.doodad

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hfad.doodad.dataLayer.database.Task
import com.hfad.doodad.databinding.TaskRowBinding

class ListAdapter( private val viewModel: HomeViewModel ) : ListAdapter<Task, com.hfad.doodad.ListAdapter.TaskViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  TaskViewHolder {
      return TaskViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, viewModel )
    }

    class TaskViewHolder private constructor(private val binding: TaskRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task, viewModel: HomeViewModel){
            binding.task = task
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TaskViewHolder {
                val taskBinding = TaskRowBinding.inflate(LayoutInflater.from(parent.context),
                    parent, false)
                return TaskViewHolder(taskBinding )
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return newItem == oldItem
            }
        }
    }
}