package io.github.vnicius.githubreposearch.ui.splashscreen

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Handler
import android.os.Looper
import io.github.vnicius.githubreposearch.extension.weak
import io.github.vnicius.githubreposearch.ui.main.MainActivity
import java.lang.ref.WeakReference


/**
 * Created by @vnicius on 5/1/21.
 * vinicius.matheus252@gmail.com
 */

/**
 * Implementação básica de [SplashScreenContract.SplashScreenRouter]
 *
 * @param context - Contexto da aplicação
 */
class SplashScreenRouterImp(context: Context) : SplashScreenContract.SplashScreenRouter {

    /**
     * TIP: Prefira usar [WeakReference] para guardar a referência principalmente de objetos de
     * classes do Android Framework, como Context, Activity e Fragment. Principalmente por você
     * não ter controle da construção e destruição desses objetos.
     *
     * Dê uma olhada na extension [weak] para uma forma simple de criar uma [WeakReference] de
     * qualquer objeto
     */
    private val contextWeakReference: WeakReference<Context> = context.applicationContext.weak
    private val context: Context?
        get() = contextWeakReference.get()

    override fun goToMainScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            context?.let { context ->
                context.startActivity(Intent(context, MainActivity::class.java).apply {
                    addFlags(FLAG_ACTIVITY_NEW_TASK)
                })
            }
        }, OPEN_MAIN_DELAY)
    }

    companion object {
        const val OPEN_MAIN_DELAY = 1000L
    }
}