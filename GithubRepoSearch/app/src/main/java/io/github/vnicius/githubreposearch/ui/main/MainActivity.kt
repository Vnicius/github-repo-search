package io.github.vnicius.githubreposearch.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.vnicius.githubreposearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}