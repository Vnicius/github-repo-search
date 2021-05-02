package io.github.vnicius.githubreposearch.data.repository.repo

import io.github.vnicius.githubreposearch.data.model.RepoSearchResult


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

/**
 * Repositório para acesso ao model Repo e relacionados
 */
interface RepoRepository {
    /**
     * Realiza a busca de repositórios basedo numa query
     *
     * @param query - query da busca
     */
    suspend fun search(query: String): RepoSearchResult
}