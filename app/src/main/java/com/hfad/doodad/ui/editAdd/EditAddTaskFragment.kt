package com.hfad.doodad.ui.editAdd

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.hfad.doodad.R
import com.hfad.doodad.dataLayer.database.Task
import com.hfad.doodad.databinding.FragmentAddEditTaskBinding
import com.hfad.doodad.model.EventObserver
import com.hfad.doodad.ui.MainActivity
import com.hfad.doodad.util.CONSTANTS.ADD_EDIT_RESULT_OK
import com.hfad.doodad.util.getViewModelFactory
import com.hfad.doodad.util.showSnackBar

class AddEditTaskFragment : Fragment() {

    val viewModel : EditAddViewModel by viewModels ( {this}, {getViewModelFactory()} )
    lateinit var binding : FragmentAddEditTaskBinding
    val args : AddEditTaskFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_task, container, false)
       binding.lifecycleOwner = this.viewLifecycleOwner
       binding.viewmodel = viewModel
       return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.createNewOrUpdate(args.taskId?.toInt())
        setUpSnackBar()
        setUpNavigation()
    }

    private fun setUpSnackBar() {
        requireView().showSnackBar(this, viewModel.snackText, Snackbar.LENGTH_SHORT)
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpNavigation(){
        viewModel.signOff.observe(this, EventObserver{
            findNavController().
            navigate(AddEditTaskFragmentDirections.actionAddEditTaskFragmentToTaskFragmentHome(ADD_EDIT_RESULT_OK))
        })
    }
}