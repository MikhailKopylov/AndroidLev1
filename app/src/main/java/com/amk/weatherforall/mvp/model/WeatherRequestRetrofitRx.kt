package com.amk.weatherforall.mvp.model

import com.amk.weatherforall.mvp.interfaces.WeatherRequestRx
import com.amk.weatherforall.mvp.model.api.OpenWeatherCityNameRx
import com.amk.weatherforall.mvp.model.api.OpenWeatherCoordRx
import com.amk.weatherforall.mvp.model.entity.City.City
import io.reactivex.rxjava3.schedulers.Schedulers


class WeatherRequestRetrofitRx(
    private val openWeatherCoordRx: OpenWeatherCoordRx,
    private val openWeatherCityNameRx: OpenWeatherCityNameRx
) : WeatherRequestRx {

    override fun dataWeatherCoord(
        lat: Double,
        lon: Double,
        keyApi: String,
        units: String,
        local: String
    ) = openWeatherCoordRx
        .loadWeather(lat.toString(), lon.toString(), keyApi, units, local)
        .subscribeOn(
            Schedulers.io()
        )

    override fun dataWeatherCity(
        city: City,
        keyApi: String,
        units: String,
        local: String
    ) = openWeatherCityNameRx.loadWeather(city.name, keyApi, units, local)
        .subscribeOn(Schedulers.io())

}