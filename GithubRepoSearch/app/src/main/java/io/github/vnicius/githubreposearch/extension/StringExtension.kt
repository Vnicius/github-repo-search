package io.github.vnicius.githubreposearch.extension

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import okhttp3.MediaType


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

fun String.toMediaType(): MediaType = MediaType.get(this)

fun <T> String.fromJson(serializer: KSerializer<T>): T {
    return Json.decodeFromString(serializer, this)
}

fun String.isEquallyTo(otherString: String?): Boolean =
    if (otherString == null) {
        this == otherString
    } else {
        this.trim().equals(otherString.trim(), ignoreCase = true)
    }