package com.amk.weatherforall.core

import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.Weather.WeatherForecast
import com.amk.weatherforall.core.Weather.weatherFor5Days.*
import com.amk.weatherforall.core.Weather.weatherRequest.WeatherRequestCityName
import com.amk.weatherforall.fragments.MainFragment
import com.amk.weatherforall.services.WeatherRequestRetrofit

object WeatherPresenter {

    const val KEI_API: String = "a196f08fdb6ccf6e2a8a0ee4af9d9f27"
    const val UNITS: String = "metric"

    const val LATITUDE_DEFAULT:Double = -34.0
    const val LONGITUDE_DEFAULT:Double = 151.0

    val main: Main = Main(0.0, 0.0, 0.0, 0.0, 0, 0, 0, 0, 0.0)
    val weather: Weather = Weather(0, "", "", "")
    val clouds: Clouds = Clouds(0)
    val wind: Wind = Wind(0.0, 0)
    val sys: Sys = Sys("")
    val weaterDataDefault: WeatherData =
        WeatherData(1L, main, arrayOf(weather), clouds, wind, 0, 0.0, sys, "")
    var city: City = City.CITY_DEFAULT

    var weatherForecast: WeatherForecast = WeatherForecast(City("Unknown"), arrayOf(weaterDataDefault))

    val historyWeatherQueries: ArrayList<WeatherForecast> = arrayListOf()

    var isRequestSuccessful: Boolean = true
    private val weatherRequestRetrofit: WeatherRequestRetrofit = WeatherRequestRetrofit()

    lateinit var fragment: MainFragment

    fun newRequest(city: City) {
        this.city = city
        weatherRequestRetrofit.requestWeather(city, KEI_API)
    }


    fun newRequest(lat:Double, lon:Double) {
        weatherRequestRetrofit.requestWeather(lat, lon, KEI_API)
    }



}