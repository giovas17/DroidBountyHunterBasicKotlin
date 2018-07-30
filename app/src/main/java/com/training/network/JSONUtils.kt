package com.training.network

import android.content.Context
import org.json.JSONException
import com.training.models.Fugitivo
import org.json.JSONArray
import com.training.data.DatabaseBountyHunter



/**
 * @author Giovani Gonzalez
 * Created giovani on 7/25/18
 */
class JSONUtils {

    companion object {
        fun parsearFugitivos(respuesta: String, context: Context): Boolean {
            val database = DatabaseBountyHunter(context)
            try {
                val array = JSONArray(respuesta)
                for (i in 0 until array.length()) {
                    val objecto = array.getJSONObject(i)
                    val nombreFugitivo = objecto.optString("name", "")
                    database.insertarFugitivo(Fugitivo(0, nombreFugitivo, 0))
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                return false
            } finally {
                return true
            }
        }

    }
}