package io.github.vnicius.githubreposearch.data.repository.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.vnicius.githubreposearch.data.model.Repo
import io.github.vnicius.githubreposearch.data.service.repo.RepoRemoteService


/**
 * Created by Vinícius Veríssimo on 15/06/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class RepoPagingRemoteSource(
    private val query: String,
    private val pageSize: Int,
    private val repoRemoteService: RepoRemoteService
) : PagingSource<Int, Repo>() {

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        var data: List<Repo> = listOf()
        var prevPage: Int? = null
        var nextPage: Int? = null

        try {
            val page = params.key ?: 1
            val response = repoRemoteService.search(query, page, pageSize)

            prevPage = page.takeIf { it > 1 }?.let { it - 1 }
            nextPage = if (response.items.isEmpty()) {
                null
            } else {
                page + 1
            }

            data = response.items
        } catch (e: Exception) {
        }

        return LoadResult.Page(data = data, prevKey = prevPage, nextKey = nextPage)
    }
}