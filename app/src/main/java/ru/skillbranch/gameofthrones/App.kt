package ru.skillbranch.gameofthrones

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import ru.skillbranch.gameofthrones.data.SQLiteHelper

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        SQLiteHelper[getContext()]
    }

    fun getContext() : Context {
        return applicationContext
    }
}