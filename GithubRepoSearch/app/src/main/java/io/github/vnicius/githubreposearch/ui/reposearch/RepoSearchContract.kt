package io.github.vnicius.githubreposearch.ui.reposearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.vnicius.githubreposearch.data.model.Repo
import io.github.vnicius.githubreposearch.data.model.RepoSearchResult


/**
 * Created by Vinícius Veríssimo on 5/2/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
interface RepoSearchContract {

    abstract class RepoSearchViewModel : ViewModel() {
        abstract val searchResult: LiveData<RepoSearchResult?>

        abstract fun search(query: String, isPriority: Boolean = false)
        abstract fun resetSearch()
        abstract fun onRepoSelected(repo: Repo)
    }

    interface RepoSearchRouter {
        fun goToRepo(repo: Repo)
    }
}