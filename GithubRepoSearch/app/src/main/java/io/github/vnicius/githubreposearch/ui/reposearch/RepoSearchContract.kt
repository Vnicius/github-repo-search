package io.github.vnicius.githubreposearch.ui.reposearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.vnicius.githubreposearch.data.model.NetworkState
import io.github.vnicius.githubreposearch.data.model.Repo


/**
 * Created by Vinícius Veríssimo on 5/2/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
interface RepoSearchContract {

    abstract class RepoSearchViewModel : ViewModel() {
        abstract val searchResult: LiveData<List<Repo>?>
        abstract val searchState: LiveData<NetworkState>

        abstract fun search(query: String, isPriority: Boolean = false)
        abstract fun resetSearch()
        abstract fun onRepoSelected(repo: Repo)
    }

    interface RepoSearchRouter {
        fun goToRepo(repo: Repo)
    }
}