package io.github.vnicius.githubreposearch.ui.splashscreen

import android.content.Context
import io.github.vnicius.githubreposearch.util.LanguageProvider


/**
 * Created by @vnicius on 5/1/21.
 * vinicius.matheus252@gmail.com
 */

/**
 * Implementação básica de [SplashScreenContract.SplashScreenViewModel]
 *
 * @param splashScreenRouter - objeto de [SplashScreenContract.SplashScreenRouter] reponsável pela
 * navegação
 */
class SplashScreenViewModelImp(private val splashScreenRouter: SplashScreenContract.SplashScreenRouter) :
    SplashScreenContract.SplashScreenViewModel() {

    override fun loadLanguagesData(context: Context) {
        LanguageProvider.load(context.applicationContext)
    }

    override fun onLoadFinished() {
        splashScreenRouter.goToMainScreen()
    }
}