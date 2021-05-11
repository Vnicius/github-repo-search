package io.github.vnicius.githubreposearch.ui.repodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import io.github.vnicius.githubreposearch.R
import io.github.vnicius.githubreposearch.databinding.FragmentRepoDetailsBinding
import io.github.vnicius.githubreposearch.ui.common.FullScreenDialogFragment


/**
 * Created by Vinícius Veríssimo on 5/10/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class RepoDetailsFragment : FullScreenDialogFragment() {

    private lateinit var viewBinding: FragmentRepoDetailsBinding

    private val args: RepoDetailsFragmentArgs by navArgs()
    private val viewModel: RepoDetailsContract.RepoDetailsViewModel by viewModels {
        RepoDetailsViewModelFactory(RepoDetailsRouterImp())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentRepoDetailsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readArgs()
        setupCloseButton()
        setupRepoData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.setupWithFragment(this)
    }

    override fun onDestroy() {
        viewModel.setupWithFragment(null)
        super.onDestroy()
    }

    // region Setup

    private fun readArgs() {
        viewModel.repo = args.repo
    }

    private fun setupRepoData() {
        viewModel.repo?.let { repo ->
            viewBinding.apply {
                ownerAvatar.load(repo.owner.avatarUrl) {
                    crossfade(true)
                    placeholder(R.drawable.circle)
                    transformations(CircleCropTransformation())
                }
                ownerName.text = repo.owner.login
                repoTitle.text = repo.name
            }
        }
    }

    private fun setupCloseButton() {
        viewBinding.closeButton.setOnClickListener {
            viewModel.doClose()
        }
    }

    // endregion
}