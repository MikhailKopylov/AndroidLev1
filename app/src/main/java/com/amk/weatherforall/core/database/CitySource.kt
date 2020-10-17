package com.amk.weatherforall.core.database

import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.interfaces.CityDAO
import com.amk.weatherforall.services.Settings

class CitySource(private val cityDAO: CityDAO) {

    var allCities: List<City> = cityDAO.getAllCities()

    private fun loadCities() {
        allCities = cityDAO.getAllCities()
    }

    fun countCities():Long{
        return cityDAO.getCountCities()
    }

    fun addCity(city: City){
        val cityUpperCase:City = Settings.copyCity(city)
        for(elem in allCities){
            if (cityUpperCase.name==elem.name) return
        }
        cityDAO.insertCity(cityUpperCase)
        loadCities()
    }
    fun updateCity(city: City){
        cityDAO.updateCity(city)
        loadCities()
    }

    fun deleteCity(city: City){
        cityDAO.deleteCity(city)
        loadCities()
    }

}