package me.rolgalan.pokemon.provider

import me.rolgalan.pokemon.model.Pokemon
import me.rolgalan.pokemon.provider.boundary.PokemonBoundary
import me.rolgalan.pokemon.server.ApiManager

/**
 * Created by Roldán Galán on 03/11/2017.
 *
 * This class should be the only way of the view (app module) to fetch data from the server
 * or the local storage.
 */
class DataProvider private constructor() {

    private object Holder {
        val INSTANCE = DataProvider()
    }

    companion object {
        val instance: DataProvider by lazy { Holder.INSTANCE }
    }

    fun getNewPokemon(id: Int, listener: DataInterface<Pokemon>) {
        ApiManager.instance.getPokemon(id, PokemonBoundary(listener))
    }

    fun getBackpackPokemon(id: Int, listener: DataInterface<Pokemon>) {
        //TODO
    }
}