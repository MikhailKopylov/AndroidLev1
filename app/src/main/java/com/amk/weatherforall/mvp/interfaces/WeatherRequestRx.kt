package com.amk.weatherforall.mvp.interfaces

import com.amk.weatherforall.mvp.model.entity.City.City
import com.amk.weatherforall.mvp.model.entity.weather.WeatherForecast
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface WeatherRequestRx {
    fun dataWeatherCoord(
        lat: Double,
        lon: Double,
        keyApi: String,
        units: String,
        local: String
    ): Observable<WeatherForecast>


    fun dataWeatherCity(
        city: City,
        keyApi: String,
        units: String,
        local: String
    ): @NonNull Observable<WeatherForecast>?
}