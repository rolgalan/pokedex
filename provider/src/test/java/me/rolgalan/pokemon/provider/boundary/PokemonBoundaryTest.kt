package me.rolgalan.pokemon.provider.boundary

import me.rolgalan.pokemon.server.model.NamedApiResource
import me.rolgalan.pokemon.server.model.PokemonResponse
import me.rolgalan.pokemon.server.model.PokemonSprites
import me.rolgalan.pokemon.server.model.PokemonType
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Roldán Galán on 04/11/2017.
 * TODO check mapping with null values
 */
class PokemonBoundaryTest {
    val typeName = "type-name"
    val frontImage = "front-image"
    val backImage = "back-image"
    val url = "url"
    val slot = 2
    val id = 1
    val name = "name"
    val baseExperience = 10
    val height = 6
    val order = 8
    val weight = 12
    val named = NamedApiResource(typeName, url)
    val pType = PokemonType(slot, named);
    val sprites = PokemonSprites(backImage, frontImage, "bf", "ff")

    @Test
    fun mapPokemon() {
        val pokemon = PokemonBoundary(null).mapPokemon(PokemonResponse(id, name, baseExperience, height, true, order, weight, listOf(pType), sprites))
        assertEquals(id, pokemon.id)
        assertEquals(name, pokemon.name)
        assertEquals(baseExperience, pokemon.baseExperience)
        assertEquals(height, pokemon.height)
        assertEquals(order, pokemon.order)
        assertEquals(weight, pokemon.weight)
        assertEquals(frontImage, pokemon.getFrontImage())
        assertEquals(typeName, pokemon.getType())
    }

    @Test
    fun mapSprites() {
        val sprites = PokemonBoundary(null).mapSprites(sprites)
        assertEquals(frontImage, sprites.frontDefault)
        assertEquals(backImage, sprites.backDefault)
    }

    @Test
    fun mapTypes() {
        //TODO test list types
    }

    @Test
    fun mapType() {
        val type = PokemonBoundary(null).mapType(pType)
        assertEquals(slot, type.slot)
        assertEquals(url, type.type.url)
        assertEquals(typeName, type.type.name)
    }

    @Test
    fun mapNamedApiResource() {
        val named = PokemonBoundary(null).mapNamedApiResource(named)
        assertEquals(url, named.url)
        assertEquals(typeName, named.name)
    }

}