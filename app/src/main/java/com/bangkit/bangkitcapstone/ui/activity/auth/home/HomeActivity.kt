package com.bangkit.bangkitcapstone.ui.activity.auth.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.bangkitcapstone.R
import com.bangkit.bangkitcapstone.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        val hiddenDestinations =
            setOf(
                R.id.foodDetailFragment,
                R.id.caloriesFragment,
                R.id.cameraFragment,
                R.id.dairyIntakeFragment,
                R.id.timerFragment,
                R.id.workoutDetailFragment,
                R.id.workoutIntakeFragment,
                R.id.settingsFragment
            )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            navView.visibility =
                if (destination.id in hiddenDestinations) View.GONE else View.VISIBLE
        }
    }
}