package com.amk.weatherforall.services

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.amk.weatherforall.core.Weather.weatherFor5Days.WeatherData

object Settings {

    var temperatureC:Boolean = true
    var showWind:Boolean = true
    var showPressure:Boolean = false


    fun temperatureMode(showInC: Boolean, weatherData: WeatherData): String {
        return if (showInC) {
            "${weatherData.main.temp.toInt()} C"
        } else {
            "${weatherData.main.temp.toInt().convertToF()} F"
        }
    }

    @SuppressLint("SetTextI18n")
    fun windMode(windTextView: TextView, weatherData: WeatherData) {
        if (showWind) {
            windTextView.text = "${weatherData.wind.speed} m/s"
            windTextView.visibility = View.VISIBLE
        } else {
            windTextView.visibility = View.INVISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    fun pressureMode(pressureTextView: TextView, weatherData: WeatherData) {
        if (showPressure) {
            pressureTextView.text = "${weatherData.main.pressure} mm Hg"
            pressureTextView.visibility = View.VISIBLE
        } else {
            pressureTextView.visibility = View.INVISIBLE
        }
    }



    private fun Int.convertToF() = ((this * 1.8) + 32).toInt()
}