package ru.skillbranch.gameofthrones.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

interface GameOfThronesApi {

    @GET("houses")
    fun getAllHouses() : Call<List<HouseRes>>

    @GET("houses")
    fun getCurrentHouse(@Query("name") name : String) : Call<List<HouseRes>>

    @GET("characters/{id}")
    fun getCharacterInfo(@Path("id") id : Long) : Call<CharacterRes>
}