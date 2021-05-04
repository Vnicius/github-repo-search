package io.github.vnicius.githubreposearch.data.repository.repo

import io.github.vnicius.githubreposearch.data.model.NetworkState
import io.github.vnicius.githubreposearch.data.model.Repo
import kotlinx.coroutines.flow.Flow


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

/**
 * Repositório para acesso ao model Repo e relacionados
 */
interface RepoRepository {
    val searchState: Flow<NetworkState>

    /**
     * Realiza a busca de repositórios basedo numa query
     *
     * @param query - query da busca
     */
    suspend fun search(query: String): List<Repo>?
}