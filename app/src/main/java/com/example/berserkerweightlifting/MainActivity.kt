package com.example.berserkerweightlifting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.berserkerweightlifting.viewModel.AppViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupBottomNavMenu(navController)


        // Barra de navegacion superior
        //appBarConfiguration= AppBarConfiguration(navController.graph)
        appBarConfiguration= AppBarConfiguration(setOf(R.id.slashScreenFragment,
            R.id.loginScreenFragment,
            R.id.registrationScreenFragment,
            R.id.homeScreenFragment,
            R.id.profileScreenFragment,
            R.id.settingsScreenFragment))

        toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if( destination.id == R.id.slashScreenFragment  ||
                destination.id == R.id.loginScreenFragment ||
                destination.id == R.id.registrationScreenFragment ||
                destination.id == R.id.prsScreenFragment ||
                destination.id == R.id.userInformationScreenFragment ||
                destination.id == R.id.premiumScreenFragment ||
                destination.id == R.id.rutinaScreenFragment){
                bottomNav.visibility = View.GONE
                //toolbar.visibility = View.GONE


            }
            else {
                bottomNav.visibility = View.VISIBLE
            }
        }

        /*val sharedViewModel: AppViewModel by viewModels()
        sharedViewModel.funcionPrueba()
        sharedViewModel.face.observe(this){
            println("Aqui demostrando que hubo un cambio")
        }*/




        //setupActionBarWithNavController(navController)

    }

    private fun setupBottomNavMenu(navController: NavController){
        bottomNav = findViewById(R.id.bottomNavigationView)
        bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }
    /*private fun setToolbar(){
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar = findViewById(R.id.toolbar_main)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }*/
}