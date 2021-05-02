package io.github.vnicius.githubreposearch.extension

import android.content.res.AssetManager


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

fun AssetManager.geContentFromFile(fileName: String): String? = try {
    val inputStream = open(fileName)
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    String(buffer)
} catch (e: Exception) {
    null
}