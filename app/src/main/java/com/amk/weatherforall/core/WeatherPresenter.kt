package com.amk.weatherforall.core

import com.amk.weatherforall.core.Weather.*
import com.amk.weatherforall.core.Weather.weatherFor16Days.WeatherData
import com.amk.weatherforall.fragments.MainFragment
import kotlin.collections.ArrayList

class WeatherPresenter(val fragment:MainFragment) {

    val historyWeatherQueries:ArrayList<WeatherForecast> = arrayListOf()

    val WEATHER_DATA_LIST:ArrayList<WeatherData> = arrayListOf()
    var weatherForecast:WeatherForecast = WeatherRequest(fragment = fragment).weatherResult

    fun newRequest(){
        WeatherRequest.isSendRequest = true
        weatherForecast = WeatherRequest(fragment = fragment).weatherResult
    }
}