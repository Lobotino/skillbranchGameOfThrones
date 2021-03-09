package ru.skillbranch.gameofthrones.repositories.local

import android.content.Context
import ru.skillbranch.gameofthrones.data.SQLiteHelper
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes

class LocalCharactersRepository(private val context: Context) {

    fun saveCharacters(vararg charactersRes: CharacterRes) {
        val db = SQLiteHelper[context].writableDatabase
        for (character in charactersRes) {
            SQLiteHelper[context].insertCharacter(db, character)
        }
    }

    fun getFullCharacter(id : String, parseParentIdFromUrl: (String) -> Long) : CharacterFull {
        val db = SQLiteHelper[context].readableDatabase
        return SQLiteHelper[context].getCharacterFullById(db, id, parseParentIdFromUrl)
    }

    fun getCharacter(id: Long): CharacterRes {
        val db = SQLiteHelper[context].readableDatabase
        return SQLiteHelper[context].getCharacterById(db, id)
    }
}