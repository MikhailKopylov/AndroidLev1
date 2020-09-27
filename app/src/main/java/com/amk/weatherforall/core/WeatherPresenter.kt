package com.amk.weatherforall.core

import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.Weather.WeatherForecast
import com.amk.weatherforall.core.Weather.WeatherRequest
import com.amk.weatherforall.services.WeatherRequestRetrofit

object WeatherPresenter {

    const val KEI_API: String = "a196f08fdb6ccf6e2a8a0ee4af9d9f27"
    const val UNITS: String = "metric"

    val historyWeatherQueries: ArrayList<WeatherForecast> = arrayListOf()

    var city: City = City.CITY_DEFAULT
    var weatherForecast: WeatherForecast = WeatherRequest(city).weatherResult

    var isRequestSuccessful: Boolean = true
    private val weatherRequestRetrofit: WeatherRequestRetrofit = WeatherRequestRetrofit()

    fun newRequest(city: City) {
        this.city = city
        weatherRequestRetrofit.requestWeather(city, KEI_API)

    }
}