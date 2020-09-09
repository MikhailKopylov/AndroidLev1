package com.amk.weatherforall.core.interfaces

import com.amk.weatherforall.core.Weather.WeatherForecast
import com.amk.weatherforall.core.Weather.WeatherHandlerSimple

interface PublisherWeather {

    fun subscribe(observableWeather: ObservableWeather)
    fun unsubscribe(observableWeather: ObservableWeather)
    fun notify(weatherForecast: WeatherForecast)
}