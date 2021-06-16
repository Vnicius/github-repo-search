package io.github.vnicius.githubreposearch.data.service.repo

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.vnicius.githubreposearch.data.model.RepoSearchResponse
import io.github.vnicius.githubreposearch.data.service.GithubAPIConstants
import io.github.vnicius.githubreposearch.data.service.GithubRetrofitService
import io.github.vnicius.githubreposearch.extension.toMediaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import retrofit2.Retrofit


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
@ExperimentalSerializationApi
class GithubRepoService : RepoRemoteService {

    private val applicationJson = "application/json".toMediaType()
    private val githubAPI = Retrofit.Builder()
        .baseUrl(GithubAPIConstants.BASE_URL)
        .addConverterFactory(Json {
            ignoreUnknownKeys = true
        }.asConverterFactory(applicationJson))
        .build()
        .create(GithubRetrofitService::class.java)

    override suspend fun search(query: String, page: Int, pageSize: Int): RepoSearchResponse =
        withContext(Dispatchers.IO) {
            val response = githubAPI.searchRepositories(query, page = page, perPage = pageSize)

            response
        }
}