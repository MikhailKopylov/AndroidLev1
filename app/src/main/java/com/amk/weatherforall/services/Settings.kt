package com.amk.weatherforall.services

import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.Weather.weatherFor5Days.WeatherData

object Settings {

    var temperatureC: Boolean = true
    var showWind: Boolean = true
    var showPressure: Boolean = false


    fun temperatureMode(showInC: Boolean, weatherData: WeatherData): String {
        return if (showInC) {
            "${weatherData.main.temp.toInt()}° C"
        } else {
            "${weatherData.main.temp.toInt().convertToF()}° F"
        }
    }


    fun startWithUpperCase(word: String): String {
        if(word.isEmpty()) return word
        return StringBuilder().append(word.first().toUpperCase())
            .append(word.subSequence(1, word.length))
            .toString()
    }


    fun cityNameStartWithUpperCase(city: City): City {
        return City(city.id, startWithUpperCase(city.name),
        city.coord, city.country, city.timezone, city.population, city.sunrise, city.sunset)
    }
    private fun Int.convertToF() = ((this * 1.8) + 32).toInt()
}

internal fun Int.converterToMmHg() = (this / 133.3 * 100).toInt().toString()



