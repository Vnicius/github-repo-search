package io.github.vnicius.githubreposearch.ui.splashscreen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

/**
 * Tela de Splash, responsável pela apresentação inicial do app (tela com a logo) e realizar
 * configurações iniciais do app, se necessário
 */
class SplashScreenActivity : AppCompatActivity() {

    private val viewModel: SplashScreenContract.SplashScreenViewModel by viewModels {
        SplashScreenViewModelFactory(SplashScreenRouterImp(this.applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.apply {
            loadLanguagesData(this@SplashScreenActivity.applicationContext)
            onLoadFinished()
        }
    }
}