package io.github.vnicius.githubreposearch.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes


/**
 * Created by Vinícius Veríssimo on 5/2/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

val ViewGroup.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this.context)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToParent: Boolean = false): View {
    return layoutInflater.inflate(layoutRes, this, attachToParent)
}