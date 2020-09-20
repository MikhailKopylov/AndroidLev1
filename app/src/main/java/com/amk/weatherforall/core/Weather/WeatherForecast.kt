package com.amk.weatherforall.core.Weather

import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.Weather.weatherFor5Days.WeatherData

data class WeatherForecast(
    val city: City,
    val list:Array<WeatherData>

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is WeatherForecast) return false

        if (city != other.city) return false
        if (!list.contentEquals(other.list)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = city.hashCode()
        result = 31 * result + list.contentHashCode()
        return result
    }
}