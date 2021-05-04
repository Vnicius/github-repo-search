package io.github.vnicius.githubreposearch.extension

import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette


/**
 * Created by Vinícius Veríssimo on 5/2/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

@ColorInt
fun ImageView.getVibrantColor(): Int? = drawable?.let {
    try {
        Palette.from(it.toBitmap()).generate().vibrantSwatch?.rgb
    } catch (e: Exception) {
        null
    }
}