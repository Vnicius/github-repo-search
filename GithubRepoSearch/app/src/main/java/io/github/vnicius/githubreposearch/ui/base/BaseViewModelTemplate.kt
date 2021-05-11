package io.github.vnicius.githubreposearch.ui.base

import androidx.fragment.app.Fragment


/**
 * Created by Vinícius Veríssimo on 5/10/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
open class BaseViewModelTemplate(private val baseRouter: BaseContract.BaseRouter) :
    BaseContract.BaseViewModel() {
    override fun setupWithFragment(fragment: Fragment?) {
        baseRouter.setupWithFragment(fragment)
    }
}