package me.rolgalan.pokemon.model

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by Roldán Galán on 05/11/2017.
 */
class BackpackTest {

    @Test
    fun backpack_starts_empty() {

        val backpack = Backpack()
        assertEquals(0, backpack.size)
    }

    @Test
    fun addPokemon_inEmptyList() {
        val backpack = Backpack()
        val pokemon = PokemonTest().createPokemon()
        backpack.addPokemon(pokemon)
        assertEquals(1, backpack.size)
        assertEquals(pokemon, backpack.getPokemon(pokemon.id))
    }

    @Test
    fun addPokemon_inPopulatedList() {
        val backpack = Backpack()
        backpack.addPokemon(PokemonTest().createPokemon(1))
        backpack.addPokemon(PokemonTest().createPokemon(2))
        backpack.addPokemon(PokemonTest().createPokemon(3))
        assertEquals(3, backpack.size)
        assertTrue(backpack.hasPokemon(1))
        assertTrue(backpack.hasPokemon(2))
        assertTrue(backpack.hasPokemon(3))
    }

    @Test
    fun addPokemon_alreadyExisting() {
        val backpack = Backpack()

        backpack.addPokemon(PokemonTest().createPokemon(1))
        backpack.addPokemon(PokemonTest().createPokemon(2))
        backpack.addPokemon(PokemonTest().createPokemon(3))
        assertEquals(3, backpack.size)

        val pokemon = PokemonTest().createPokemon(2, "whatever", "somethingelse")
        //This pokemon has not been added yet, so even if the id is the same, it shouldn't be there
        assertNotEquals(pokemon, backpack.getPokemon(pokemon.id))

        //After adding the new pokemon, the size of the list remains equal, since we already had a
        //pokemon of id 2
        backpack.addPokemon(pokemon)
        assertEquals(3, backpack.size)
        assertEquals(pokemon, backpack.getPokemon(pokemon.id))
    }

    @Test
    fun removePokemon_emptyList() {
        val backpack = Backpack()

        backpack.removePokemon(20)
        assertEquals(0, backpack.size)
    }

    @Test
    fun removePokemon() {
        val backpack = Backpack()
        val pokemon = PokemonTest().createPokemon(2)

        backpack.addPokemon(PokemonTest().createPokemon(1))
        backpack.addPokemon(pokemon)
        backpack.addPokemon(PokemonTest().createPokemon(3))
        assertEquals(3, backpack.size)

        backpack.removePokemon(pokemon)
        assertEquals(2, backpack.size)

        assertFalse(backpack.hasPokemon(pokemon.id))
    }

    @Test
    fun hasPokemon() {

        val backpack = Backpack()
        val pokemon = PokemonTest().createPokemon(2)

        backpack.addPokemon(PokemonTest().createPokemon(1))
        backpack.addPokemon(pokemon)
        backpack.addPokemon(PokemonTest().createPokemon(3))

        assertTrue(backpack.hasPokemon(2))
        assertTrue(backpack.hasPokemon(pokemon))
        assertFalse(backpack.hasPokemon(100))
    }

    @Test
    fun getPokemon() {
        val backpack = Backpack()
        val pokemon = PokemonTest().createPokemon(2)

        backpack.addPokemon(PokemonTest().createPokemon(1))
        backpack.addPokemon(pokemon)
        backpack.addPokemon(PokemonTest().createPokemon(3))

        assertEquals(pokemon, backpack.getPokemon(2))
        assertNotEquals(pokemon, backpack.getPokemon(10))
        assertNotEquals(pokemon, backpack.getPokemon(3))
    }
}