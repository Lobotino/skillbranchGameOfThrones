package ru.skillbranch.gameofthrones.repositories.remote

import ru.skillbranch.gameofthrones.data.remote.GameOfThronesApi
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

class OnlineHousesRepository(private val apiClient : GameOfThronesApi) {

    fun getHouses(page: Long, pageSize: Long) : List<HouseRes> {
        val response = apiClient.getAllHouses(page, pageSize).execute()
        return if(response.isSuccessful && response.body() != null) {
            response.body()!!
        } else {
            ArrayList()
        }
    }

    fun getCurrentHouse(name : String) : HouseRes? {
        val response = apiClient.getCurrentHouse(name).execute()
        if(response.isSuccessful && response.body() != null && response.body()!!.isNotEmpty()) {
            return response.body()!![0]
        }
        return null
    }
}