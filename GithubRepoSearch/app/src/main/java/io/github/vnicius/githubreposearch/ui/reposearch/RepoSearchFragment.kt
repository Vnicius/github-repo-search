package io.github.vnicius.githubreposearch.ui.reposearch

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.github.vnicius.githubreposearch.R
import io.github.vnicius.githubreposearch.data.model.NetworkState
import io.github.vnicius.githubreposearch.data.model.Repo
import io.github.vnicius.githubreposearch.data.repository.repo.RepoRemoteDataSourceImp
import io.github.vnicius.githubreposearch.data.repository.repo.RepoRepositoryImp
import io.github.vnicius.githubreposearch.data.service.repo.GithubRepoService
import io.github.vnicius.githubreposearch.databinding.FragmentRepoSearchBinding
import io.github.vnicius.githubreposearch.exception.NoResultsException
import io.github.vnicius.githubreposearch.extension.getDrawableFromAttr
import io.github.vnicius.githubreposearch.extension.hideKeyboard
import io.github.vnicius.githubreposearch.extension.setDivider


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

/**
 * Fragment responsável por buscar os repositórios no servidor e apresentar o resultado da busca
 */
class RepoSearchFragment : Fragment() {

    private lateinit var viewBinding: FragmentRepoSearchBinding

    private val viewModel: RepoSearchContract.RepoSearchViewModel by viewModels {
        RepoSearchViewModelFactory(
            RepoRepositoryImp(RepoRemoteDataSourceImp(GithubRepoService()))
        )
    }
    private val repoAdapter: RepoAdapter?
        get() = viewBinding.searchResultList.adapter as? RepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentRepoSearchBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showSearchIdle()
        setupSearchTextChange()
        setupSearchResultObserver()
        setupEditTextIMESearch()
        setupSearchBarButtonsListeners()
        setupSearchResultRecyclerView()
        setupSearchState()
    }

    // region Setup

    private fun setupSearchTextChange() {
        viewBinding.searchBar.addTextChangedListener { text ->
            handleEditableText(text)
        }
    }

    private fun setupSearchResultObserver() {
        viewModel.searchResult.observe(viewLifecycleOwner, { searchResult ->
            handleSearchResult(searchResult)
        })
    }

    private fun setupEditTextIMESearch() {
        viewBinding.searchBar.setOnEditorActionListener { _, actionId, _ ->
            val isSearchOption = actionId == EditorInfo.IME_ACTION_SEARCH

            if (isSearchOption) {
                handleEditableText(viewBinding.searchBar.text, true)
            }

            isSearchOption
        }
    }

    private fun setupSearchBarButtonsListeners() {
        viewBinding.searchBar.apply {
            setOnSearchButtonClickedListener {
                handleEditableText(viewBinding.searchBar.text, true)
            }
            setOnCancelButtonClickedListener {
                clearSearch()
                cancelSearch()
            }
        }
    }

    private fun setupSearchResultRecyclerView() {
        viewBinding.searchResultList.apply {
            adapter = RepoAdapter()
            setDivider(R.drawable.list_item_divider)
        }
    }

    private fun setupSearchState() {
        viewModel.searchState.observe(viewLifecycleOwner, { state ->
            when (state) {
                NetworkState.Idle -> {
                    clearSearchResult()
                    showSearchIdle()
                }
                NetworkState.Loading -> {
                    clearSearchResult()
                    showSearchLoading()
                }
                is NetworkState.Failed -> {
                    clearSearchResult()

                    if (state.exception is NoResultsException) {
                        showSearchNoResult()
                    } else {
                        showSearchError()
                    }
                }
            }
        })
    }

    private fun setupSearchContentFocus(childId: Int) {
        viewBinding.searchContentContainer.children.forEach { child ->
            child.isVisible = child.id == childId
        }
    }

    // endregion

    private fun handleEditableText(text: Editable?, isForced: Boolean = false) {
        text?.toString()?.let {
            handleSearchQuery(it, isForced)
        }
    }

    private fun handleSearchQuery(query: String, isForced: Boolean = false) {
        if (query.isNotBlank()) {
            performSearch(query, isForced)
        } else {
            cancelSearch()
        }

        if (isForced) {
            closeKeyboard()
        }
    }

    private fun performSearch(query: String, isForced: Boolean = false) {
        viewModel.search(query, isForced)
    }

    private fun closeKeyboard() {
        context?.hideKeyboard(viewBinding.searchBar)
    }

    private fun cancelSearch() {
        viewModel.resetSearch()
        clearSearchResult()
    }

    private fun clearSearch() {
        viewBinding.searchBar.text = null
    }

    private fun handleSearchResult(repoSearchResult: List<Repo>?) {
        repoSearchResult?.let { repoSearchResult ->
            onSearchResultChanged(repoSearchResult)
            showSearchResult()
        }
    }

    private fun onSearchResultChanged(repos: List<Repo>) {
        repoAdapter?.submitList(repos)
    }

    private fun showSearchError() {
        viewBinding.searchStateMessage.apply {
            setIcon(context.getDrawableFromAttr(R.attr.errorSearchIcon))
            setMessageRes(R.string.search_error_message)
            setupSearchContentFocus(this.id)
        }
    }

    private fun showSearchIdle() {
        viewBinding.searchStateMessage.apply {
            setIcon(context?.getDrawableFromAttr(R.attr.emptySearchIcon))
            setMessageRes(R.string.empty_search)
            setupSearchContentFocus(this.id)
        }
    }

    private fun showSearchLoading() {
        setupSearchContentFocus(viewBinding.spinner.id)
    }

    private fun showSearchResult() {
        setupSearchContentFocus(viewBinding.searchResultList.id)
    }

    private fun showSearchNoResult() {
        viewBinding.searchStateMessage.apply {
            setIcon(context?.getDrawableFromAttr(R.attr.noResultsSearchIcon))
            setMessageRes(R.string.search_no_result_message)
            setupSearchContentFocus(this.id)
        }
    }

    private fun clearSearchResult() {
        view?.post {
            repoAdapter?.submitList(null)
        }
    }
}