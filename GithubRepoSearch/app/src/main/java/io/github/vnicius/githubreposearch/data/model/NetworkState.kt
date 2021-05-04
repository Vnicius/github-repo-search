package io.github.vnicius.githubreposearch.data.model


/**
 * Created by Vinícius Veríssimo on 5/3/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
sealed class NetworkState {
    object Idle : NetworkState()
    object Loading : NetworkState()
    object Success : NetworkState()
    object Loaded : NetworkState()
    class Failed(val exception: Exception) : NetworkState()
}
