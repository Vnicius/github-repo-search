package io.github.vnicius.githubreposearch.navigation

import androidx.annotation.MenuRes
import androidx.annotation.NavigationRes


/**
 * Created by Vinícius Veríssimo on 5/4/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
data class BottomNavigationConfig(@MenuRes val menuRes: Int, @NavigationRes val graphs: List<Int>)
