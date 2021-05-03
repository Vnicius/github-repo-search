package io.github.vnicius.githubreposearch.ui.reposearch

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.github.vnicius.githubreposearch.R
import io.github.vnicius.githubreposearch.data.model.Repo
import io.github.vnicius.githubreposearch.data.model.RepoSearchResult
import io.github.vnicius.githubreposearch.data.repository.repo.RepoRemoteDataSourceImp
import io.github.vnicius.githubreposearch.data.repository.repo.RepoRepositoryImp
import io.github.vnicius.githubreposearch.data.service.repo.GithubRepoService
import io.github.vnicius.githubreposearch.databinding.FragmentRepoSearchBinding
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

        setupSearchTextChange()
        setupSearchResultObserver()
        setupEditTextIMESearch()
        setupSearchBarButtonsListeners()
        setupSearchResultRecyclerView()
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

    // endregion

    private fun handleEditableText(text: Editable?, isForced: Boolean = false) {
        text?.toString()?.let {
            handleSearchQuery(it, isForced)
        }
    }

    private fun handleSearchQuery(query: String, isForced: Boolean = false) {
        if (query.isNotBlank()) {
            performSearch(query, isForced)
        }

        if (isForced) {
            closeKeyboard()
        }
    }

    private fun performSearch(query: String, isForced: Boolean = false) {
        viewModel.search(query, isForced)
    }

    private fun closeKeyboard() {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let {
            it.hideSoftInputFromWindow(viewBinding.searchBar.windowToken, 0)
        }
    }

    private fun cancelSearch() {
        viewBinding.searchBar.text = null
        viewModel.resetSearch()
    }

    private fun handleSearchResult(repoSearchResult: RepoSearchResult?) {
        repoSearchResult?.let { repoSearchResult ->
            onSearchResultChanged(repoSearchResult.result.items)
        }
    }

    private fun onSearchResultChanged(repos: List<Repo>) {
        (viewBinding.searchResultList.adapter as? RepoAdapter)?.submitList(repos)
    }
}