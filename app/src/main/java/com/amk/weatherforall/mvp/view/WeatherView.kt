package com.amk.weatherforall.mvp.view

import com.amk.weatherforall.mvp.model.entity.City.City
import com.amk.weatherforall.mvp.model.entity.weather.WeatherForecast

interface WeatherView {
    fun setWeather(weatherForecast: WeatherForecast)
    fun setCity(city: City)
    fun setErrorMessage(error:String)
    fun successFirstLoad()

}
