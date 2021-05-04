package io.github.vnicius.githubreposearch.data.repository.repo

import io.github.vnicius.githubreposearch.data.model.NetworkState
import io.github.vnicius.githubreposearch.data.model.RepoSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class RepoRepositoryImp(private val repoRemoteDataSource: RepoRemoteDataSource) : RepoRepository {

    private val mutableSearchState = MutableStateFlow<NetworkState>(NetworkState.Idle)

    override val searchState: Flow<NetworkState> = mutableSearchState

    override suspend fun search(query: String): RepoSearchResult? =
        withContext(Dispatchers.IO) {
            try {
                mutableSearchState.value = NetworkState.Loading

                val result = repoRemoteDataSource.search(query)

                mutableSearchState.value = NetworkState.Loaded

                result
            } catch (e: Exception) {
                mutableSearchState.value = NetworkState.Failed(e)
                null
            }
        }
}