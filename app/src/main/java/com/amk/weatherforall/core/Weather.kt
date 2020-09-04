package com.amk.weatherforall.core

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Weather(
    val temperature: Int,
    val rainfall: String,
    val wind: Int,
    val pressure: Int,
val dateTimeWeather: LocalDateTime
) :  Parcelable