package io.github.vnicius.githubreposearch.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import androidx.core.content.res.ResourcesCompat


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
@StyleRes
fun Context.getStyleFromAttr(resId: Int): Int {
    val typedValue = TypedValue()

    theme.resolveAttribute(resId, typedValue, true)

    return typedValue.data
}

@ColorInt
fun Context.getColorFromAttr(resId: Int): Int {
    val typedValue = TypedValue()

    theme.resolveAttribute(resId, typedValue, true)

    return typedValue.data
}

fun Context.getDrawableFromAttr(resId: Int): Drawable? {
    val typedValue = TypedValue()

    theme.resolveAttribute(resId, typedValue, true)

    return ResourcesCompat.getDrawable(resources, typedValue.resourceId, null)
}

fun Context.hideKeyboard(focusedView: View?) {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let {
        focusedView?.let { view ->
            it.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }
    }
}