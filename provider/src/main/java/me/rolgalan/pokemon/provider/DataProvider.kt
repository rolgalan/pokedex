package me.rolgalan.pokemon.provider

import android.content.Context
import me.rolgalan.database.DatabasePrefs
import me.rolgalan.pokemon.model.Backpack
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

    //Cached backpack
    private var backpack: Backpack? = null
    //TODO This value shouldn't be hardcoded -> It should be asked to the server
    private val TOTAL_POKEMON = 949

    lateinit var context: Context

    private object Holder {
        val INSTANCE = DataProvider()
    }

    companion object {
        val instance: DataProvider by lazy { Holder.INSTANCE }
    }

    fun getNewRandomPokemon(listener: DataInterface<Pokemon>) {
        val id = 1 + Math.floor(Math.random() * TOTAL_POKEMON).toInt()
        getNewPokemon(id, listener)
    }

    fun getNewPokemon(id: Int, listener: DataInterface<Pokemon>) {
        //New pokemons always come from the server
        ApiManager.instance.getPokemon(id, PokemonBoundary(listener))
    }

    fun getBackpack(listener: DataInterface<Backpack>) {
        //TODO load backpack from DB and cache it here in the backpack field
        if (backpack != null) {
            listener.onReceived(backpack!!)
        } else {
            loadBackpackFromDB(listener)
        }
    }

    fun catchPokemon(pokemon: Pokemon, listener: DataInterface<Boolean>) {
        val capturedPokemon = pokemon.copy(capturedDate = System.currentTimeMillis())

        getBackpack(
                object : DataInterface<Backpack> {
                    override fun onReceived(data: Backpack) {
                        data.addPokemon(capturedPokemon)
                        listener.onReceived(true)
                        saveBackpack(data)
                    }

                    override fun onError(error: String) {
                        //retry getting backpack? This shouldn't happen...
                        listener.onReceived(false)
                    }
                })
    }

    fun hasPokemonInBackpack(id: Int, listener: DataInterface<Boolean>) {
        getBackpack(
                object : DataInterface<Backpack> {
                    override fun onReceived(data: Backpack) {
                        listener.onReceived(data.hasPokemon(id))
                    }

                    override fun onError(error: String) {
                        listener.onError(error)
                    }
                })
    }

    fun removeBackpack() {
        backpack?.clear()
        DatabasePrefs(context).removeBackpack()
    }

    private fun saveBackpack(backpack: Backpack) {
        //TODO Do async
        DatabasePrefs(context).saveBackpack(backpack)
    }

    private fun loadBackpackFromDB(listener: DataInterface<Backpack>) {
        //TODO Do async
        val dbBackpack: Backpack? = DatabasePrefs(context).getBackpack()
        if (dbBackpack != null) {
            backpack?.clear()
            getOrInitCachedBackpack().putAll(dbBackpack)
            listener.onReceived(backpack!!)
        } else {
            listener.onReceived(getOrInitCachedBackpack())
        }
    }

    private fun getOrInitCachedBackpack(): Backpack {
        if (backpack == null) {
            backpack = Backpack()
        }
        return backpack!!
    }
}