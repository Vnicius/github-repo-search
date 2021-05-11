package io.github.vnicius.githubreposearch.ui.repodetail

import io.github.vnicius.githubreposearch.data.model.Repo


/**
 * Created by Vinícius Veríssimo on 5/10/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class RepoDetailsViewModelImp(private val repoDetailsRouter: RepoDetailsContract.RepoDetailsRouter) :
    RepoDetailsContract.RepoDetailsViewModel(repoDetailsRouter) {

    override var repo: Repo? = null

    override fun doClose() {
        repoDetailsRouter.close()
    }
}