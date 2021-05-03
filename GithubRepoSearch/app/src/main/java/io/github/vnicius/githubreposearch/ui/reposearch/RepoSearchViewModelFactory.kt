package io.github.vnicius.githubreposearch.ui.reposearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.vnicius.githubreposearch.data.repository.repo.RepoRepository


/**
 * Created by Vinícius Veríssimo on 5/2/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class RepoSearchViewModelFactory(private val repoRepository: RepoRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == RepoSearchContract.RepoSearchViewModel::class.java) {
            RepoSearchViewModelImp(repoRepository) as T
        } else {
            super.create(modelClass)
        }
    }
}