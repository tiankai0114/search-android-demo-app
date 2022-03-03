package telenav.demo.app

import android.app.Application
import android.content.Context
import android.os.Environment
import android.os.Looper
import android.preference.PreferenceManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class App : Application() {

    companion object {
        var application: App? = null

        const val FILTER_NUMBER = "number_of_results"
        const val LAST_ENTITY_RESPONSE_REF_ID = "last_entity_response_ref_id"
        const val SEARCH_LIMIT = "search_limit"
        const val SUGGESTIONS_LIMIT = "suggestions_limit"
        const val PREDICTIONS_LIMIT = "predictions_limit"
        const val ENVIRONMENT = "environment"
        const val FILTER_NUMBER_VALUE = 10
        const val RATE_STARS = "rate_stars"
        const val PRICE_LEVEL = "price_level"
        const val OPEN_TIME = "open_time"
        const val RESERVED = "reserved"
        const val CONNECTION_TYPES = "connection_types"

        fun writeToSharedPreferences(keyName: String, defaultValue: Int) {
            val prefs =
                application?.applicationContext?.getSharedPreferences(
                    application?.applicationContext?.getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE
                )?.edit()
            prefs?.putInt(keyName, defaultValue)
            prefs?.apply()
        }

        fun readFromSharedPreferences(keyName: String): Int {
            val prefs =
                application?.applicationContext?.getSharedPreferences(
                    application?.applicationContext?.getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE
                )
            return prefs?.getInt(keyName, FILTER_NUMBER_VALUE)?.toInt() ?: FILTER_NUMBER_VALUE
        }

        fun writeStringToSharedPreferences(keyName: String, string: String) {
            val prefs =
                application?.applicationContext?.getSharedPreferences(
                    application?.applicationContext?.getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE
                )?.edit()
            prefs?.putString(keyName, string)
            prefs?.apply()
        }

        fun writeBooleanToSharedPreferences(keyName: String, state: Boolean) {
            val prefs =
                application?.applicationContext?.getSharedPreferences(
                    application?.applicationContext?.getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE
                )?.edit()
            prefs?.putBoolean(keyName, state)
            prefs?.apply()
        }

        fun readStringFromSharedPreferences(keyName: String, def: String): String? {
            val prefs =
                application?.applicationContext?.getSharedPreferences(
                    application?.applicationContext?.getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE
                )
            return prefs?.getString(keyName, def)
        }

        fun readIntFromSharedPreferences(keyName: String, def: Int): Int {
            val prefs =
                application?.applicationContext?.getSharedPreferences(
                    application?.applicationContext?.getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE
                )
            return prefs?.getInt(keyName, def) ?: 0
        }

        fun readBooleanFromSharedPreferences(keyName: String, def: Boolean): Boolean {
            val prefs =
                application?.applicationContext?.getSharedPreferences(
                    application?.applicationContext?.getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE
                )
            return prefs?.getBoolean(keyName, def) ?: def
        }
    }

    init {
        application = this
    }
}


fun Context.setGPSListener(locationCallback: LocationCallback) {
    val locationRequest = LocationRequest.create()?.apply {
        interval = 15000
        fastestInterval = 15000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    try {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    } catch (e: SecurityException) {
    }
}

fun Context.stopGPSListener(locationCallback: LocationCallback) {
    try {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.removeLocationUpdates(locationCallback)
    } catch (e: SecurityException) {
    }
}

fun Context.convertNumberToDistance(dist: Double): String {
    val km = dist / 1000.0

    val iso = resources.configuration.locale.getISO3Country()
    return if (iso.equals("usa", true) || iso.equals("mmr", true)) {
        String.format("%.1f mi", km / 1.609)
    } else {
        String.format("%.1f km", km)
    }
}

fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()
