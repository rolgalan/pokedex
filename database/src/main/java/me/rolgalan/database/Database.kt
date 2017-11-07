package me.rolgalan.database

import me.rolgalan.pokemon.model.Backpack

/**
 * Created by Roldán Galán on 07/11/2017.
 */

public interface Database {

    fun saveBackpack(backpack: Backpack)
    fun getBackpack(): Backpack?
    fun removeBackpack()
}
