package io.github.vnicius.githubreposearch.data.model

import kotlinx.serialization.Serializable


/**
 * Created by Vinícius Veríssimo on 5/2/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
@Serializable
data class LanguageWrapper(val name: String, val color: String)
