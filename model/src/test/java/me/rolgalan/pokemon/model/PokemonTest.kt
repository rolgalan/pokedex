package me.rolgalan.pokemon.model

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Roldán Galán on 03/11/2017.
 */
class PokemonTest {

    fun createPokemon(typeName: String = "type-name", frontImage: String = "front-image"): Pokemon {
        val named = NamedApiResource(typeName, "url")
        val type = PokemonType(2, named)
        val sprites = PokemonSprites("back", frontImage, "backF", "frontF")
        return Pokemon(1, "name", 10, 6, true, 8, 12, listOf(type), sprites)
    }

    @Test
    fun testType() {
        val pok = createPokemon()
        assertEquals("type-name", pok.getType())
    }

    @Test
    fun testFrontImage() {
        val pok = createPokemon()
        assertEquals("front-image", pok.getFrontImage())
    }

    /**
     * Checks there is no IndexOutOfBoundsException if type is an empty list
     */
    @Test
    fun testType_emptyTypeList() {
        val sprites = PokemonSprites("back", "front-image", "backF", "frontF")
        val pok = Pokemon(1, "name", 10, 6, true, 8, 12, emptyList(), sprites)

        assertEquals("", pok.getType())
    }
}