package com.amk.weatherforall.services

import android.annotation.SuppressLint
import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

object DateTimeUtils {

    const val ADD_TO_MILLISECONDS = 1000

    @SuppressLint("SimpleDateFormat")
    fun formatTime(timeLong: Long): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val time = LocalDateTime.ofEpochSecond(timeLong, 0, ZoneOffset.UTC)
            if (time.minute > 9) {
                "${time.hour}:${time.minute}"
            } else {
                "${time.hour}:0${time.minute}"
            }
        } else {
            val formatterTime = SimpleDateFormat("HH:mm")
            formatterTime.format(timeLong * ADD_TO_MILLISECONDS)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDate(dateLong: Long): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = LocalDateTime.ofEpochSecond(dateLong, 0, ZoneOffset.UTC)

            if (date.monthValue > 9) {
                "${date.dayOfMonth}.${date.monthValue}.${date.year}"
            } else {
                "${date.dayOfMonth}.0${date.monthValue}.${date.year}"
            }
        } else {
            val formatterDate = SimpleDateFormat("dd.MM.yy")
            formatterDate.format(dateLong * ADD_TO_MILLISECONDS)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDateForParseToLocalDate(dateLong: Long): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = LocalDateTime.ofEpochSecond(dateLong, 0, ZoneOffset.UTC)
            val day = if (date.dayOfMonth > 9) {
                "${date.dayOfMonth}"
            } else {
                "0${date.dayOfMonth}"
            }

            if (date.monthValue > 9) {
                "${date.year}-${date.monthValue}-$day"
            } else {
                "${date.year}-0${date.monthValue}-$day}"
            }
        } else {
            val formatterDate = SimpleDateFormat("yyyy-mm-dd")
            formatterDate.format(Date())
        }
    }

    fun isNextDay(date: Long, previousDate: Long): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val day: LocalDate =
                LocalDate.parse(formatDateForParseToLocalDate(date))
            val previousDay: LocalDate =
                LocalDate.parse(formatDateForParseToLocalDate(previousDate))

            val difference = ChronoUnit.DAYS.between(previousDay, day)
            difference > 0
        } else {
            val simpleDateFormat = SimpleDateFormat("dd")
            val day: Int = simpleDateFormat.format(date * ADD_TO_MILLISECONDS).toInt()
            val previousDay: Int =
                simpleDateFormat.format(previousDate * ADD_TO_MILLISECONDS).toInt()
            val difference = day - previousDay
            difference > 0
        }
    }


}