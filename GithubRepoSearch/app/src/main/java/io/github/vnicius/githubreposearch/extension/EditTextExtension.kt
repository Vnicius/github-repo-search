package io.github.vnicius.githubreposearch.extension

import android.graphics.drawable.Drawable
import android.widget.EditText


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

fun EditText.setStartCompoundDrawable(drawable: Drawable?) {
    val currentDrawables = compoundDrawablesRelative

    setCompoundDrawablesRelativeWithIntrinsicBounds(
        drawable,
        currentDrawables[1],
        currentDrawables[2],
        currentDrawables[3]
    )
}

fun EditText.setEndCompoundDrawable(drawable: Drawable?) {
    val currentDrawables = compoundDrawablesRelative

    setCompoundDrawablesRelativeWithIntrinsicBounds(
        currentDrawables[0],
        currentDrawables[1],
        drawable,
        currentDrawables[3]
    )
}

val EditText.startCompoundDrawable: Drawable?
    get() = compoundDrawablesRelative[0]

val EditText.endCompoundDrawable: Drawable?
    get() = compoundDrawablesRelative[2]