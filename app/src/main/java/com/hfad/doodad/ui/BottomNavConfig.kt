package com.hfad.doodad.ui

import android.util.LruCache
import android.util.SparseArray
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.core.util.set
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hfad.doodad.R

var isFirstHost: Boolean = false
var selectedHostTag: String = ""

private val selectedNavController = MutableLiveData< NavController >() // currentNavController
private val idToTag : SparseArray< String > = SparseArray()
private var firstGraphId : Int = 0
private var firstTag : String = ""

private lateinit var fragmentManager: FragmentManager
private lateinit var lruBackStack: LruCache<Int, Fragment> //BackStack from viewModel


fun BottomNavigationView.setUpWithBottomNavigation( graphs : List< Int >,
                                                    fragMgr: FragmentManager,
                                                    backStack: LruCache<Int, Fragment> ): MutableLiveData<NavController>
{
    fragmentManager = fragMgr
    lruBackStack = backStack

    graphs.forEachIndexed{ index: Int, graph: Int ->

        val generatedTag = generateFragmentTag(graph)
        val generatedHostFragment = generateNavHost(graph, generatedTag)
        val hostGraphId = generatedHostFragment.navController.graph.id
        idToTag[hostGraphId] = generatedTag

        //figure out home(first)
        if (index == 0){
            firstGraphId = hostGraphId
            firstTag = generatedTag
        }

        if (this.selectedItemId == hostGraphId ){
            selectedNavController.value = generatedHostFragment.navController
            lruBackStack.put(hostGraphId, generatedHostFragment)
            attachHostFragment(generatedHostFragment, index == 0)
        }else{
            detachHostFragment(generatedHostFragment)
        }
    }

    isFirstHost = this.selectedItemId == firstGraphId
    selectedHostTag = idToTag[this.selectedItemId]

    handleMenuSelection()
    handleMenuReSelection()

    return selectedNavController
}

fun BottomNavigationView.handleMenuSelection() {
      this.setOnNavigationItemSelectedListener{
          if (fragmentManager.isStateSaved) {
              false
          }else{

              val newlySelectedTag = idToTag[it.itemId]
              val newlySelectedHost = fragmentManager.findFragmentByTag(newlySelectedTag) as NavHostFragment

              makeTransaction(newlySelectedHost)
              addToBackStack(newlySelectedHost, it.itemId)

              selectedNavController.value = newlySelectedHost.navController
              selectedHostTag = newlySelectedTag

              true
          }
      }
}

fun BottomNavigationView.handleMenuReSelection() {
    setOnNavigationItemReselectedListener {
       selectedNavController.value?.apply {
           //Go to home without destroying the home
           popBackStack(graph.startDestination, false)
       }
    }
}

fun addToBackStack(newlySelectedHost: NavHostFragment, graphId: Int) {
    if (newlySelectedHost.tag == firstTag){
        lruBackStack.removeExcept(firstGraphId, newlySelectedHost )
    }else
        lruBackStack.put(graphId, newlySelectedHost)
}

fun LruCache<Int, Fragment>.removeExcept(graphId: Int, host: NavHostFragment){
    evictAll()
    put(graphId,host)
}

fun LruCache<Int, Fragment>.popBackStack( ): Int? {
    return if (size() > 1){

        val hostToPop =  snapshot().keys.last()
        remove(hostToPop)
         snapshot().keys.last()
    }else{
        null
    }
}

fun makeTransaction(host: NavHostFragment) {
    fragmentManager.beginTransaction()
        .setCustomAnimations(R.anim.nav_default_enter_anim,
            R.anim.nav_default_exit_anim,
            R.anim.nav_default_pop_enter_anim,
            R.anim.nav_default_pop_exit_anim)
        .attach(host)
        .detach(fragmentManager.findFragmentByTag(selectedHostTag)!!)
        .setPrimaryNavigationFragment(host)
        .setReorderingAllowed(true)
        .commitNow()
}

fun detachHostFragment(host: NavHostFragment) {
    fragmentManager.beginTransaction().detach(host).commitNow()
}

fun attachHostFragment( host: NavHostFragment, isFirst: Boolean) {
    fragmentManager.beginTransaction().attach(host)
        .apply { if (isFirst) setPrimaryNavigationFragment(host) }
        .commitNow()
}

fun generateNavHost(@NavigationRes id: Int,
                    tag: String
                     ): NavHostFragment {

    val alreadyExists = fragmentManager.findFragmentByTag(tag) as? NavHostFragment
    alreadyExists?.let { return it }

    val hostFrag = NavHostFragment.create(id)
    fragmentManager.beginTransaction().add( R.id.nav_host_fragment_container, hostFrag, tag ).commitNow()
    return hostFrag
}

fun generateFragmentTag(id : Int) = "navHostFragment$id"
