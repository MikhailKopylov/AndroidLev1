package com.amk.weatherforall.mvp.presenter

import com.amk.weatherforall.mvp.model.WeatherRequestRetrofitRx
import com.amk.weatherforall.mvp.model.database.CitySource
import com.amk.weatherforall.mvp.model.entity.City.City
import com.amk.weatherforall.mvp.model.entity.weather.WeatherForecast
import com.amk.weatherforall.mvp.model.entity.weather.weatherFor5Days.*
import com.amk.weatherforall.mvp.view.WeatherView
import io.reactivex.rxjava3.core.Scheduler
import timber.log.Timber

class WeatherPresenter(
    private val view: WeatherView,
    private val weatherRequest: WeatherRequestRetrofitRx,
    private val mainThreadScheduler: Scheduler,
    private val citySource: CitySource,
) {

    companion object {
        const val KEI_API: String = "a196f08fdb6ccf6e2a8a0ee4af9d9f27"
        const val UNITS: String = "metric"

        const val LATITUDE_DEFAULT: Double = 55.7522
        const val LONGITUDE_DEFAULT: Double = 37.6156

        const val UNKNOWN_CITY_NAME = "Unknown"
    }

    fun swipeRefresh(local: String) {
        newRequest(city, local)
    }

    fun firstLoad(local: String){
        newRequest(city, local)
    }

//    private val main: Main = Main(0.0, 0.0, 0.0, 0.0, 0, 0, 0, 0, 0.0)
//    val weather: Weather = Weather(0, "", "", "")
//    private val clouds: Clouds = Clouds(0)
//    private val wind: Wind = Wind(0.0, 0)
//    private val sys: Sys = Sys("")
//    private val weatherDataDefault: WeatherData =
//        WeatherData(1L, main, arrayOf(weather), clouds, wind, 0, 0.0, sys, "")
//
//    var weatherForecast: WeatherForecast =
//        WeatherForecast(City(UNKNOWN_CITY_NAME, 0), arrayOf(weatherDataDefault))

    val historyWeatherQueries: ArrayList<WeatherForecast> = arrayListOf()

//    var isRequestSuccessful: Boolean = true
//    private val weatherRequestRetrofit: WeatherRequestRetrofitMVVM = WeatherRequestRetrofitMVVM()

    lateinit var local: String
    var city: City = citySource.allCities[0]

    fun onPause(weatherForecast: WeatherForecast) {
        historyWeatherQueries.add(weatherForecast)
    }

    fun newRequest(city: City, local: String) {
        this@WeatherPresenter.local = local
        this@WeatherPresenter.city = city
        if (city.name == UNKNOWN_CITY_NAME) {
//            weatherRequestRetrofit.requestWeatherCoord(city.coord.lat, city.coord.lon, KEI_API)
            weatherRequest
                .dataWeatherCoord(city.coord.lat, city.coord.lon, KEI_API, UNITS, local)
                .observeOn(mainThreadScheduler)
                .subscribe({
                    it?.let {
                        view.setWeather(it)
                        view.successFirstLoad()
                        view.setCity(it.city)
                        citySource.addCity(city)
                    }
                }, {
//                    view.setErrorMessage("Wrong data from coordinate ")
                    Timber.e(it)
                })
        } else {
//            weatherRequestRetrofit.requestWeatherCity(city, KEI_API)
            weatherRequest
                .dataWeatherCity(city, KEI_API, UNITS, local)
                .observeOn(mainThreadScheduler)
                .subscribe({
                    it?.let {
                        view.setWeather(it)
                        view.successFirstLoad()
                        view.setCity(it.city)
                        citySource.addCity(city)
                    }
                }, {
                    Timber.e(it)
//                    view.setErrorMessage("Wrong data from city name ")
                })
        }
    }


}