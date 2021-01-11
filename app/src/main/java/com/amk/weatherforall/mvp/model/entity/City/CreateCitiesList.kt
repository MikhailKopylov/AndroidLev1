package com.amk.weatherforall.mvp.model.database

import android.content.res.Resources
import com.amk.weatherforall.R
import com.amk.weatherforall.mvp.model.entity.City.City

fun createListCities(citySource: CitySource, resources: Resources) {
    citySource.addCity(City(resources.getString(R.string.Moscow), 524901))
    citySource.addCity(City(resources.getString(R.string.Saint_Petersburg), 498817))
    citySource.addCity(City(resources.getString(R.string.Saratov), 498677))
    citySource.addCity(City(resources.getString(R.string.Novosibirsk), 1496747))
    citySource.addCity(City(resources.getString(R.string.Ekaterinburg), 1486209))
    citySource.addCity(City(resources.getString(R.string.Kazan), 551487))
    citySource.addCity(City(resources.getString(R.string.Chelyabinsk), 1508291))
    citySource.addCity(City(resources.getString(R.string.Samara), 499068))
    citySource.addCity(City(resources.getString(R.string.Omsk), 1496153))
    citySource.addCity(City(resources.getString(R.string.Rostov_on_don), 501183))
    citySource.addCity(City(resources.getString(R.string.Ufa), 479561))
    citySource.addCity(City(resources.getString(R.string.Krasnoyarsk), 1502026))
    citySource.addCity(City(resources.getString(R.string.Voronezh), 472045))
    citySource.addCity(City(resources.getString(R.string.Perm), 511196))
    citySource.addCity(City(resources.getString(R.string.Volgograd), 472757))
    citySource.addCity(City(resources.getString(R.string.Krasnodar), 542415))
    citySource.addCity(City(resources.getString(R.string.Tyumen), 1488754))
    citySource.addCity(City(resources.getString(R.string.Izhevsk), 554840))
    citySource.addCity(City(resources.getString(R.string.Barnaul), 1510853))
    citySource.addCity(City(resources.getString(R.string.Ulyanovsk), 479119))
    citySource.addCity(City(resources.getString(R.string.Irkutsk), 2023469))
    citySource.addCity(City(resources.getString(R.string.Khabarovsk), 2022890))
    citySource.addCity(City(resources.getString(R.string.Yaroslavl), 468902))
    citySource.addCity(City(resources.getString(R.string.Kotlas), 543704))
    citySource.addCity(City(resources.getString(R.string.Vladivostok), 479119))
    citySource.addCity(City(resources.getString(R.string.Kuloy), 539385))
}