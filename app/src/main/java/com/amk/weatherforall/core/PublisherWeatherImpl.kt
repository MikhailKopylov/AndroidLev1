package com.amk.weatherforall.core

import com.amk.weatherforall.core.interfaces.ObservableWeather
import com.amk.weatherforall.core.interfaces.PublisherWeather

class PublisherWeatherImpl:PublisherWeather {

    private val observableWeathers:MutableList<ObservableWeather> = mutableListOf()

    override fun subscribe(observableWeather: ObservableWeather) {
        observableWeathers.add(observableWeather)
    }

    override fun unsubscribe(observableWeather: ObservableWeather) {
        observableWeathers.remove(observableWeather)
    }

    override fun notify(weather: Weather) {
        observableWeathers.forEach { it.updateWeather(weather) }
    }
}