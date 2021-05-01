package io.github.vnicius.githubreposearch.extension

import android.content.Context
import android.util.TypedValue
import androidx.annotation.StyleRes


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