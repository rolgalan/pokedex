package me.rolgalan.pokemon.server

import android.util.Log
import me.rolgalan.pokemon.server.model.PokemonResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Roldán Galán on 03/11/2017.
 */
class ApiManager private constructor() {

    private object Holder {
        val INSTANCE = ApiManager()
    }

    companion object {
        val instance: ApiManager by lazy { Holder.INSTANCE }
    }

    /**
     * Request a pokemon with an id and return it asynchronously through the listener ResponseInterface
     */
    public fun getPokemon(id: Int, listener: ResponseInterface<PokemonResponse>) {
        val call = RestClient.getClient().getPokemon(id)
        call.enqueue(MyCallback(listener))
    }

    /**
     * Generic callback for common error handling.
     * TODO All hardcoded error strings should be localised in strings.xml
     */
    class MyCallback<T> constructor(private val listener: ResponseInterface<T>) : Callback<T> {

        private val TAG = "Request"

        override fun onResponse(call: Call<T>, response: Response<T>?) {
            Log.d(TAG, "MyCallback.onResponse success " + (response?.isSuccessful ?: "responseNULL"))
            if (response != null) {

                if (response.isSuccessful && response.body() != null) {
                    listener.onResultsReceived(response.body()!!)
                } else {
                    Log.e(TAG, "MyCallback.onResponse error code (" + response.code() + ") " + response.errorBody())
                    if (response.errorBody() != null) {
                        Log.w(TAG, "onResponse errorbody: " + response.errorBody()!!.toString())
                    }
                    listener.onError("Unknown sever error " + response.code())
                }

            } else {
                Log.e(TAG, "MyCallback.onResponse null")
                listener.onError("Invalid data received")
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            Log.e(TAG, "MyCallback.onFailure " + t)
            //t.printStackTrace();
            if (t.message != "Canceled")
                listener.onError("Error requesting data to the server")
        }
    }
}