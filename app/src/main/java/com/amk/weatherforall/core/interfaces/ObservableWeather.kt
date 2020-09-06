package com.amk.weatherforall.core.interfaces

import com.amk.weatherforall.core.Weather

interface ObservableWeather {

    fun updateWeather(weather:Weather)
}