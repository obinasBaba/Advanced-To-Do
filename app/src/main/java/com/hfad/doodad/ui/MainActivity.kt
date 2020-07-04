package com.hfad.doodad.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.hfad.doodad.R

class MainActivity : AppCompatActivity() {

  private lateinit var navController : NavController
  private lateinit var drawerLayout : DrawerLayout
  private lateinit var navigationView : NavigationView
  private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        navController = findNavController(R.id.navHostFragment)
         drawerLayout = findViewById (R.id.drawer_layout)
         navigationView = findViewById (R.id.navigation_view)


        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.taskFragmentHome,
            R.id.taskStatisticsFrag
        ).setOpenableLayout(drawerLayout) .build()

        setupActionBarWithNavController(navController,appBarConfiguration)
        navigationView.setupWithNavController(navController)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected( navController ) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}