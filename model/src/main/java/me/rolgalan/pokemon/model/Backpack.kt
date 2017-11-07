package me.rolgalan.pokemon.model

/**
 * Created by Roldán Galán on 05/11/2017.
 * TODO consider stop extending from HashMap<Int, Pokemon>() and implement custom hashmap inner object
 */

class Backpack : HashMap<Int, Pokemon>() {
    val sortedList = mutableListOf<Pokemon>()
    /**
     * Adds a pokemon to the backpack
     */
    fun addPokemon(pokemon: Pokemon) {
        put(pokemon.id, pokemon)
        calcSortedList()
    }

    /**
     * Removes the pokemon with this id from the backpack
     */
    fun removePokemon(id: Int) {
        remove(id)
        calcSortedList()
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

    /**
     * Creates a list with the Pokemon in their right order
     * This is not optimal because is going to be generated each time a pokemon is added
     * or removed, even if the list is not required in that moment (so it might be generated later).
     *
     * But this is not rocket-science and this operation is not going to be noticed by the user
     * int this kind of appplication.
     */
    private fun calcSortedList() {
        sortedList.clear()
        sortedList.addAll(values.sortedBy { it.order })
    }

    fun addAll(other: Backpack) {
        //Do put manually to not recalculate sortedList for each item
        //other.forEach { addPokemon(it.value) }
        other.forEach { put(it.value.id, it.value) }

        calcSortedList()
    }
}
