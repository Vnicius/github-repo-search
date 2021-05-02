package io.github.vnicius.githubreposearch.data.service

import androidx.annotation.IntRange
import io.github.vnicius.githubreposearch.data.model.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

/**
 * Interface para a configuração do acesso à API do Github utilizado o Retrofit
 */
interface GithubRetrofitService {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @IntRange(from = 0, to = 100)
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int = 0
    ): RepoSearchResponse
}