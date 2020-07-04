package com.hfad.doodad.ui.editAdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hfad.doodad.R
import com.hfad.doodad.util.getViewModelFactory

class AddEditTaskFragment : Fragment() {

    val viewModel : EditAddViewModel by viewModels ( {this}, {getViewModelFactory()} )
    val args = navArgs<AddEditTaskFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}