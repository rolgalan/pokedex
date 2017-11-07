package me.rolgalan.pokemon.model

import java.io.Serializable
import java.util.*

/**
 * Created by Roldán Galán on 03/11/2017.
 */

data class Pokemon(
        val id: Int,
        val name: String,
        val baseExperience: Int,
        val height: Int,
        val order: Int,
        val weight: Int,
        val types: List<PokemonType>,
        val sprites: PokemonSprites?,
        val capturedDate: Long?) : Serializable {

    fun getFrontImage(): String? {
        return sprites?.frontDefault
    }

    fun getType(): String {
        if (types.isNotEmpty()) {
            return types[0].type.name
        }
        return ""
    }

    fun isCaptured(): Boolean {
        return capturedDate != null
    }

    fun getCapturedDate(): Date? {
        capturedDate?.let {
            return Date(capturedDate)
        }
        return null
    }
}

data class PokemonSprites(
        val backDefault: String?,
        val frontDefault: String?
) : Serializable

data class PokemonType(
        val slot: Int,
        val type: NamedApiResource
) : Serializable

data class NamedApiResource(
        val name: String,
        val url: String
) : Serializable