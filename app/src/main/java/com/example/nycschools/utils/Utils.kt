package com.example.nycschools.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object Utils {

    object checkInternet{
        fun checkForInternet(context: Context) :Boolean{

            // register activity with the connectivity manager service
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // Returns a Network object corresponding to the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport, or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        }

    }
    object NetworkUtils{
        const val BASE_URL = "https://data.cityofnewyork.us"
    }

    object ScreenUtils{
        const val HOME_SCREEN = "Home Screen"
        const val SCHOOL_INFO = "School Info"
    }

    object NavUtils{
        const val SCHOOL_DBN = "School dbn"
    }


}