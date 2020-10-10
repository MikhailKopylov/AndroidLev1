package com.amk.weatherforall.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectCityNameViewModel:ViewModel() {

    val selectedCity = MutableLiveData<String>()

    fun cityName(cityName:String){
        selectedCity.value = cityName
    }
}