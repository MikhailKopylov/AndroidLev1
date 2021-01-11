package com.amk.weatherforall.mvp.interfaces

import com.amk.weatherforall.mvp.model.entity.weather.WeatherForecast
import io.reactivex.rxjava3.core.Observable

interface WeatherRequestCall {
    fun dataWeather(lat: Double, lon: Double, keyApi: String):Observable<WeatherForecast>
}