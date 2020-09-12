package com.amk.weatherforall.core.Weather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class WeatherOld(
    val temperature: Int,
    val rainfall: String,
    val wind: Int,
    val pressure: Int,
val dateTimeWeather: LocalDateTime
) :  Parcelable