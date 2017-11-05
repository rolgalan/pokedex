package me.rolgalan.pokemon.server.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Roldán Galán on 03/11/2017.
 */

data class PokemonResponse(
        val id: Int,
        val name: String,
        @SerializedName("base_experience")
        val baseExperience: Int,
        val height: Int,
        @SerializedName("is_default")
        val isDefault: Boolean,
        val order: Int,
        val weight: Int,
        val types: List<PokemonType>,
        val sprites: PokemonSprites?)

data class PokemonSprites(
        @SerializedName("back_default")
        val backDefault: String?,
        @SerializedName("front_default")
        val frontDefault: String?,
        @SerializedName("back_female")
        val backFemale: String?,
        @SerializedName("front_female")
        val frontFemale: String?
)

data class PokemonType(
        val slot: Int,
        val type: NamedApiResource
)

data class NamedApiResource(
        val name: String,
        val url: String
)