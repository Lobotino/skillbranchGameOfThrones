package ru.skillbranch.gameofthrones.repositories

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.skillbranch.gameofthrones.data.remote.GameOfThronesApi
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

class HousesRepository(private val apiClient : GameOfThronesApi) {

    fun getHouses(page : Long, pageSize : Long, result: (houses: List<HouseRes>) -> Unit) {
        apiClient.getAllHouses(page, pageSize).enqueue(object : Callback<List<HouseRes>> {
            override fun onResponse(call: Call<List<HouseRes>>, response: Response<List<HouseRes>>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        result(response.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<List<HouseRes>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getHouses(page: Long, pageSize: Long) : List<HouseRes> {
        val response = apiClient.getAllHouses(page, pageSize).execute()
        return if(response.isSuccessful && response.body() != null) {
            response.body()!!
        } else {
            ArrayList()
        }
    }

    fun getCurrentHouse(name: String, result: (HouseRes) -> Unit) {
        apiClient.getCurrentHouse(name).enqueue(object : Callback<List<HouseRes>> {
            override fun onResponse(call: Call<List<HouseRes>>, response: Response<List<HouseRes>>) {
                if (response.isSuccessful) {
                    if (response.body() != null && response.body()!!.isNotEmpty()) {
                        result(response.body()!![0])
                    }
                }
            }

            override fun onFailure(call: Call<List<HouseRes>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getCurrentHouse(name : String) : HouseRes? {
        val response = apiClient.getCurrentHouse(name).execute()
        if(response.isSuccessful && response.body() != null && response.body()!!.isNotEmpty()) {
            return response.body()!![0]
        }
        return null
    }
}