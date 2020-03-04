package es.soprasteria.brewdog.constants


/**
 * Class to define application level constants
 */
object AppConstants {

    private const val PACKAGE_NAME = "es.soprasteria.brewdog"

    // File for shared preferences
    const val PREFS_FILENAME = "$PACKAGE_NAME.prefs"

    // Intent to pass Beer between Activities
    const val INTENT_EXTRA_BEER = "$PACKAGE_NAME.intent_extra_beer"

    // List ordering directions
    enum class Direction {
        ASCENDING, DESCENDING
    }

    // Key to save an empty search
    const val EMPTY_SEARCH_KEY = "_empty_"

}