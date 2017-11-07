package me.rolgalan.pokemon

import android.app.Application
import com.facebook.stetho.Stetho
import me.rolgalan.pokemon.provider.DataProvider

/**
 * Created by Roldán Galán on 07/11/2017.
 */
class PokemonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //Init Crashlytics or any other traker for a real project!

        Stetho.initializeWithDefaults(this)
        DataProvider.instance.context = applicationContext
    }
}