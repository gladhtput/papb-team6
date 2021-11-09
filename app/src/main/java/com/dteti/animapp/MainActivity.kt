package com.dteti.animapp

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dteti.animapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.logging.Logger

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("Lifecycle", "onCreate() called.")

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_favourite, R.id.navigation_about
            )
        )
        navView.setupWithNavController(navController)
    }

    override fun onPause() {
        Log.i("Lifecycle", "onPause() called.")

        super.onPause()
    }

    override fun onResume() {
        Log.i("Lifecycle", "onResume() called.")

        super.onResume()
    }

    override fun onDestroy() {
        Log.i("Lifecycle", "onDestroy() called.")

        super.onDestroy()
    }
}