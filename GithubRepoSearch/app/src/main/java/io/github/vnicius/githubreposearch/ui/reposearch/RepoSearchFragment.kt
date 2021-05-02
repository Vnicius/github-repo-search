package io.github.vnicius.githubreposearch.ui.reposearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.vnicius.githubreposearch.databinding.FragmentRepoSearchBinding


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
    }
}