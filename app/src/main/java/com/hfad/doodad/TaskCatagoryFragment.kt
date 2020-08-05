@file:Suppress("INACCESSIBLE_TYPE")

package com.hfad.doodad

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cruxlab.sectionedrecyclerview.lib.BaseSectionAdapter
import com.cruxlab.sectionedrecyclerview.lib.SectionDataManager
import com.cruxlab.sectionedrecyclerview.lib.SimpleSectionAdapter
import com.hfad.doodad.ui.adapters.sections.SectionTemplet
import kotlinx.android.synthetic.main.fragment_task_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class TaskListFragment : Fragment() {

    val sectionManager = SectionDataManager()
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3).apply {

        }
        recyclerView.adapter = sectionManager.adapter

        recyclerView.addItemDecoration(  DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL))
        recyclerView.addItemDecoration(  DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)

        sectionManager.addSection(SectionTemplet( "AllTasks" ){

        } )
        sectionManager.addSection(SectionTemplet( null) {
            sectionManager.insertSection(sectionManager.sectionCount - 1, SectionTemplet("lable", null))
        })
    }


}
