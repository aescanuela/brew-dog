package es.soprasteria.brewdog

import android.app.Application
import androidx.room.Room
import es.soprasteria.brewdog.database.AppDatabase

// FIXME: Quitar la clase si al final no se usan Prefs
class MyApplication : Application() {


    private lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }

    companion object {

        var instance: MyApplication? = null
            private set
    }

    fun getDatabase(): AppDatabase{
        return database
    }


}
