package com.amk.weatherforall.viewModels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.amk.weatherforall.mvp.presenter.Settings

class SettingViewModel:ViewModel() {

    val settings = MediatorLiveData<Settings>()

}