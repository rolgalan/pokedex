package me.rolgalan.pokemon.model

import java.io.Serializable

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
        val sprites: PokemonSprites?) : Serializable {

    fun getFrontImage(): String? {
        return sprites?.frontDefault
    }

    fun getType(): String {
        if (types.isNotEmpty()) {
            return types[0].type.name
        }
        return ""
    }
}

data class PokemonSprites(
        val backDefault: String?,
        val frontDefault: String?
)

data class PokemonType(
        val slot: Int,
        val type: NamedApiResource
)

data class NamedApiResource(
        val name: String,
        val url: String
)