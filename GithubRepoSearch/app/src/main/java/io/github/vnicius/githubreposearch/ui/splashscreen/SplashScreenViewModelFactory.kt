package io.github.vnicius.githubreposearch.ui.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * Created by @vnicius on 5/1/21.
 * vinicius.matheus252@gmail.com
 */

/**
 * Factory para a criação de [ViewModel] da SplashScreen
 */
class SplashScreenViewModelFactory(private val splashScreenRouter: SplashScreenContract.SplashScreenRouter) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == SplashScreenContract.SplashScreenViewModel::class.java) {
            SplashScreenViewModelImp(splashScreenRouter) as T
        } else {
            super.create(modelClass)
        }
    }
}