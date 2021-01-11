package com.amk.weatherforall.mvp.view

import com.amk.weatherforall.mvp.model.entity.weather.WeatherForecast

interface SplashView {
    fun startWeatherFragment(weatherForecast: WeatherForecast)
    fun setErrorMessage(error:String)
}