package br.com.soulskyye.marvel

import android.app.Application
import io.realm.Realm

class MarvelApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this)
    }

}