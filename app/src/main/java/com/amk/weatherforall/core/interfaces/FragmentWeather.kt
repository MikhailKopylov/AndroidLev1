package com.amk.weatherforall.core.interfaces

import com.amk.weatherforall.core.weather.WeatherForecast

interface FragmentWeather {

    fun updateWeather(weatherForecast: WeatherForecast?)
}