package com.amk.weatherforall.services

import android.app.IntentService
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.WeatherPresenter
import com.amk.weatherforall.fragments.MainFragment

class WeatherRequestService : IntentService("WeatherRequestService") {

    companion object {
        const val EXTRA_CITY_NAME = "com.amk.weatherforall.services.WeatherRequest.CITY_NAME"
        const val EXTRA_RESULT = "com.amk.weatherforall.services.WeatherRequest.RESULT"

        fun startWeatherRequestService(context: FragmentActivity?, cityName: String) {
            val intent = Intent(context, WeatherRequestService::class.java)
            intent.putExtra(EXTRA_CITY_NAME, cityName)
            context?.startService(intent)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        val cityName:String  = intent?.getStringExtra(EXTRA_CITY_NAME)?:return
        val city = City(cityName)
        WeatherPresenter.newRequest(city)
        sendBroadcastFinishRequest(city)
    }

    private fun sendBroadcastFinishRequest(city: City){
        val intent = Intent(MainFragment.BROADCAST_ACTION_REQUEST_FINISHED)
        intent.putExtra(EXTRA_RESULT, city.name)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }
}