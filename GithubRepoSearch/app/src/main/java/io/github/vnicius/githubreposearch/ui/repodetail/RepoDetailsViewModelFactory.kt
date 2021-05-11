package io.github.vnicius.githubreposearch.ui.repodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * Created by Vinícius Veríssimo on 5/10/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class RepoDetailsViewModelFactory(private val repoDetailsRouter: RepoDetailsContract.RepoDetailsRouter) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == RepoDetailsContract.RepoDetailsViewModel::class.java) {
            RepoDetailsViewModelImp(repoDetailsRouter) as T
        } else {
            super.create(modelClass)
        }
    }
}