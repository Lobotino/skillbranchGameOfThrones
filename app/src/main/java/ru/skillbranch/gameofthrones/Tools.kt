package ru.skillbranch.gameofthrones

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object Tools {

    fun isOnline(): Boolean {
        val connectivityManager = App.app!!.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork;

        if (network != null) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            if (networkCapabilities != null) {
                return (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(
                            NetworkCapabilities.TRANSPORT_WIFI
                        ));
            }
        }
        return false
    }
}