package ru.skillbranch.gameofthrones.repositories.local

import android.content.Context
import ru.skillbranch.gameofthrones.data.SQLiteHelper
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

class LocalHousesRepository(private val context : Context) {

    fun saveHouses(vararg houses: HouseRes) {
        val db = SQLiteHelper[context].writableDatabase
        for (house in houses) {
            SQLiteHelper[context].insertHouse(db, house)
        }
    }

    fun getHouse(shortName: String): HouseRes {
        val sqLiteDatabase = SQLiteHelper[context].readableDatabase
        return SQLiteHelper[context].getHouseResByShortName(sqLiteDatabase, shortName)
    }
}