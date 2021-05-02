package io.github.vnicius.githubreposearch.data.model

import io.github.vnicius.githubreposearch.util.LanguageProvider
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
@Serializable
data class Language(val name: String, val color: String)

object LanguageProviderSerializer : KSerializer<Language> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Language", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Language {
        val value = decoder.decodeString()
        return LanguageProvider.resolveName(value)
    }

    override fun serialize(encoder: Encoder, value: Language) {
        encoder.encodeString(value.name)
    }
}