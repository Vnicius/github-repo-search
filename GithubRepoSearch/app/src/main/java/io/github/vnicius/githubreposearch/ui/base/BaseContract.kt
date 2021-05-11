package io.github.vnicius.githubreposearch.ui.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel


/**
 * Created by Vinícius Veríssimo on 5/10/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
interface BaseContract {

    abstract class BaseViewModel : ViewModel() {
        abstract fun setupWithFragment(fragment: Fragment?)
    }

    interface BaseRouter {
        fun setupWithFragment(fragment: Fragment?)
    }
}