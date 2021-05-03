package io.github.vnicius.githubreposearch.util

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.toColorInt
import io.github.vnicius.githubreposearch.data.model.Language
import io.github.vnicius.githubreposearch.data.model.LanguageWrapper
import io.github.vnicius.githubreposearch.extension.geContentFromFile
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

    @ColorInt
    private var unknownLanguageColor: Int = Color.WHITE
    private val languageProviderScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun load(context: Context) {
        languageProviderScope.launch {
            context.assets?.geContentFromFile(COLORS_JSON_FILE_NAME)?.let { content ->
                val languagesWrappers: List<LanguageWrapper> = Json.decodeFromString(content)
                languages = languagesWrappers.map { Language(it.name, it.color.toColorInt()) }
            }
        }
    }

    fun resolveName(name: String): Language =
        languages.firstOrNull { it.name.equals(name, ignoreCase = true) && it.color != Color.BLACK }
            ?: Language(name, unknownLanguageColor)
}