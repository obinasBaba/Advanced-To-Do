package com.hfad.doodad.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hfad.doodad.TagListFragment
import com.hfad.doodad.TaskListFragment
import com.hfad.doodad.ui.events.EventOne
import java.lang.IllegalArgumentException

const val TASK_LIST_PAGE_INDEX = 0
const val TAG_LIST_PAGE_INDEX = 1

class HomePaggerAdapter(parent: Fragment) : FragmentStateAdapter(parent)
{
    private val indexToFrag = mapOf(
        TASK_LIST_PAGE_INDEX to { TaskListFragment() },
        TAG_LIST_PAGE_INDEX to { TagListFragment() } )

    override fun getItemCount(): Int = indexToFrag.size

    override fun createFragment(position: Int): Fragment =
        indexToFrag[position]?.invoke() ?: throw IndexOutOfBoundsException("NOOT INDEX FOUND")
}