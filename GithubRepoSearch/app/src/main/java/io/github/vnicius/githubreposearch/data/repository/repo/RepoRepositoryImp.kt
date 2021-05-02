package io.github.vnicius.githubreposearch.data.repository.repo

import io.github.vnicius.githubreposearch.data.model.RepoSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class RepoRepositoryImp(private val repoRemoteDataSource: RepoRemoteDataSource) : RepoRepository {
    override suspend fun search(query: String): RepoSearchResult = withContext(Dispatchers.IO) {
        repoRemoteDataSource.search(query)
    }
}