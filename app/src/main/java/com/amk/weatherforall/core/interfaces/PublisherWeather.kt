package com.amk.weatherforall.core.interfaces

import com.amk.weatherforall.core.Weather

interface PublisherWeather {

    fun subscribe(observableWeather: ObservableWeather)
    fun unsubscribe(observableWeather: ObservableWeather)
    fun notify(weather: Weather)
}