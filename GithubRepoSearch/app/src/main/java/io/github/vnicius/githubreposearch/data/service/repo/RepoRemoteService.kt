package io.github.vnicius.githubreposearch.data.service.repo

import io.github.vnicius.githubreposearch.data.model.RepoSearchResponse


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

/**
 * Interface para acessar o serviço remoto para o model Repo
 */
interface RepoRemoteService {
    /**
     * Realiza a busca de repositórios basedo numa query
     *
     * @param query - query da busca
     */
    suspend fun search(query: String): RepoSearchResponse
}