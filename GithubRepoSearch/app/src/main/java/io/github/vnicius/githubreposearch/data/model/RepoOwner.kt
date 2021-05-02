package io.github.vnicius.githubreposearch.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
@Serializable
data class RepoOwner(val login: String, @SerialName("avatar_url") val avatarUrl: String)
