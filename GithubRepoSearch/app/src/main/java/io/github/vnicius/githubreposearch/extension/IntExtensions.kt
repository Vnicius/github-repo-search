package io.github.vnicius.githubreposearch.extension


/**
 * Created by Vinícius Veríssimo on 5/2/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

fun Int.toHexColor(): String = String.format("#%06X", (0xFFFFFF and this))