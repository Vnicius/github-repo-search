package io.github.vnicius.githubreposearch.ui.reposearch

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnAttach
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import io.github.vnicius.githubreposearch.R
import io.github.vnicius.githubreposearch.data.model.Repo
import io.github.vnicius.githubreposearch.databinding.ViewRepoItemBinding
import io.github.vnicius.githubreposearch.extension.getVibrantColor
import io.github.vnicius.githubreposearch.extension.inflate
import io.github.vnicius.githubreposearch.extension.toCompact
import io.github.vnicius.githubreposearch.util.ImageColorTransformation


/**
 * Created by Vinícius Veríssimo on 5/2/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class RepoAdapter : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
            oldItem.id == newItem.id && oldItem.starsCount == newItem.starsCount
    }
    private val asyncListDiffer = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(parent.inflate(R.layout.view_repo_item))

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        getItemAt(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    private fun getItemAt(position: Int): Repo? = asyncListDiffer.currentList.get(position)

    fun submitList(repos: List<Repo>?) {
        asyncListDiffer.submitList(repos)
    }

    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val viewBinding = ViewRepoItemBinding.bind(itemView)

        fun bind(repo: Repo) {
            val language = repo.language

            viewBinding.apply {
                ownerAvatar.load(repo.owner.avatarUrl) {
                    crossfade(true)
                    transformations(
                        CircleCropTransformation(),
                        ImageColorTransformation(::handleAvatarColor)
                    )
                }
                handleAvatarColor(ownerAvatar.getVibrantColor())
                ownerName.text = repo.owner.login
                repoTitle.text = repo.name
                description.text = repo.description
                starCount.text = repo.starsCount.toCompact()
                languageContainer.isVisible = language.name.isNotBlank()
                languageName.text = language.name
                languageName.setTextColor(language.color)
                languageIcon.setColorFilter(language.color)
            }
        }

        private fun handleAvatarColor(color: Int?) {
            itemView.doOnAttach {
                val textColor = color.takeIf { it != Color.BLACK } ?: Color.WHITE
                viewBinding.ownerName.setTextColor(textColor)
            }
        }
    }
}