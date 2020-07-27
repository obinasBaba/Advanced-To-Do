package com.hfad.doodad.ui.homeFrag

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.hfad.doodad.R
import com.hfad.doodad.databinding.FragmentTaskHomeBinding
import com.hfad.doodad.model.EventObserver
import com.hfad.doodad.model.TaskFilterType
import com.hfad.doodad.ui.TaskAdapter
import com.hfad.doodad.util.getViewModelFactory
import com.hfad.doodad.util.showSnackBar

private const val TAG = "log"
@SuppressLint("FragmentLiveDataObserve")
class TaskFragmentHome : Fragment() {

    private val viewModel : HomeViewModel by viewModels( {this}, {getViewModelFactory()} )
    private lateinit var binding : FragmentTaskHomeBinding
    private lateinit var taskAdapter : TaskAdapter
    private val args : TaskFragmentHomeArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_task_home, container, false)
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setUpListAdapter()
        setUpSnackBar()
        setUpNavigation()

        viewModel.items.observe(this, Observer {
            taskAdapter.submitList(it)
        })
    }
    private fun setUpListAdapter() {
        val viewModel = binding.viewModel
        viewModel?.let { VM ->
            taskAdapter = TaskAdapter(VM)
            binding.taskList.adapter = taskAdapter
        }
    }

    private fun setUpSnackBar() {
        requireView().showSnackBar(this, viewModel.snackText, Snackbar.LENGTH_SHORT)

        arguments?.let {
            viewModel.showUserMessage(args.userMsg)
        }
    }

    private fun setUpNavigation() {
        viewModel.eventAddTask.observe(this, EventObserver {
            findNavController().navigate(
                TaskFragmentHomeDirections.actionTaskFragmentHomeToAddEditTaskFragment(
                    null
                )
            )
        })
        viewModel.eventNavigateToDetail.observe(this, EventObserver {
            findNavController().navigate(
                TaskFragmentHomeDirections.actionTaskFragmentHomeToTaskDetailFrag(
                    it.data
                )
            )
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.filter_option -> {
            showPopup()
            true
        }
        R.id.clear -> {
            viewModel.clearCompletedTasks()
            true
        }
        R.id.refresh -> {
            true
        }
        else -> false
    }


    private fun showPopup() {
        val view = requireActivity().findViewById<View>(R.id.filter_option)
        PopupMenu(requireContext(), view ).run {
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.all -> {
                        viewModel.setFiltering(TaskFilterType.COMPLETED)
                    }
                    R.id.unfinished -> {
                        viewModel.setFiltering(TaskFilterType.UNCOMPLETED)
                    }
                    R.id.finished -> {
                        viewModel.setFiltering(TaskFilterType.COMPLETED)
                    }
                }
                true
            }
            inflate(R.menu.filtering)
            show()
        }
    }

}