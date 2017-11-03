package me.rolgalan.pokemon.server

/**
 * Created by Roldán Galán on 03/11/2017.
 *
 * Any query to server must implement this interface to get the response asynchronously
 */
interface ResponseInterface<in T> {

    fun onResultsReceived(response: T)

    fun onError(error: String)
}