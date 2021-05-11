package io.github.vnicius.githubreposearch.ui.reposearch

import io.github.vnicius.githubreposearch.data.model.Repo
import io.github.vnicius.githubreposearch.ui.base.BaseRouterTemplate


/**
 * Created by Vinícius Veríssimo on 5/10/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class RepoSearchRouterImp : BaseRouterTemplate(), RepoSearchContract.RepoSearchRouter {
    override fun goToRepo(repo: Repo) {
        navController?.navigate(
            RepoSearchFragmentDirections.actionSearchFragmentToRepoDetailsFragment(
                repo
            )
        )
    }
}