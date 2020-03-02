package es.soprasteria.brewdog.utils

import android.content.Context
import es.soprasteria.brewdog.constants.AppConstants


object PreferenceUtils {


    fun getStringPreference(context: Context?, key: String, defaultValue: String? = null): String? {
        val sharedPref =
            context?.getSharedPreferences(AppConstants.PREFS_FILENAME, Context.MODE_PRIVATE)
                ?: return null
        return sharedPref.getString(key, defaultValue)
    }


    fun setStringPreference(context: Context?, key: String, value: String) {
        val sharedPref =
            context?.getSharedPreferences(AppConstants.PREFS_FILENAME, Context.MODE_PRIVATE)
                ?: return
        with(sharedPref.edit()) {
            putString(key, value)
            commit()
        }

    }

}
