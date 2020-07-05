package com.hfad.doodad.ui.homeFrag

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.hfad.doodad.R
import com.hfad.doodad.databinding.FragmentTaskHomeBinding
import com.hfad.doodad.model.EventObserver
import com.hfad.doodad.ui.ListAdapter
import com.hfad.doodad.util.getViewModelFactory
import com.hfad.doodad.util.showSnackBar

private const val TAG = "log"
@SuppressLint("FragmentLiveDataObserve")
class TaskFragmentHome : Fragment() {

    private val viewModel : HomeViewModel by viewModels( {this}, {getViewModelFactory()} )
    private lateinit var binding : FragmentTaskHomeBinding
    private lateinit var listAdapter : ListAdapter
    private val args : TaskFragmentHomeArgs by navArgs()

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
        setUpSnackBar()
        setUpNavigation()
    }
    private fun setUpListAdapter() {
        val viewModel = binding.viewModel
        viewModel?.let { VM ->
            listAdapter = ListAdapter(VM)
            binding.taskList.adapter = listAdapter
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


}