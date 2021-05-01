package io.github.vnicius.githubreposearch.ui.splashscreen

import androidx.lifecycle.ViewModel


/**
 * Created by @vnicius on 5/1/21.
 * vinicius.matheus252@gmail.com
 *
 * Interface que contém o "contrato" da SplashScreen
 */
interface SplashScreenContract {

    /**
     * ViewModel da SplashScreen
     */
    abstract class SplashScreenViewModel : ViewModel() {
        /**
         * Lida com a finalização do carregamento da SplashScreen
         */
        abstract fun onLoadFinished()
    }

    /**
     * Router da SplashScreen
     */
    interface SplashScreenRouter {
        /**
         * Realiza a navegação para a tela principal
         */
        fun goToMainScreen()
    }
}