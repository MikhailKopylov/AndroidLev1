package com.amk.weatherforall.core.Weather.weatherFor16Days

import java.time.LocalDateTime

data class WeatherData(
    val temp: Temperature,
    val pressure:Double,
    val humidity:Int,
    val weather: Array<Weather>,
    val speed:Double,
    val deg: Int,
    val clouds: Int


) {
    val date: LocalDateTime = LocalDateTime.now()

    fun getTemp():Int{
        return (temp.day - 273).toInt()
    }
}


