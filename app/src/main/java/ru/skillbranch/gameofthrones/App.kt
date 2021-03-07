package ru.skillbranch.gameofthrones

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import ru.skillbranch.gameofthrones.data.SQLiteHelper

class App : Application() {

    companion object {
        var app : App? = null
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        Stetho.initializeWithDefaults(this)
        SQLiteHelper[getContext()]
    }

    fun getContext(): Context {
        return applicationContext
    }
}