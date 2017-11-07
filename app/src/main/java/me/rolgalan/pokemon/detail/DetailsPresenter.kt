package me.rolgalan.pokemon.detail

import me.rolgalan.pokemon.model.Pokemon
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Created by Roldán Galán on 06/11/2017.
 */
class DetailsPresenter(val view: DetailsView, var pokemon : Pokemon) {
    private val df : DateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    /**
     * Call to this method after the [DetailsView] is created, to show the [Pokemon]
     * If there is a non-empty backpack, we should display a fragment with its content.
     * If the backpack is empty, we have to request a new pokemon and display it, so the user
     * can catch it.
     */
    fun prepareView() {
        showCommonFields(pokemon)
        view.showCapturedFields(pokemon.isCaptured())
        if (pokemon.isCaptured()){
            showCapturedFields(pokemon)
        }
    }

    private fun showCommonFields(pokemon: Pokemon) {
        view.loadImage(pokemon.getFrontImage())
        view.setName(pokemon.name)
        //If these weight or height were doubles, the formatter would be here
        view.setWeight(pokemon.weight.toString())
        view.setHeight(pokemon.height.toString())
    }

    private fun showCapturedFields(pokemon: Pokemon) {
        view.setExperience(pokemon.baseExperience.toString())
        view.setType(pokemon.getType())
        view.setCapturedTime(df.format(pokemon.getCapturedDate()))
    }

    interface DetailsView{
        fun loadImage(url : String?)
        fun setName(text : String)
        fun setWeight(text : String)
        fun setHeight(text : String)
        fun showCapturedFields(show : Boolean)
        fun setExperience(text : String)
        fun setCapturedTime(text : String)
        fun setType(text : String)
    }
}