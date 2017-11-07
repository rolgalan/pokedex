package me.rolgalan.database

import android.util.Base64
import java.io.*

/**
 * Created by Roldán Galán on 07/11/2017.
 * Temporary hack for a fast persistency solution.
 * Copied from SO : https://stackoverflow.com/a/33997941/1516973
 *
 *
 * TODO -> Don't use this and implement SQLite or any other nice system
 */

internal object ObjectSerializerHelper {

    fun objectToString(obj: Serializable): String? {

        var encoded: String? = null
        try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(obj)
            objectOutputStream.close()
            encoded = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return encoded
    }

    fun stringToObject(string: String): Serializable? {

        val bytes = Base64.decode(string, 0)
        var obj: Serializable? = null
        try {
            val objectInputStream = ObjectInputStream(
                    ByteArrayInputStream(bytes))
            return objectInputStream.readObject() as Serializable
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        return obj
    }

}