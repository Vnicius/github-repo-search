package io.github.vnicius.githubreposearch.data.repository.repo

import io.github.vnicius.githubreposearch.data.model.RepoSearchResponse
import io.github.vnicius.githubreposearch.data.service.repo.RepoRemoteService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class RepoRemoteDataSourceImp(private val repoRemoteService: RepoRemoteService) :
    RepoRemoteDataSource {
    override suspend fun search(query: String): RepoSearchResponse = withContext(Dispatchers.IO) {
        repoRemoteService.search(query)
    }
}