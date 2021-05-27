package io.github.vnicius.githubreposearch.ui.common.custombottomnavigation

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.children


/**
 * Created by Vinícius Veríssimo on 25/05/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class CustomBottomNavigationMenuPresenter @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private var itemIconTint: ColorStateList? = null
    private var itemTextColor: ColorStateList? = null
    private var isLabelVisible: Boolean = true

    var onItemSelectedCallback: OnItemSelectedCallback? = null

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
    }

    private fun addChildrenFromMenu(menu: Menu) {
        for (index in 0 until menu.size()) {
            menu.getItem(index)?.let {
                addMenuItem(it)
            }
        }
    }

    private fun addMenuItem(menuItem: MenuItem) {
        val view = CustomBottomNavigationMenuItemView(context).apply {
            setupWithMenuItem(menuItem)
            setIconTint(itemIconTint)
            setTextColor(itemTextColor)
            setLabelVisibility(isLabelVisible)
        }
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f)

        addView(view, params)

        view.setOnClickListener {
            onItemSelected(menuItem)
        }
    }

    private fun onItemSelected(menuItem: MenuItem) {
        setupSelectedMenuItem(menuItem)
        onItemSelectedCallback?.onItemSelected(menuItem)
    }

    private fun setupSelectedMenuItem(menuItem: MenuItem) {
        children.forEach { child ->
            (child as? CustomBottomNavigationMenuItemView)?.let { navigationItem ->
                val isSelected = navigationItem.menuItem?.itemId == menuItem.itemId
                navigationItem.isSelected = isSelected
            }
        }
    }

    private fun setForAllChildren(block: CustomBottomNavigationMenuItemView.() -> Unit) {
        children.filterIsInstance(CustomBottomNavigationMenuItemView::class.java).forEach(block)
    }

    fun setupWithMenu(menu: Menu) {
        removeAllViews()
        addChildrenFromMenu(menu)
    }

    fun setItemSelected(menuItem: MenuItem) {
        onItemSelected(menuItem)
    }

    fun setItemIconColor(color: ColorStateList?) {
        setForAllChildren {
            setIconTint(color)
        }
        itemIconTint = color
    }

    fun setItemTextColor(color: ColorStateList?) {
        setForAllChildren {
            setTextColor(color)
        }
        itemTextColor = color
    }

    fun setLabelVisibility(isVisible: Boolean) {
        isLabelVisible = isVisible
        setForAllChildren {
            setLabelVisibility(isVisible)
        }
    }

    interface OnItemSelectedCallback {
        fun onItemSelected(menuItem: MenuItem)
    }
}