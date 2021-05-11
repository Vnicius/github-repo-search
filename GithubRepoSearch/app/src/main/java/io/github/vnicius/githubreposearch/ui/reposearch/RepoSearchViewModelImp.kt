package io.github.vnicius.githubreposearch.ui.reposearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.github.vnicius.githubreposearch.data.model.NetworkState
import io.github.vnicius.githubreposearch.data.model.Repo
import io.github.vnicius.githubreposearch.data.repository.repo.RepoRepository
import io.github.vnicius.githubreposearch.extension.isEquallyTo
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * Created by Vinícius Veríssimo on 5/2/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class RepoSearchViewModelImp(
    private val repoSearchRouter: RepoSearchContract.RepoSearchRouter,
    private val repoRepository: RepoRepository
) :
    RepoSearchContract.RepoSearchViewModel(repoSearchRouter) {

    private val mutableSearchResult = MutableLiveData<List<Repo>?>()
    private val mutableSearchState = MutableLiveData<NetworkState>(NetworkState.Idle)

    private var currentSearchJob: Job? = null
    private var latestQuery: String = ""

    override val searchResult: LiveData<List<Repo>?> = mutableSearchResult
    override val searchState: LiveData<NetworkState> = mutableSearchState

    init {
        setupSearchStateListener()
    }

    override fun search(query: String, isPriority: Boolean) {
        if (query.isEquallyTo(latestQuery) && searchResult.value?.isNotEmpty() == true) return

        currentSearchJob?.cancel()
        latestQuery = query

        if (query.isBlank()) return

        currentSearchJob = viewModelScope.launch {
            mutableSearchState.postValue(NetworkState.Loading)

            if (!isPriority) {
                delay(SEARCH_DELAY)
            }

            repoRepository.search(query)?.let { result ->
                if (query.isEquallyTo(latestQuery)) {
                    mutableSearchResult.postValue(result)
                }
            }
        }
    }

    override fun resetSearch() {
        mutableSearchState.postValue(NetworkState.Idle)
        currentSearchJob?.cancel()
        currentSearchJob = null
        mutableSearchResult.postValue(null)
    }

    override fun onRepoSelected(repo: Repo) {
        repoSearchRouter.goToRepo(repo)
    }

    private fun setupSearchStateListener() {
        viewModelScope.launch {
            repoRepository.searchState.collect {
                // Ignorar error por cancelamento do Job
                if (it !is CancellationException) {
                    mutableSearchState.postValue(it)
                }
            }
        }
    }

    companion object {
        private const val SEARCH_DELAY = 500L
    }
}