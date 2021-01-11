package com.amk.weatherforall.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amk.weatherforall.mvp.model.entity.City.City

class SelectCityViewModel:ViewModel() {

    val selectedCity = MutableLiveData<City>()

    fun cityName(city:City){
        selectedCity.value = city
    }
}