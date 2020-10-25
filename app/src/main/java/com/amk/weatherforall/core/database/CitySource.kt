package com.amk.weatherforall.core.database

import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.City.DateLastUseCity
import com.amk.weatherforall.core.interfaces.CityDAO
import com.amk.weatherforall.services.Settings
import java.util.*

class CitySource(private val cityDAO: CityDAO) {

    var allCities: List<City> = cityDAO.getAllCitiesOrderByLastUse()

    private fun loadCities() {
        allCities = cityDAO.getAllCitiesOrderByLastUse()
//        val listLastCities:List<DateLastUseCity> = cityDAO.getCitiesLastUse()
    }

    fun countCities():Long{
        return cityDAO.getCountCities()
    }

    fun addCity(city: City){
        val cityUpperCase:City = Settings.cityNameStartWithUpperCase(city)
        for(elem in allCities){
            if (cityUpperCase.name==elem.name){
                cityDAO.insertLastCity(DateLastUseCity(elem.idDB, cityUpperCase.id, Date().time))
                loadCities()
                return
            }
        }
        val id:Long = cityDAO.insertCity(cityUpperCase)
        cityDAO.insertLastCity(DateLastUseCity(id, cityUpperCase.id,Date().time))

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