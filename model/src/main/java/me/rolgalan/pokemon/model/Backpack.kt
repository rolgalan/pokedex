package me.rolgalan.pokemon.model

/**
 * Created by Roldán Galán on 05/11/2017.
 */
class Backpack : HashMap<Int, Pokemon>() {
    /**
     * Adds a pokemon to the backpack
     */
    fun addPokemon(pokemon: Pokemon) {
        put(pokemon.id, pokemon)
    }

    /**
     * Removes the pokemon with this id from the backpack
     */
    fun removePokemon(id: Int) {
        remove(id)
    }

    /**
     * Removes from the backpack the pokemon with the same id as this one
     */
    fun removePokemon(pokemon: Pokemon) {
        removePokemon(pokemon.id)
    }

    /**
     * It doesn't check for the exact pokemon, but for it's kind (defined by the id)
     */
    fun hasPokemon(pokemon: Pokemon): Boolean {
        return hasPokemon(pokemon.id)
    }

    /**
     * It doesn't check for the exact pokemon, but for it's kind (defined by the id)
     */
    fun hasPokemon(id: Int): Boolean {
        return keys.contains(id)
    }

    /**
     * If there is no pokemon with this id in the backpack, this will return null
     */
    fun getPokemon(id: Int): Pokemon? {
        return get(id)
    }

    fun getSortedList(): List<Pokemon> {
        return values.sortedBy { it.order }
    }

    fun addAll(other: Backpack) {
        other.forEach { addPokemon(it.value) }
    }
}
