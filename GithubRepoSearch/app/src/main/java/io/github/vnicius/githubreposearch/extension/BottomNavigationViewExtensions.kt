package io.github.vnicius.githubreposearch.extension

import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.vnicius.githubreposearch.navigation.BottomNavigationConfig


/**
 * Created by Vinícius Veríssimo on 5/4/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

fun BottomNavigationView.setupBottomNavigationConfig(
    bottomNavigationConfig: BottomNavigationConfig,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent
): LiveData<NavController> {
    inflateMenu(bottomNavigationConfig.menuRes)
    return setupWithNavController(
        bottomNavigationConfig.graphs,
        fragmentManager,
        containerId,
        intent
    )
}
