package com.training.provider

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.training.data.*

/**
 * @author Giovani Gonzalez
 * Created giovani on 10/26/18
 */

private const val authority = ""
const val FUGITIVES_TRAPPED = 2502
const val LOGS_DROID_BOUNTY = 1234
const val CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/${authority}/" + FUGITIVES_TRAPPED

class FugitivosProvider : ContentProvider() {

    var database : DatabaseBountyHunter? = null

    // 1) The code passed into the constructor represents the code to return for the root
    // URI.  It's common to use NO_MATCH as the code for this case. Add the constructor below.
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        // 2) Use the addURI function to match each of the types.  Use the constants from
        // WeatherContract to help define the types to the UriMatcher.
        addURI(authority, "fugitivos/*", FUGITIVES_TRAPPED)
        addURI(authority, "logs/*", LOGS_DROID_BOUNTY)

    }

    override fun onCreate(): Boolean {
        // You can initialize variables here
        database = DatabaseBountyHunter(context)
        return true
    }

    override fun insert(p0: Uri?, p1: ContentValues?): Uri {
        // TODO("not implemented") To change body of created functions use File | Settings | File Templates.
        return Uri.EMPTY
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?,
                       selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        val localSelection: String = uri.getQueryParameter(COLUMN_NAME_STATUS) ?: ""
        val localSortOrder: String = uri.getQueryParameter("OrderBy") ?: ""
        when (uriMatcher.match(uri)) {
            FUGITIVES_TRAPPED -> {
                return database!!.querySQL("SELECT * FROM " + TABLE_NAME_FUGITIVOS + " WHERE " +
                        COLUMN_NAME_STATUS + "= ? ORDER BY " + localSortOrder, arrayOf(localSelection))
            }
        }
        return null
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        when (uriMatcher.match(uri)) {
            FUGITIVES_TRAPPED -> {
                database!!.actualizarFugitivo(values!!,"0")
            }
        }
        return 0
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        when (uriMatcher.match(uri)) {
            FUGITIVES_TRAPPED -> {
                database!!.borrarFugitivo("1")
            }
        }
        return 0
    }

    override fun getType(uri: Uri?): String {
        when (uriMatcher.match(uri)) {
            FUGITIVES_TRAPPED -> {
                return CONTENT_TYPE
            }
        }
        return ""
    }

}