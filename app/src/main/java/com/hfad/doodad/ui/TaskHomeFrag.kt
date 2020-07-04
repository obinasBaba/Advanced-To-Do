package com.hfad.doodad.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hfad.doodad.R
import com.hfad.doodad.databinding.FragmentTaskHomeBinding
import com.hfad.doodad.model.EventObserver
import com.hfad.doodad.model.HomeViewModel
import com.hfad.doodad.model.getViewModelFactory

private const val TAG = "log"
@SuppressLint("FragmentLiveDataObserve")
class TaskFragmentHome : Fragment() {

    private val viewModel : HomeViewModel by viewModels( {this}, {getViewModelFactory()} )
    private lateinit var binding : FragmentTaskHomeBinding
    private lateinit var listAdapter : ListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_task_home, container, false)
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setUpListAdapter()
        setUpNavigation()
    }


    private fun setUpNavigation() {
        viewModel.eventAddTask.observe(this, EventObserver{
            Snackbar.make(binding.root, "Add task ", Snackbar.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_taskFragmentHome_to_taskDetailFrag)
        })

        viewModel.addNewTask.observe(this, EventObserver{
            findNavController().navigate(R.id.action_taskFragmentHome_to_taskDetailFrag)
        })
    }

    private fun setUpListAdapter() {

        val viewModel = binding.viewModel
        viewModel?.let { VM ->
            listAdapter = ListAdapter(VM)
            binding.taskList.adapter = listAdapter
        }
    }
}