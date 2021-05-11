package io.github.vnicius.githubreposearch.ui.repodetail

import io.github.vnicius.githubreposearch.data.model.Repo
import io.github.vnicius.githubreposearch.ui.base.BaseContract
import io.github.vnicius.githubreposearch.ui.base.BaseViewModelTemplate


/**
 * Created by Vinícius Veríssimo on 5/10/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
interface RepoDetailsContract {

    abstract class RepoDetailsViewModel(router: RepoDetailsRouter) : BaseViewModelTemplate(router) {
        abstract var repo: Repo?

        abstract fun doClose()
    }

    interface RepoDetailsRouter : BaseContract.BaseRouter {
        fun close()
    }
}