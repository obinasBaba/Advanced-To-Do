package com.hfad.doodad.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.hfad.doodad.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private lateinit var navController : NavController
  private lateinit var drawerLayout : DrawerLayout
  private lateinit var navigationView : NavigationView
  private lateinit var appBarConfiguration : AppBarConfiguration

    val viewModel: MainViewModel by viewModels()

    private lateinit var currentNavController : MutableLiveData<NavController>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null )
            setUp()

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setUp()
    }

    private fun setUp() {
        val graphList = listOf(
            R.navigation.tasks,
            R.navigation.events,
            R.navigation.setting
        )

        currentNavController = bottomNavigationView.setUpWithBottomNavigation(
            graphList,
            supportFragmentManager,
            viewModel.backStack
        )

        currentNavController.observe(this, Observer {
            Toast.makeText(this, "Controller Changed", Toast.LENGTH_SHORT).show()
        })
    }


    override fun onBackPressed() {
        if (isAtStartDestination())
        {
            return
        }else{
            viewModel.backStack.popBackStack()?.let {
                bottomNavigationView.selectedItemId = it
            } ?:
            super.onBackPressed()
        }
    }

    private fun isAtStartDestination(): Boolean =
        currentNavController.value!!.navigateUp()


}

