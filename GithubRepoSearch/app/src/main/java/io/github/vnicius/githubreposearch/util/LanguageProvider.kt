package io.github.vnicius.githubreposearch.util

import android.content.Context
import io.github.vnicius.githubreposearch.R
import io.github.vnicius.githubreposearch.data.model.Language
import io.github.vnicius.githubreposearch.extension.geContentFromFile
import io.github.vnicius.githubreposearch.extension.getColorFromAttr
import io.github.vnicius.githubreposearch.extension.toHexColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
object LanguageProvider {

    private const val COLORS_JSON_FILE_NAME = "colors.json"
    private var languages: List<Language> = listOf()
    private var unknownLanguageColorHex: String = "#FFF"
    private val languageProviderScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun load(context: Context) {
        languageProviderScope.launch {
            context.assets?.geContentFromFile(COLORS_JSON_FILE_NAME)?.let { content ->
                languages = Json.decodeFromString(content)
            }
            unknownLanguageColorHex =
                context.getColorFromAttr(R.attr.colorUnknownLanguageText).toHexColor()
        }
    }

    fun resolveName(name: String): Language =
        languages.firstOrNull { it.name.equals(name, ignoreCase = true) }
            ?: Language(name, unknownLanguageColorHex)
}