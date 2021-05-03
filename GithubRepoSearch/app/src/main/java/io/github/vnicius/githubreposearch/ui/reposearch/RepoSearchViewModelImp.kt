package io.github.vnicius.githubreposearch.ui.reposearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.github.vnicius.githubreposearch.data.model.Repo
import io.github.vnicius.githubreposearch.data.model.RepoSearchResult
import io.github.vnicius.githubreposearch.data.repository.repo.RepoRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Created by Vinícius Veríssimo on 5/2/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class RepoSearchViewModelImp(private val repoRepository: RepoRepository) :
    RepoSearchContract.RepoSearchViewModel() {

    private val mutableSearchResult = MutableLiveData<RepoSearchResult?>()

    private var currentSearchJob: Job? = null
    private var latestQuery: String = ""

    override val searchResult: LiveData<RepoSearchResult?> = mutableSearchResult

    override fun search(query: String, isPriority: Boolean) {
        currentSearchJob?.cancel()

        val currentQuery = query.trim()

        latestQuery = currentQuery

        if (currentQuery.isBlank()) return

        currentSearchJob = viewModelScope.launch {
            try {
                if (!isPriority) {
                    delay(SEARCH_DELAY)
                }

                val result = repoRepository.search(query)

                if (result.query.equals(currentQuery, ignoreCase = true)) {
                    mutableSearchResult.postValue(result)
                }
            } catch (e: Exception) {
            }
        }
    }

    override fun resetSearch() {
        currentSearchJob?.cancel()
        currentSearchJob = null
        mutableSearchResult.postValue(null)
    }

    override fun onRepoSelected(repo: Repo) {

    }

    companion object {
        private const val SEARCH_DELAY = 500L
    }
}