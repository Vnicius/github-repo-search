package io.github.vnicius.githubreposearch.extension

import java.lang.ref.WeakReference


/**
 * Created by @vnicius on 5/1/21.
 * vinicius.matheus252@gmail.com
 */

val <T> T.weak: WeakReference<T>
    get() = WeakReference(this)