package io.github.vnicius.githubreposearch.extension


/**
 * Created by Vinícius Veríssimo on 5/2/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

fun Int.toHexColor(): String = String.format("#%06X", (0xFFFFFF and this))

fun Int.toCompact(): String =
    when (this) {
        in 1000..999999 -> {
            val intPart = (this / 1000f).toInt()
            val decimalPart =
                (this - intPart).takeIf { it > 100 }
                    ?.toString()
                    ?.firstOrNull()
                    ?.let { ".${it}" }
                    ?: ""

            "$intPart${decimalPart}k"
        }
        in 1000000..999999999 -> {
            val intPart = (this / 1000000f).toInt()
            val decimalPart =
                (this - intPart).takeIf { it > 100000 }
                    ?.toString()
                    ?.firstOrNull()
                    ?.let { "" + ".${it}" }
                    ?: ""

            "$intPart${decimalPart}k"
        }
        else -> this.toString()
    }