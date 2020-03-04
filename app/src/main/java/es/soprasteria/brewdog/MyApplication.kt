package es.soprasteria.brewdog

import android.app.Application

/**
 * Application class so we can use applicationContext
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {

        // Singleton pattern
        var instance: MyApplication? = null
            private set
    }


}
