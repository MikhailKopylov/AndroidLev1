package com.amk.weatherforall.services

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.amk.weatherforall.R
import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.DateTimeUtils
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

    @SuppressLint("SetTextI18n")
    fun windView(windTextView: TextView, weatherData: WeatherData) {
        val wind: String = windTextView.context.resources.getString(R.string.wind)
        val m_s: String = windTextView.context.resources.getString(R.string.m_s)
        if (showWind) {
            windTextView.text = "$wind ${weatherData.wind.speed} $m_s"
            windTextView.visibility = View.VISIBLE
        } else {
            windTextView.visibility = View.INVISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    fun pressureView(pressureTextView: TextView, weatherData: WeatherData) {
        val mm_Hg: String = pressureTextView.context.resources.getString(R.string.mm_Hg)
        val pressure: String = pressureTextView.context.resources.getString(R.string.pressure)
        if (showPressure) {
            pressureTextView.text =
                "$pressure ${weatherData.main.pressure.converterToMmHg()} $mm_Hg"
            pressureTextView.visibility = View.VISIBLE
        } else {
            pressureTextView.visibility = View.INVISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    fun dateView(dateTextView: TextView, weatherData: WeatherData) {
        dateTextView.text = "${DateTimeUtils.formatDate(weatherData.dateTime)} "
    }

    @SuppressLint("SetTextI18n")
    fun timeView(timeTextView: TextView, weatherData: WeatherData) {
        timeTextView.text = "${DateTimeUtils.formatTime(weatherData.dateTime)} "
    }

    @SuppressLint("SetTextI18n")
    fun descriptionView(descriptionTextView: TextView, weatherData: WeatherData) {
        descriptionTextView.text = startWithUpperCase(weatherData.weather[0].description)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun iconView(icon: ImageView, weatherData: WeatherData) {
        icon.setImageDrawable(
            icon.context.resources.getDrawable(
                drawable(weatherData.weather[0].icon),
                null
            )
        )
    }

    fun startWithUpperCase(word: String): String {
        if(word.isEmpty()) return word
        return StringBuilder().append(word.first().toUpperCase())
            .append(word.subSequence(1, word.length))
            .toString()
    }


    private fun Int.convertToF() = ((this * 1.8) + 32).toInt()
    fun copyCity(city: City): City {
        return City(city.id, startWithUpperCase(city.name),
        city.coord, city.country, city.timezone, city.population, city.sunrise, city.sunset)
    }
}

private fun Int.converterToMmHg() = (this / 133.3 * 100).toInt().toString()



