package com.amk.weatherforall.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BottomNavigationViewModel:ViewModel() {
    val itemBottomNavView =MutableLiveData<Int>()

    fun selectItemBottom(idItemBottomNavView:Int){
        itemBottomNavView.value = idItemBottomNavView
    }

}