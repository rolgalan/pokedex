package me.rolgalan.database

import android.content.Context
import android.content.SharedPreferences
import me.rolgalan.pokemon.model.Backpack
import java.io.Serializable

/**
 * Created by Roldán Galán on 07/11/2017.
 */
class DatabasePrefs(context: Context) : Database {
    private val PREF_KEY = "PREFS_BACK_KEY"
    private val PREFS_FILE = "me.rolgalan.database.backpack_file"

    var prefs: SharedPreferences? = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)

    override fun saveBackpack(backpack: Backpack) {
        val editor = prefs!!.edit()
        editor.putString(PREF_KEY, ObjectSerializerHelper.objectToString(backpack))
        editor.apply()
    }

    override fun removeBackpack() {
        val editor = prefs!!.edit()
        editor.remove(PREF_KEY)
        editor.apply()
    }

    override fun getBackpack(): Backpack? {
        val string: String? = prefs?.getString(PREF_KEY, null)
        string?.let {
            val serializable: Serializable? = ObjectSerializerHelper.stringToObject(string)
            if (serializable != null) {
                return serializable as Backpack
            }
        }
        return null
    }
}