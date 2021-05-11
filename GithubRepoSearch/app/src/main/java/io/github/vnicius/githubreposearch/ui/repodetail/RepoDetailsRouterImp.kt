package io.github.vnicius.githubreposearch.ui.repodetail

import io.github.vnicius.githubreposearch.ui.base.BaseRouterTemplate


/**
 * Created by Vinícius Veríssimo on 5/10/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class RepoDetailsRouterImp : BaseRouterTemplate(), RepoDetailsContract.RepoDetailsRouter {
    override fun close() {
        navController?.popBackStack()
    }
}