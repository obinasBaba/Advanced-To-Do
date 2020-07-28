package com.hfad.doodad.ui

import android.util.LruCache
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class MainViewModel : ViewModel()
{
    val backStack = LruCache< Int, Fragment>(3)
    val currentNavController = MutableLiveData<NavController>()
}