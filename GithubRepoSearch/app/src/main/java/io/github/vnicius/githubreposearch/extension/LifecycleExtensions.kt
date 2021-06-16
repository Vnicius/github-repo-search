package io.github.vnicius.githubreposearch.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope


/**
 * Created by Vinícius Veríssimo on 15/06/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

fun LifecycleOwner.doWhenResumed(block: suspend () -> Unit) {
    lifecycleScope.launchWhenResumed {
        block()
    }
}