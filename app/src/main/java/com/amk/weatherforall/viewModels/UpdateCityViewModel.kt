package com.amk.weatherforall.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amk.weatherforall.mvp.model.entity.City.City

class UpdateCityViewModel:ViewModel() {

    val updateCity = MutableLiveData<City>()

    fun updateCity(city: City){
        updateCity.value = city
    }
}