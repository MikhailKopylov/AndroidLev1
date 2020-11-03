package com.amk.weatherforall.services

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import com.amk.weatherforall.R
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
    fun formatDate(dateLong: Long, resources: Resources): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = LocalDateTime.ofEpochSecond(dateLong, 0, ZoneOffset.UTC)
            val monthLocalName = monthLocal(date.monthValue, resources)

            "${date.dayOfMonth} $monthLocalName"
        } else {
            val date = dateLong * ADD_TO_MILLISECONDS
            val formatterDayOfMonth = SimpleDateFormat("dd")
            val formatterNumberMonth = SimpleDateFormat("MM")
            val monthLocalName =
                monthLocal(formatterNumberMonth.format(date).toInt(), resources)
            val dayOfMonth = formatterDayOfMonth.format(date)

            "$dayOfMonth $monthLocalName "
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun formatDateHeader(dateLong: Long, resources: Resources): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = LocalDateTime.ofEpochSecond(dateLong, 0, ZoneOffset.UTC)
            val dayOfWeekLocal = dayOfWeekLocal(date.dayOfWeek.name, resources)
            val monthLocalName = monthLocal(date.monthValue, resources)

            "$dayOfWeekLocal,\n${date.dayOfMonth} $monthLocalName"

        } else {
            val date = dateLong * ADD_TO_MILLISECONDS
            val formatterDayOfMonth = SimpleDateFormat("dd")
            val formatterDayOfWeek = SimpleDateFormat("EEEE")
            val formatterNumberMonth = SimpleDateFormat("MM")
            val monthLocalName =
                monthLocal(formatterNumberMonth.format(date).toInt(), resources)
            val dayOfWeekLocal = dayOfWeekLocal(formatterDayOfWeek.format(date), resources)
            val dayOfMonth = formatterDayOfMonth.format(date)
            "$dayOfWeekLocal,\n$dayOfMonth $monthLocalName "
        }
    }

    private fun dayOfWeekLocal(dayOfWeek: String, resources: Resources): String {
        return when (dayOfWeek.toUpperCase(Locale.ROOT)) {
            "MONDAY" -> resources.getString(R.string.Monday)
            "SUNDAY" -> resources.getString(R.string.Sunday)
            "TUESDAY" -> resources.getString(R.string.Tuesday)
            "WEDNESDAY" -> resources.getString(R.string.Wednesday)
            "THURSDAY" -> resources.getString(R.string.Thursday)
            "FRIDAY" -> resources.getString(R.string.Friday)
            "SATURDAY" -> resources.getString(R.string.Saturday)
            else -> dayOfWeek
        }
    }

    private fun monthLocal(month: Int, resources: Resources): String {
        return when (month) {
            1 -> resources.getString(R.string.January)
            2 -> resources.getString(R.string.February)
            3 -> resources.getString(R.string.March)
            4 -> resources.getString(R.string.April)
            5 -> resources.getString(R.string.May)
            6 -> resources.getString(R.string.June)
            7 -> resources.getString(R.string.July)
            8 -> resources.getString(R.string.August)
            9 -> resources.getString(R.string.September)
            10 -> resources.getString(R.string.October)
            11 -> resources.getString(R.string.November)
            12 -> resources.getString(R.string.December)
            else -> month.toString()
        }
    }


    @SuppressLint("SimpleDateFormat")
    fun isNextDay(date: Long, previousDate: Long): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val day: LocalDate =
                LocalDate.parse(formatDateForParseToLocalDate(date))
            val previousDay: LocalDate =
                LocalDate.parse(formatDateForParseToLocalDate(previousDate))

            val difference = ChronoUnit.DAYS.between(previousDay, day)
            difference > 0
        } else {
            val differenceDay = differenceDay(date, previousDate, "dd")
            val differenceMonth = differenceDay(date, previousDate, "MM")
            val differenceYear = differenceDay(date, previousDate, "yyyy")
            differenceYear > 0 || differenceMonth > 0 || differenceDay > 0
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

    @SuppressLint("SimpleDateFormat")
    private fun differenceDay(date: Long, previousDate: Long, format: String): Int {
        val simpleDateFormat = SimpleDateFormat(format)
        val currentDate: Int = simpleDateFormat.format(date * ADD_TO_MILLISECONDS).toInt()
        val previousDate: Int =
            simpleDateFormat.format(previousDate * ADD_TO_MILLISECONDS).toInt()
        return currentDate - previousDate
    }

    @SuppressLint("SimpleDateFormat")
    fun isChangeTimeOfDay(date: Long): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val time = LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.UTC)
            time.hour == 9 || time.hour == 18 || time.hour == 0
        } else {
            val formatterTime = SimpleDateFormat("HH")
            val result = formatterTime.format(date * ADD_TO_MILLISECONDS)
            result == "09" || result == "18" || result == "0"
        }
    }

}