package me.rolgalan.pokemon.provider

/**
 * Created by Roldán Galán on 03/11/2017.
 * <p>
 * Any class which wants to communicate with DataProvider to get some asynchronous data, must to
 * implement this interface.
 */
public interface DataInterface<in T> {

    /**
     * Handle successful return of data
     */
    fun onReceived(data: T)

    /**
     * Handle error requests
     */
    fun onError(error: String)
}