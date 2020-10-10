package com.amk.weatherforall.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class SelectCoordViewModel:ViewModel() {

    val selectedCoord = MutableLiveData<LatLng>()

    fun coordinate(coord:LatLng){
        selectedCoord.value = coord
    }
}