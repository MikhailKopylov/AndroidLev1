package com.amk.weatherforall.core.Weather

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

data class WeatherHandlerSimple(



    var weather: Array<Weather>,
    val list: Main,
    val wind: Wind,
    val clouds: Clouds,
    val name: String

) {

    val date: LocalDateTime = LocalDateTime.now()


    fun convertToLocalDateViaMilisecond(dateToConvert: Date): LocalDateTime {
        return Instant.ofEpochMilli(dateToConvert.time)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is WeatherHandlerSimple) return false

        if (!weather.contentEquals(other.weather)) return false
        if (list != other.list) return false
        if (wind != other.wind) return false
        if (clouds != other.clouds) return false
        if (name != other.name) return false


        return true
    }

    override fun hashCode(): Int {
        var result = 31 * weather.contentHashCode()
        result = 31 * result + list.hashCode()
        result = 31 * result + wind.hashCode()
        result = 31 * result + clouds.hashCode()
        result = 31 * result + name.hashCode()

        return result
    }


}