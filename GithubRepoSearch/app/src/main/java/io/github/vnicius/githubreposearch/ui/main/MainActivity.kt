package io.github.vnicius.githubreposearch.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.vnicius.githubreposearch.R
import io.github.vnicius.githubreposearch.databinding.ActivityMainBinding
import io.github.vnicius.githubreposearch.extension.setupBottomNavigationConfig
import io.github.vnicius.githubreposearch.navigation.mainBottomNavigationConfig

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        if (savedInstanceState == null) {
            setupBottomNav()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        setupBottomNav()
    }

    private fun setupBottomNav() {
        viewBinding.customBottomNav.setupBottomNavigationConfig(
            bottomNavigationConfig = mainBottomNavigationConfig,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )
    }
}