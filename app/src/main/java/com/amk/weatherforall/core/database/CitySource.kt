package com.amk.weatherforall.core.database

import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.interfaces.CityDAO

class CitySource(private val cityDAO: CityDAO) {

    var allCities: List<City> = cityDAO.getAllCities()

    private fun loadCities() {
        allCities = cityDAO.getAllCities()
    }

    fun countCities():Long{
        return cityDAO.getCountCities()
    }

    fun addCity(city: City){
        for(elem in allCities){
            if (city.name==elem.name) return
        }
        cityDAO.insertCity(city)
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