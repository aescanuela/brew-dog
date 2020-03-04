package es.soprasteria.brewdog.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.soprasteria.brewdog.MyApplication
import es.soprasteria.brewdog.constants.AppConstants
import es.soprasteria.brewdog.model.Beer


/**
 * Helper methods to use in entire Application
 */
object Utils {


    /**
     * Retrieve string from Shared Prefs
     */
    private fun getStringPreference(
        context: Context?,
        key: String
    ): String? {
        val sharedPref =
            context?.getSharedPreferences(AppConstants.PREFS_FILENAME, Context.MODE_PRIVATE)
                ?: return null
        return sharedPref.getString(key, null)
    }


    /**
     * Save string in Shared Prefs
     */
    private fun setStringPreference(context: Context?, key: String, value: String) {
        val sharedPref =
            context?.getSharedPreferences(AppConstants.PREFS_FILENAME, Context.MODE_PRIVATE)
                ?: return
        with(sharedPref.edit()) {
            putString(key, value)
            commit()
        }
    }


    /**
     * Get key to save/retrieve "search" preference
     */
    private fun getSearchKey(food: String?): String {
        return if (food.isNullOrEmpty()) {
            AppConstants.EMPTY_SEARCH_KEY
        } else {
            food
        }
    }


    /**
     * Get JSON saved in preferences for the food query (if it was searched previously)
     * @param food that we want to search
     * @return the JSON that we obtained in the query for food previously (if we searched before and it is saved)
     */
    private fun getSearchResults(food: String?): String? {
        return getStringPreference(MyApplication.instance, getSearchKey(food))
    }


    /**
     * save search result
     * @param food: the food we have searched for
     * @param body: the list we obtained from API searching for "food"
     */
    fun saveSearchResults(food: String?, body: java.util.ArrayList<Beer>?) {
        setStringPreference(
            MyApplication.instance!!,
            getSearchKey(food),
            Gson().toJson(body).toString()
        )
    }


    /**
     * Get the list of beers saved in preferences for the food query (if it was searched previously)
     * @param food that we want to search
     * @return the list of beers that we obtained in the query for food previously (if we searched before and it is saved). null if we never searched that food
     */
    fun parseSearchJson(food: String?): ArrayList<Beer>? {
        val resultSaved = getSearchResults(food)
        return if (resultSaved == null) {
            null
        } else {
            val result = arrayListOf<Beer>()
            try {
                val collectionType = object : TypeToken<Collection<Beer>>() {}.type
                result.addAll(Gson().fromJson<ArrayList<Beer>>(resultSaved, collectionType))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            result
        }
    }


}
