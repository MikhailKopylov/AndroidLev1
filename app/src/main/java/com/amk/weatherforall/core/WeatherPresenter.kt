package com.amk.weatherforall.core

import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.interfaces.FragmentWeather
import com.amk.weatherforall.core.weather.WeatherForecast
import com.amk.weatherforall.core.weather.weatherFor5Days.*
import com.amk.weatherforall.services.WeatherRequestRetrofit

object WeatherPresenter {

    const val KEI_API: String = "a196f08fdb6ccf6e2a8a0ee4af9d9f27"
    const val UNITS: String = "metric"

    const val LATITUDE_DEFAULT: Double = 55.7522
    const val LONGITUDE_DEFAULT: Double = 37.6156

    const val UNKNOWN_CITY_NAME = "Unknown"

    private val main: Main = Main(0.0, 0.0, 0.0, 0.0, 0, 0, 0, 0, 0.0)
    val weather: Weather = Weather(0, "", "", "")
    private val clouds: Clouds = Clouds(0)
    private val wind: Wind = Wind(0.0, 0)
    private val sys: Sys = Sys("")
    private val weatherDataDefault: WeatherData =
        WeatherData(1L, main, arrayOf(weather), clouds, wind, 0, 0.0, sys, "")
    var city: City = City.CITY_DEFAULT

    var weatherForecast: WeatherForecast =
        WeatherForecast(City(UNKNOWN_CITY_NAME, 0), arrayOf(weatherDataDefault))

    val historyWeatherQueries: ArrayList<WeatherForecast> = arrayListOf()

    var isRequestSuccessful: Boolean = true
    private val weatherRequestRetrofit: WeatherRequestRetrofit = WeatherRequestRetrofit()

    lateinit var local: String

    lateinit var fragment: FragmentWeather

    fun newRequest(city: City, local: String) {
        this.local = local
        this.city = city
        if (city.name == UNKNOWN_CITY_NAME) {
            weatherRequestRetrofit.requestWeatherCoord(city.coord.lat, city.coord.lon, KEI_API)
        } else {
            weatherRequestRetrofit.requestWeatherCity(city, KEI_API)
        }
    }


}