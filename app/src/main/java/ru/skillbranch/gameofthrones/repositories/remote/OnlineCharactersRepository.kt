package ru.skillbranch.gameofthrones.repositories.remote

import ru.skillbranch.gameofthrones.data.remote.GameOfThronesApi
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes

class OnlineCharactersRepository(private val apiClient: GameOfThronesApi) {

    fun getCharacterById(id : Long) : CharacterRes? {
        val call = apiClient.getCharacterInfo(id).execute()
        if(call.isSuccessful && call.body() != null) {
            return call.body()
        }
        return null
    }
}