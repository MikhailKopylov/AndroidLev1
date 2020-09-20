package com.amk.weatherforall.core.interfaces

import com.amk.weatherforall.core.Weather.WeatherForecast
import com.amk.weatherforall.core.Weather.WeatherHandlerSimple

interface ObservableWeather {

    fun updateWeather(weatherForecast: WeatherForecast?)
}