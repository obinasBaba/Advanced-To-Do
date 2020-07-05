package com.hfad.doodad.ui.editAdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hfad.doodad.R
import com.hfad.doodad.dataLayer.database.Task
import com.hfad.doodad.databinding.FragmentAddEditTaskBinding
import com.hfad.doodad.util.getViewModelFactory

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

        setUpSnackBar()
    }

    private fun setUpSnackBar() {
        TODO("Not yet implemented")
    }
}