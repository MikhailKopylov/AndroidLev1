package com.amk.weatherforall.core.Weather

import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.Weather.weatherFor5Days.WeatherData

data class WeatherForecast(
    val city: City,
    val list:Array<WeatherData>

){

}