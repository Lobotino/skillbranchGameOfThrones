package ru.skillbranch.gameofthrones.data.remote

import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.skillbranch.gameofthrones.AppConfig
import java.util.concurrent.TimeUnit

class GameOfThronesClient {

    companion object {
        var api : GameOfThronesApi? = null
        const val CONNECTION_TIMEOUT_SEC = 30;
        const val WRITE_TIMEOUT_SEC = 30;
        const val READ_TIMEOUT_SEC = 30;
    }

    fun getApiClient() : GameOfThronesApi {
        if (api == null) {
            val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
                .protocols(listOf(Protocol.HTTP_1_1))

            val url: String = AppConfig.BASE_URL
            api = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build().create(GameOfThronesApi::class.java)
        }
        return api!!
    }
}