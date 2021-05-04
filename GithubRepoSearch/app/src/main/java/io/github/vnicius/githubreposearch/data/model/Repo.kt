package io.github.vnicius.githubreposearch.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
@Serializable
data class Repo(
    val id: Long,
    val name: String,
    @SerialName("full_name")
    val fullName: String,
    val owner: RepoOwner,
    @SerialName("html_url")
    val url: String?,
    val description: String?,
    @Serializable(with = LanguageProviderSerializer::class)
    val language: Language?,
    @SerialName("stargazers_count")
    val starsCount: Int
)
