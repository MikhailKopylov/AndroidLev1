package com.amk.weatherforall.core

import android.annotation.SuppressLint
import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

object DateTimeUtils {
    @SuppressLint("SimpleDateFormat")
    fun formatTime(time: LocalDateTime): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (time.minute > 9) {
                "${time.hour}:${time.minute}"
            } else {
                "${time.hour}:0${time.minute}"
            }
        } else {
            val formatterTime = SimpleDateFormat("hh:mma")
            formatterTime.format(Date())
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: LocalDateTime): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            if (date.monthValue > 9) {
                "${date.dayOfMonth}.${date.monthValue}.${date.year}"
            } else {
                "${date.dayOfMonth}.0${date.monthValue}.${date.year}"
            }
        } else {
            val formatterDate = SimpleDateFormat("dd.mm.yyyy")
            formatterDate.format(Date())
        }
    }

    fun datePlusDays(countDays: Int): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now().plusDays(countDays.toLong())

            if (current.monthValue > 9) {
                "${current.dayOfMonth}.${current.monthValue}.${current.year}"
            } else {
                "${current.dayOfMonth}.0${current.monthValue}.${current.year}"
            }
        } else {
            val formatterDate = SimpleDateFormat("dd.mm.yyyy")
            //TODO сделать прибаление дней
            formatterDate.format(Date())
        }
    }

    fun convertFromKelvinToC(kelvin: Int): Int {
        return kelvin - 273
    }
}