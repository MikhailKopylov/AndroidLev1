package com.amk.weatherforall.services

import junit.framework.TestCase
import org.junit.Before
import java.util.*

class DateTimeUtilsTest : TestCase() {



    fun testIsNextDay1Day1Month1Year() {
        val currentDate = 1603854000L //Wed, 28 Oct 2020 03:00:00 GMT
        val previousDate = 1603929600L//Wed, 28 Oct 2020 00:00:00 GMT
        assertEquals(DateTimeUtils.isNextDay(currentDate, previousDate), false)
    }

    fun testIsNextDay2Day1Month1Year() {
        val currentDate = 1603854000L //Wed, 28 Oct 2020 03:00:00 GMT
        val previousDate = 1603767600L// Tue, 27 Oct 2020 03:00:00 GMT
        assertEquals(DateTimeUtils.isNextDay(currentDate, previousDate), true)
    }

    fun testIsNextDay1Day2Month1Year() {
        val currentDate = 1603854000L //Wed, 28 Oct 2020 03:00:00 GMT
        val previousDate = 1601251200L// Mon, 28 Sep 2020 00:00:00 GMT
        assertEquals(DateTimeUtils.isNextDay(currentDate, previousDate), true)
    }

    fun testIsNextDay1Day1Month2Year() {
        val currentDate = 1603854000L //Wed, 28 Oct 2020 03:00:00 GMT
        val previousDate = 1572220800L//  Mon, 28 Oct 2019 00:00:00 GMT
        assertEquals(DateTimeUtils.isNextDay(currentDate, previousDate), true)
    }
    fun testIsNextDay2Day2Month1Year() {
        val currentDate = 1603854000L //Wed, 28 Oct 2020 03:00:00 GMT
        val previousDate = 1601164800L// Sun, 27 Sep 2020 00:00:00 GMT
        assertEquals(DateTimeUtils.isNextDay(currentDate, previousDate), true)
    }

    fun testIsNextDay2Day2Month2Year() {
        val currentDate = 1603854000L //Wed, 28 Oct 2020 03:00:00 GMT
        val previousDate = 1569542400L//  Fri, 27 Sep 2019 00:00:00 GMT
        assertEquals(DateTimeUtils.isNextDay(currentDate, previousDate), true)
    }


//    fun testIsNextDayOneDay() {
//        val currentDate = 1603854000L //Wed, 28 Oct 2020 03:00:00 GMT
//        val previousDate = 1603767600L// Tue, 27 Oct 2020 03:00:00 GMT
//        assertEquals(DateTimeUtils.isNextDay(currentDate, previousDate), false)
//    }
}