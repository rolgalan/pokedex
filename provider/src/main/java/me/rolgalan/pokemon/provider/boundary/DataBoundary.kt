package me.rolgalan.pokemon.provider.boundary

import android.util.Log
import me.rolgalan.pokemon.provider.DataInterface
import me.rolgalan.pokemon.server.ResponseInterface

/**
 * Created by Roldán Galán on 03/11/2017.
 *
 * This class implements ResponseInterface and transforms the data type received into a different type.
 * Works as a boundary between ResponseInterface (currently used only by the server, but in the future
 * any other external source, should implement this interface to communicate with the app) and
 * DataInterface, used by the view (app module) to request data.
 */
abstract class DataBoundary<RETURN_TYPE, EXTERNAL_TYPE>(private val listener: DataInterface<RETURN_TYPE>?)
    : ResponseInterface<EXTERNAL_TYPE> {

    private val TAG = "Boundary"

    override fun onError(error: String) {

        Log.e(TAG, "DataBoundary.onError: " + error)
        listener?.onError(error)
    }

    fun listenerOnReceived(data: RETURN_TYPE) {

        Log.d(TAG, "DataBoundary.listenerOnReceived " + data)
        listener?.onReceived(data)
    }
}