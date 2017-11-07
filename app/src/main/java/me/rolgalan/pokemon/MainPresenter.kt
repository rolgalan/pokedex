package me.rolgalan.pokemon

import android.util.Log
import me.rolgalan.pokemon.model.Backpack
import me.rolgalan.pokemon.model.Pokemon
import me.rolgalan.pokemon.provider.DataInterface
import me.rolgalan.pokemon.provider.DataProvider

/**
 * Created by Roldán Galán on 05/11/2017.
 */
class MainPresenter(val view: MainView) {

    private var currentPokemon: Pokemon? = null

    /**
     * Call to this method after the Activity is created, to choose what to load.
     * If there is a non-empty backpack, we should display a fragment with its content.
     * If the backpack is empty, we have to request a new pokemon and display it, so the user
     * can catch it.
     */
    fun prepareView() {
        view.hideLoading()
        DataProvider.instance.getBackpack(
                object : DataInterface<Backpack> {
                    override fun onReceived(data: Backpack) {
                        onBackpackReceived(data)
                    }

                    override fun onError(error: String) {
                        onBackpackNotFound()
                    }
                }
        )
    }

    fun onRestart() {
        if (currentPokemon == null) {
            getNewPokemon()
        }
    }

    fun getNewPokemon() {
        view.showLoading()
        DataProvider.instance.getNewRandomPokemon(
                object : DataInterface<Pokemon> {
                    override fun onReceived(data: Pokemon) {
                        currentPokemon = data
                        checkPokemon(data)
                    }

                    override fun onError(error: String) {
                        Log.w("Pokemon", "getNewRandomPokemon error requesting data: $error")
                        onServerErrorNewPokemon(error)
                    }
                }
        )
    }

    fun catchPokemon() {
        if (currentPokemon != null) {
            catchPokemon(currentPokemon!!)
        } else {
            //This shouldn't be possible... ¯\(°_o)/¯
            view.showMsg("There is no pokemon to catch :/")
        }
    }

    private fun catchPokemon(pokemon: Pokemon) {
        view.showLoading()
        DataProvider.instance.catchPokemon(pokemon,
                object : DataInterface<Boolean> {
                    override fun onReceived(data: Boolean) {
                        onPokemonCatch(data, pokemon)
                    }

                    override fun onError(error: String) {
                        Log.w("Pokemon", "catchPokemon error requesting data: $error")
                        onServerError(error)
                    }
                })
    }

    private fun onPokemonCatch(data: Boolean, pokemon: Pokemon) {
        view.hideLoading()
        if (data) {
            view.showPokemonCatch(pokemon.name)
            view.hideFab()
            currentPokemon = null
        } else {
            view.showMsg("The pokemon couldn't be catch! :( Try again!")
        }
    }

    private fun checkPokemon(pokemon: Pokemon) {
        DataProvider.instance.hasPokemonInBackpack(pokemon.id,
                object : DataInterface<Boolean> {
                    override fun onReceived(data: Boolean) {
                        if (data) {
                            showAlreadyInBackpack(pokemon.name)
                        } else {
                            displayPokemon(pokemon)
                        }
                    }

                    override fun onError(error: String) {
                        Log.w("Pokemon", "hasPokemonInBackpack error requesting data: $error")
                        onServerErrorNewPokemon(error)
                    }
                })
    }

    private fun showAlreadyInBackpack(name: String) {
        view.hideLoading()
        view.hideFab()
        view.showAlreadyInBackpack(name)
    }

    private fun displayPokemon(data: Pokemon) {
        view.hideLoading()
        view.showPokemon(data)
        view.showFab()
    }

    private fun onServerError(error: String) {
        view.hideLoading()
        view.showMsg(error)
    }

    private fun onServerErrorNewPokemon(error: String) {
        view.hideLoading()
        view.showErrorNewPokemon(error)
    }

    private fun onBackpackNotFound() {
        getNewPokemon()
    }

    private fun onBackpackReceived(data: Backpack) {
        view.hideLoading()
        if (data.isEmpty()) {
            onBackpackNotFound()
        } else {
            view.showBackpack()
        }
    }
}

interface MainView {
    fun showLoading()
    fun showMsg(msg: String)
    fun hideLoading()
    fun showBackpack()
    fun showPokemon(data: Pokemon)
    fun hideFab()
    fun showFab()
    fun showPokemonCatch(name: String)
    fun showAlreadyInBackpack(text: String)
    fun showErrorNewPokemon(name: String)
}
