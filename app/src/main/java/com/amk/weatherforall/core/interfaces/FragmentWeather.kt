package com.amk.weatherforall.core.interfaces

import com.amk.weatherforall.core.Weather.WeatherForecast

interface FragmentWeather {

    fun updateWeather(weatherForecast: WeatherForecast?)
}