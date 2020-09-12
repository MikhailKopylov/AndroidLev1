package com.amk.weatherforall.core

import com.amk.weatherforall.core.City.CitiesList
import com.amk.weatherforall.core.Weather.*
import com.amk.weatherforall.fragments.MainFragment
import kotlin.collections.ArrayList

class WeatherPresenter(val fragment:MainFragment) {

//    lateinit var fragment:MainFragment

    val WEATHER_DATA_LIST:ArrayList<WeatherData> = arrayListOf()
    var weatherForecast:WeatherForecast = WeatherRequest(fragment = fragment).weatherResult

//    init{
//        for (i in 0..7){
//            val weather= WeatherHandler(main = Random.nextInt(-15, 25),
//                rainfall = "Clear",
//                wind = Random.nextInt(0,25),
//                pressure = Random.nextInt(740,780),
//                dateTimeWeather = LocalDateTime.now().plusDays(i.toLong())
//            )
//            WEATHER_HANDLER_LIST.add(weather)
//        }
//        CitiesList.citiesMap
//    }
}