package io.github.vnicius.githubreposearch.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import io.github.vnicius.githubreposearch.R
import io.github.vnicius.githubreposearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setupBottomNav()
    }

    private fun setupBottomNav() {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment)?.let {
            viewBinding.bottomNavigation.setupWithNavController(it.navController)
        }
    }
}