package io.github.vnicius.githubreposearch.util

import android.graphics.Bitmap
import androidx.palette.graphics.Palette
import coil.bitmap.BitmapPool
import coil.size.Size
import coil.transform.Transformation


/**
 * Created by Vinícius Veríssimo on 5/2/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class ImageColorTransformation(private val onColorGenerated: (color: Int?) -> Unit) :
    Transformation {
    override fun key(): String = "ImageColorTransformation"

    override suspend fun transform(pool: BitmapPool, input: Bitmap, size: Size): Bitmap {
        val palette = Palette.from(input).generate()

        onColorGenerated(palette.vibrantSwatch?.rgb)

        return input
    }
}