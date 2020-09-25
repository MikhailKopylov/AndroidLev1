package com.amk.weatherforall.core

import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.Weather.*
import com.amk.weatherforall.core.Weather.weatherFor16Days.WeatherData
import com.amk.weatherforall.fragments.MainFragment
import kotlin.collections.ArrayList

object WeatherPresenter{

    val historyWeatherQueries:ArrayList<WeatherForecast> = arrayListOf()

    var city: City = City.CITY_DEFAULT
    val WEATHER_DATA_LIST:ArrayList<WeatherData> = arrayListOf()
    var weatherForecast:WeatherForecast = WeatherRequest(city).weatherResult
//    lateinit var fragment: MainFragment

    fun newRequest(city: City){
        this.city = city
        WeatherRequest.isSendRequest = true
        weatherForecast = WeatherRequest(/*fragment = fragment,*/ city = city).weatherResult
    }

//    fun getWeatherForecast(/*fragment: MainFragment,*/ city: City):WeatherForecast{
//////        this.fragment = fragment
////        weatherForecast = WeatherRequest(/*fragment = fragment,*/ city = city).weatherResult
////        return WeatherRequest(/*fragment = fragment,*/ city = city).weatherResult
////    }
}