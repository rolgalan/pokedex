package me.rolgalan.pokemon.model

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Created by Roldán Galán on 03/11/2017.
 */
class PokemonTest {

    fun createPokemon(id: Int = 1, typeName: String = "type-name", frontImage: String = "front-image", capturedDate: Long? = null): Pokemon {
        val named = NamedApiResource(typeName, "url")
        val type = PokemonType(2, named)
        val sprites = PokemonSprites("back", frontImage)
        return Pokemon(id, "name", 10, 6, 8, 12, listOf(type), sprites, capturedDate)
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
        val sprites = PokemonSprites("back", "front-image")
        val pok = Pokemon(1, "name", 10, 6, 8, 12, emptyList(), sprites, null)

        assertEquals("", pok.getType())
    }

    @Test
    fun testIsCaptured_nullCapturedData() {
        val pok = createPokemon()
        assertEquals(false, pok.isCaptured())
    }

    @Test
    fun testIsCaptured_anyCapturedData() {
        val pok = createPokemon(capturedDate = System.currentTimeMillis())
        assertEquals(true, pok.isCaptured())
    }

    @Test
    fun testGetCapturedDate_nullCapturedData() {
        val pok = createPokemon()
        assertEquals(null, pok.getCapturedDate())
    }

    @Test
    fun testGetCapturedDate_anyCapturedData() {
        val date = Date()
        val pok = createPokemon(capturedDate = date.time)
        assertEquals(date, pok.getCapturedDate())
    }
}