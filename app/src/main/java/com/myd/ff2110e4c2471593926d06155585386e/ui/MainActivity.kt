package com.myd.ff2110e4c2471593926d06155585386e.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.myd.ff2110e4c2471593926d06155585386e.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private fun showBotNav() {
        bottomNavi.visibility = View.VISIBLE
    }

    private fun hideBotNav() {
        bottomNavi.visibility = View.GONE
    }
}