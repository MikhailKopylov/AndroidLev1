package com.amk.weatherforall.core

import java.time.LocalDateTime
import kotlin.collections.ArrayList
import kotlin.random.Random

object WeatherPresenter {

    val weatherList:ArrayList<Weather> = arrayListOf()

    init{
        for (i in 0..7){
            val weather= Weather(temperature = Random.nextInt(-15, 25),
                rainfall = "Clear",
                wind = Random.nextInt(0,25),
                pressure = Random.nextInt(740,780),
                dateTimeWeather = LocalDateTime.now().plusDays(i.toLong())
            )
            weatherList.add(weather)
        }
    }
}