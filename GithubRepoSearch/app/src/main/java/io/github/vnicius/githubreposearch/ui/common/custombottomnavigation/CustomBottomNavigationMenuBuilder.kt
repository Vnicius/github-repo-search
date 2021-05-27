package io.github.vnicius.githubreposearch.ui.common.custombottomnavigation

import android.content.Context
import android.view.Menu
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.core.view.get


/**
 * Created by Vinícius Veríssimo on 25/05/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class CustomBottomNavigationMenuBuilder(context: Context) {
    private val fakePopMenu: PopupMenu by lazy {
        PopupMenu(context, null)
    }
    var menu: Menu? = null
        get() = fakePopMenu.menu
        private set

    fun inflateMenu(@MenuRes menuRes: Int) {
        clearMenu()
        fakePopMenu.inflate(menuRes)
    }

    private fun clearMenu() {
        menu?.let { menu ->
            while (menu.size() > 0) {
                menu.removeItem(menu[0].itemId)
            }
        }
    }
}