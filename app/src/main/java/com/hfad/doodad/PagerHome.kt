package com.hfad.doodad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hfad.doodad.databinding.FragmentPaggerHomeBinding
import com.hfad.doodad.ui.adapters.HomePaggerAdapter
import com.hfad.doodad.ui.adapters.TAG_LIST_PAGE_INDEX
import com.hfad.doodad.ui.adapters.TASK_LIST_PAGE_INDEX

class PagerHome : Fragment() {



    lateinit var binding: FragmentPaggerHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pagger_home, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewPager.adapter = HomePaggerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab , position  ->
            when(position){
                TAG_LIST_PAGE_INDEX -> tab.text = "TAGS"
                TASK_LIST_PAGE_INDEX -> tab.text = "TASKS"
            }
        }.attach()

    }

}