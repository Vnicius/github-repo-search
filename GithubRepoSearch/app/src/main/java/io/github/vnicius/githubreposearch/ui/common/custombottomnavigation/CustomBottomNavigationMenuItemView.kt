package io.github.vnicius.githubreposearch.ui.common.custombottomnavigation

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.isVisible


/**
 * Created by Vinícius Veríssimo on 25/05/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class CustomBottomNavigationMenuItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private var iconImageView: AppCompatImageView? = null
    private var labelTextView: AppCompatTextView? = null

    var menuItem: MenuItem? = null
        private set

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        setupRippleBackground()
        addIconImageView()
        addLabelTextView()
    }

    private fun setupRippleBackground() {
        val outValue = TypedValue()
        context.theme.resolveAttribute(
            android.R.attr.selectableItemBackgroundBorderless,
            outValue,
            true
        )
        setBackgroundResource(outValue.resourceId)
    }

    private fun addIconImageView() {
        addView(
            AppCompatImageView(context).also {
                iconImageView = it
            },
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        )
    }

    private fun addLabelTextView() {
        addView(
            AppCompatTextView(context).also {
                labelTextView = it
            },
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        )
    }

    fun setupWithMenuItem(menuItem: MenuItem) {
        iconImageView?.setImageDrawable(menuItem.icon)
        labelTextView?.text = menuItem.title
        TooltipCompat.setTooltipText(this, menuItem.title)
        this.menuItem = menuItem
    }

    fun setIconTint(color: ColorStateList?) {
        iconImageView?.imageTintList = color
    }

    fun setTextColor(color: ColorStateList?) {
        labelTextView?.setTextColor(color)
    }

    fun setLabelVisibility(isVisible: Boolean) {
        labelTextView?.isVisible = isVisible
    }
}