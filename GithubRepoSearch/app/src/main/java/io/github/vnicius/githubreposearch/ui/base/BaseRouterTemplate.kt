package io.github.vnicius.githubreposearch.ui.base

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import io.github.vnicius.githubreposearch.extension.weak
import java.lang.ref.WeakReference


/**
 * Created by Vinícius Veríssimo on 5/10/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
open class BaseRouterTemplate : BaseContract.BaseRouter {

    private var currentFragmentWeakReference: WeakReference<Fragment>? = null

    protected val currentFragment: Fragment?
        get() = currentFragmentWeakReference?.get()
    protected val navController: NavController?
        get() = currentFragment?.findNavController()

    override fun setupWithFragment(fragment: Fragment?) {
        currentFragmentWeakReference?.clear()
        currentFragmentWeakReference = fragment?.weak
    }
}