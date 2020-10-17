package com.amk.weatherforall.viewModels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.amk.weatherforall.services.Settings

class SettingViewModel:ViewModel() {

    val settings = MediatorLiveData<Settings>()

    fun changeSettings(settings: Settings){
        this.settings.value = settings
    }
}