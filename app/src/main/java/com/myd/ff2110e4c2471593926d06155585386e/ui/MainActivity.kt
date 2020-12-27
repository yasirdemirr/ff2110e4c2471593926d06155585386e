package com.myd.ff2110e4c2471593926d06155585386e.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myd.ff2110e4c2471593926d06155585386e.R
import com.myd.ff2110e4c2471593926d06155585386e.extensions.setupWithNavController
import com.myd.ff2110e4c2471593926d06155585386e.ui.home.HomePageFragment


class MainActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigation()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavi)

        val navGraphIds =
            listOf(R.navigation.app_navigation, R.navigation.favorite)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.home_page_fragment,
            intent = intent
        )
        currentNavController = controller

    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onBackPressed() {
        var activeFragment = supportFragmentManager.findFragmentById(R.id.home_page_fragment)
        if (activeFragment?.childFragmentManager?.fragments?.first() is HomePageFragment) {
            super.onBackPressed()
        } else {

        }
    }
}